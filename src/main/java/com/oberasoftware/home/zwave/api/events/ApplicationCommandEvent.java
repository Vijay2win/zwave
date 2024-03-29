package com.oberasoftware.home.zwave.api.events;

import static com.oberasoftware.home.zwave.core.ZWaveRawMessage.bb2hex;

import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

/**
 * @author renarj
 */
public class ApplicationCommandEvent implements TransactionEvent {

    private int nodeId;
    private int endpointId;
    private CommandClass commandClass;

    private byte[] payload;

    public ApplicationCommandEvent(int nodeId, int endpointId, CommandClass commandClass, byte[] payload) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.commandClass = commandClass;
        this.payload = payload;
    }

    @Override
    public boolean isTransactionCompleted() {
        return false;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getEndpointId() {
        return endpointId;
    }

    public CommandClass getCommandClass() {
        return commandClass;
    }

    public byte[] getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "ApplicationCommandEvent{" +
                "nodeId=" + nodeId +
                ", endpointId=" + endpointId +
                ", commandClass=" + commandClass.getLabel() +
                ", payload=" + bb2hex(payload) +
                '}';
    }
}
