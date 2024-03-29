package com.oberasoftware.home.zwave.api.actions;

/**
 * @author renarj
 */
public interface ZWaveDeviceAction extends ZWaveAction {
    int DEFAULT_ENDPOINTID = 0;

    int getNodeId();

    default int getEndpointId() {
        return DEFAULT_ENDPOINTID;
    }
}
