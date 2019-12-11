package com.oberasoftware.home.zwave.api.events;

/**
 * @author renarj
 */
public interface DeviceEvent extends ZWaveEvent {
    int getNodeId();

    default int getEndpointId() {
        return 0;
    }

    default boolean isTriggered() {
        return false;
    }

    default boolean containsValue() {
        return false;
    }

    default String valueAsString() {
        return null;
    }

    default int valueAsInt() {
        return -1;
    }
}
