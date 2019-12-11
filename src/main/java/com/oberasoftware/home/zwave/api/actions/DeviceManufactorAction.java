package com.oberasoftware.home.zwave.api.actions;

/**
 * @author renarj
 */
public class DeviceManufactorAction implements ZWaveDeviceAction {
    private final int nodeId;

    public DeviceManufactorAction(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "DeviceManufactorInformationAction{" +
                "nodeId=" + nodeId +
                '}';
    }
}
