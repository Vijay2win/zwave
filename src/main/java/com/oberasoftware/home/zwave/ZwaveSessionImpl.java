package com.oberasoftware.home.zwave;

import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.api.ZWaveScheduler;
import com.oberasoftware.home.zwave.api.ZWaveSession;
import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
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
    private NodeManager nodeManager;

    @Override
    public boolean isNetworkReady() {
        return zWaveController.isNetworkReady();
    }

    @Override
    public void subscribe(EventListener eventListener) {
        zWaveController.subscribe(eventListener);
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