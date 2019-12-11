package com.oberasoftware.home.zwave.api.actions;

/**
 * @author renarj
 */
public class MultiInstanceEndpointAction implements ZWaveDeviceAction {
    private final int nodeId;

    public MultiInstanceEndpointAction(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "MultiInstanceEndpointAction{" +
                "nodeId=" + nodeId +
                '}';
    }
}
