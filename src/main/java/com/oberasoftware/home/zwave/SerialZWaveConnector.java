package com.oberasoftware.home.zwave;

import com.oberasoftware.home.zwave.api.ControllerConnector;
import com.oberasoftware.home.zwave.core.ZWaveRawMessage;
import com.oberasoftware.home.zwave.core.utils.IOSupplier;
import com.oberasoftware.home.zwave.exceptions.RuntimeAutomationException;
import com.oberasoftware.home.zwave.exceptions.ZWaveConfigurationException;
import com.oberasoftware.home.zwave.exceptions.ZWaveException;
import com.oberasoftware.home.zwave.threading.ReceiverThread;
import com.oberasoftware.home.zwave.threading.SenderThread;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.google.common.util.concurrent.Uninterruptibles.joinUninterruptibly;
import static com.oberasoftware.home.zwave.ZWAVE_CONSTANTS.ZWAVE_RECEIVE_TIMEOUT;

/**
 * @author renarj
 */
@Component
public class SerialZWaveConnector implements ControllerConnector {
    private static final Logger LOG = LoggerFactory.getLogger(SerialZWaveConnector.class);

    @Value("${zwave.serial.port:}")
    private String portName;

    private SerialPort serialPort;

    @Autowired
    private ReceiverThread receiverThread;

    @Autowired
    private SenderThread senderThread;

    private boolean isConnected = false;

    /**
     * Connect to the zwave controller
     *
     * @throws ZWaveException if unable to connect to the serial device
     */
    public synchronized void connect() throws ZWaveException {
        if(StringUtils.isEmpty(portName)) {
            throw new ZWaveConfigurationException("No port configured for ZWave");
        }

        if(!isConnected) {
            LOG.info("Connecting to ZWave serial port device: {}", portName);
            try {
                CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
                CommPort commPort = portIdentifier.open("zwaveport", 2000);
                this.serialPort = (SerialPort) commPort;
                this.serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                this.serialPort.enableReceiveThreshold(1);
                this.serialPort.enableReceiveTimeout(ZWAVE_RECEIVE_TIMEOUT);

                this.receiverThread.start();
                this.senderThread.start();

                this.isConnected = true;
                LOG.info("ZWave controller is connected");
            } catch (NoSuchPortException e) {
                throw new ZWaveException(String.format("Serial port %s does not exist", portName), e);
            } catch (PortInUseException e) {
                throw new ZWaveException(String.format("Serial port %s is in use", portName), e);
            } catch (UnsupportedCommOperationException e) {
                throw new ZWaveException(String.format("Unsupported operation on serial port %s", portName), e);
            }
        }
    }

    @Override
    @PreDestroy
    public void close() throws ZWaveException {
        senderThread.interrupt();
        receiverThread.interrupt();

        joinUninterruptibly(senderThread);
        joinUninterruptibly(receiverThread);

        serialPort.close();
        serialPort = null;
        isConnected = false;
    }

    public OutputStream getOutputStream() {
        return returnIfConnected(serialPort::getOutputStream);
    }

    public InputStream getInputStream() {
        return returnIfConnected(serialPort::getInputStream);
    }

    private <T> T returnIfConnected(IOSupplier<T> delegate) {
        try {
            return isConnected() ? delegate.get() : null;
        } catch(IOException e) {
            LOG.error("", e);
            throw new RuntimeAutomationException("Unable to open zwave serial device port", e);
        }
    }

    @Override
    public void send(ZWaveRawMessage rawMessage) throws ZWaveException {
        senderThread.queueMessage(rawMessage);
    }

    @Override
    public void completeTransaction() {
        senderThread.completeTransaction();
    }

    @Override
    public boolean isConnected() {
        return serialPort != null;
    }

    @Override
    public String toString() {
        return "SerialZWaveConnector{" +
                "portName='" + portName + '\'' +
                ", serialPort=" + serialPort +
                '}';
    }
}
