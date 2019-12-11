package com.oberasoftware.home.zwave.api;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.home.zwave.SerialZWaveConnector;
import com.oberasoftware.home.zwave.ZWaveController;
import com.oberasoftware.home.zwave.api.actions.ZWaveAction;
import com.oberasoftware.home.zwave.api.actions.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.exceptions.ZWaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author renarj
 */
@Component
public class ZwaveSessionImpl implements ZWaveSession {

    @Autowired
    private ZWaveController zWaveController;

    @Autowired
    private ZWaveScheduler zWaveScheduler;

    @Autowired
    private SerialZWaveConnector zWaveConnector;

    @Autowired
    private NodeManager nodeManager;

    @Override
    public ZwaveSessionImpl connect() throws ZWaveException {
        zWaveConnector.connect();
        zWaveController.initializeNetwork();
        return this;
    }

    @Override
    public boolean isNetworkReady() {
        return zWaveController.isNetworkReady();
    }

    @Override
    public ZwaveSessionImpl subscribe(EventHandler eventListener) {
        zWaveController.subscribe(eventListener);
        return this;
    }

    @Override
    public NodeManager getDeviceManager() {
        return nodeManager;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void doAction(ZWaveAction action) throws HomeAutomationException {
        zWaveController.send(action);
    }

    @Override
    public void schedule(ZWaveIntervalAction action, TimeUnit timeUnit, long interval) {
        zWaveScheduler.schedule(action, timeUnit, interval);
    }
}
