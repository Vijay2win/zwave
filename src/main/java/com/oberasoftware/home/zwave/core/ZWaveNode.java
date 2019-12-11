package com.oberasoftware.home.zwave.core;

import com.oberasoftware.home.zwave.api.events.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.api.events.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.core.impl.ZWaveNodeImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author renarj
 */
public interface ZWaveNode {
    int getNodeId();

    default NodeStatus getNodeStatus() {
        return NodeStatus.INITIALIZING;
    }

    default NodeAvailability getAvailability() {
        return NodeAvailability.UNKNOWN;
    }

    Optional<NodeIdentifyEvent> getNodeInformation();

    Optional<ManufactorInfoEvent> getManufactorInfoEvent();

    Set<CommandClass> getCommandClasses();

    List<Integer> getEndpoints();

    Map<String, Object> getNodeProperties();

    Object getProperty(String key);

    ZWaveNodeImpl cloneNode();
}
