package com.oberasoftware.home.zwave.api.events;

/**
 * @author renarj
 */
public class WakeUpReceivedEvent extends WakeUpEvent {
    public WakeUpReceivedEvent(int nodeId) {
        super(nodeId);
    }
}
