package com.oberasoftware.home.zwave.api;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.oberasoftware.home.zwave.api.actions.ZWaveIntervalAction;

/**
 * @author renarj
 */
public interface ZWaveScheduler {
    ScheduledFuture<?> schedule(ZWaveIntervalAction action, TimeUnit timeUnit, long interval);

    ScheduledFuture<?> scheduleOnce(ZWaveIntervalAction action);
}
