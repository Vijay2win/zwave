package com.oberasoftware.home.zwave.api.actions;

/**
 * @author renarj
 */
public class RequestNodeInfoAction implements ZWaveDeviceAction {

    private final int nodeId;

    public RequestNodeInfoAction(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }
}
