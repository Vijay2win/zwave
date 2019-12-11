package com.oberasoftware.home.zwave.api.actions;

/**
 * @author renarj
 */
public class IdentifyNodeAction implements ZWaveAction {

    private int nodeId;

    public IdentifyNodeAction(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }
}
