package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.BasicEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static java.lang.Integer.toHexString;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class BasicConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(BasicConverter.class);

    private static final int BASIC_SET = 0x01;
    private static final int BASIC_GET = 0x02;
    private static final int BASIC_REPORT = 0x03;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.BASIC)
    public BasicEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Basic command received for node: {}", source.getNodeId());
        byte[] payload = source.getPayload();

        int command = payload[0];
        switch (command) {
            case BASIC_SET:
            case BASIC_REPORT:
                int value = payload[1];

                return new BasicEvent(source.getNodeId(), value);
            case BASIC_GET:
            default:
                LOG.debug("Basic command: {} not implemented from node: {}", toHexString(command), source.getNodeId());
        }

        return null;
    }
}
