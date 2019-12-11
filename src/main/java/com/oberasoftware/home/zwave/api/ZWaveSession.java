package com.oberasoftware.home.zwave.api;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.home.zwave.api.actions.ZWaveAction;
import com.oberasoftware.home.zwave.api.actions.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.exceptions.ZWaveException;

import java.util.concurrent.TimeUnit;

/**
 * @author renarj
 */
public interface ZWaveSession {
    ZWaveSession connect() throws ZWaveException;

    boolean isNetworkReady();

    ZWaveSession subscribe(EventHandler eventListener);

    NodeManager getDeviceManager();

    void shutdown();

    /**
     * Schedules a special action that can be triggered in an interval
     * @param action The interval based action
     * @param timeUnit The timeunit the interval is specified as
     * @param interval The interval
     */
    void schedule(ZWaveIntervalAction action, TimeUnit timeUnit, long interval);

    void doAction(ZWaveAction action) throws HomeAutomationException;
}
