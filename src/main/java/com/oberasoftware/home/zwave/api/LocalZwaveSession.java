package com.oberasoftware.home.zwave.api;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.home.zwave.LocalSpringContainer;
import com.oberasoftware.home.zwave.SerialZWaveConnector;
import com.oberasoftware.home.zwave.ZWaveController;
import com.oberasoftware.home.zwave.api.actions.ZWaveAction;
import com.oberasoftware.home.zwave.api.actions.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.exceptions.ZWaveException;

import java.io.IOError;
import java.util.concurrent.TimeUnit;

/**
 * @author renarj
 */
public class LocalZwaveSession implements ZWaveSession {
    @Override
    public LocalZwaveSession connect() {
        try {
            LocalSpringContainer.getBean(SerialZWaveConnector.class).connect();
            LocalSpringContainer.getBean(ZWaveController.class).initializeNetwork();
            return this;
        } catch (ZWaveException e) {
            throw new IOError(e);
        }
    }

    @Override
    public boolean isNetworkReady() {
        return getController().isNetworkReady();
    }

    @Override
    public LocalZwaveSession subscribe(EventHandler eventListener) {
        getController().subscribe(eventListener);
        return this;
    }

    @Override
    public NodeManager getDeviceManager() {
        return LocalSpringContainer.getBean(NodeManager.class);
    }

    @Override
    public void doAction(ZWaveAction action) throws HomeAutomationException {
        getController().send(action);
    }

    @Override
    public void schedule(ZWaveIntervalAction action, TimeUnit timeUnit, long interval) {
        LocalSpringContainer.getBean(ZWaveScheduler.class).schedule(action, timeUnit, interval);
    }

    @Override
    public void shutdown() {
        LocalSpringContainer.destroy();
    }

    private ZWaveController getController() {
        return LocalSpringContainer.getBean(ZWaveController.class);
    }
}
