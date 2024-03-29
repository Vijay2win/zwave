package com.oberasoftware.home.zwave.api.actions;

/**
 * @author renarj
 */
public class SwitchMultiLevelGetAction implements ZWaveDeviceAction {
    private final int nodeId;

    public SwitchMultiLevelGetAction(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "SwitchMultiLevelGetAction{" +
                "nodeId=" + nodeId +
                '}';
    }
}
