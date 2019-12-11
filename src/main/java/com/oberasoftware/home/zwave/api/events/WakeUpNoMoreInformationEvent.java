package com.oberasoftware.home.zwave.api.events;

/**
 * @author renarj
 */
public class WakeUpNoMoreInformationEvent extends WakeUpEvent {

    public WakeUpNoMoreInformationEvent(int nodeId) {
        super(nodeId);
    }

    @Override
    public String toString() {
        return "WakeUpNoMoreInformationEvent{" + super.toString() + "}";
    }
}
