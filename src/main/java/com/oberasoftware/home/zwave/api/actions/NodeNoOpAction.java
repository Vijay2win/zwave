package com.oberasoftware.home.zwave.api.actions;

/**
 * @author renarj
 */
public class NodeNoOpAction implements ZWaveDeviceAction {
    private int nodeId;

    public NodeNoOpAction(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "NodeNoOpAction{" +
                "nodeId=" + nodeId +
                '}';
    }
}
