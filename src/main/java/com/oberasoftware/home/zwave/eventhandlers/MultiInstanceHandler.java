package com.oberasoftware.home.zwave.eventhandlers;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.MultiInstanceEndpointAction;
import com.oberasoftware.home.zwave.api.events.NodeUpdatedEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MultiInstanceHandler implements EventHandler {
    private static final Logger LOG = getLogger(MultiInstanceHandler.class);

    @EventSubscribe
    public MultiInstanceEndpointAction receive(NodeUpdatedEvent nodeUpdatedEvent) {
        Set<CommandClass> commandClasses = nodeUpdatedEvent.getNode().getCommandClasses();
        boolean isMultiInstance = commandClasses.stream().anyMatch(c -> c == CommandClass.MULTI_INSTANCE);

        LOG.debug("Node updated: {}, checking for multi instance support: {}", nodeUpdatedEvent.getNodeId(), isMultiInstance);
        if(isMultiInstance) {
            return new MultiInstanceEndpointAction(nodeUpdatedEvent.getNodeId());
        }
        return null;
    }
}
