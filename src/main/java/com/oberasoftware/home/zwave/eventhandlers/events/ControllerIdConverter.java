package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.events.TransactionIdEvent;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.core.ZWaveRawMessage;

import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class ControllerIdConverter implements ZWaveConverter {

    @SupportsConversion(controllerMessage = ControllerMessageType.MemoryGetId)
    @EventSubscribe
    public TransactionIdEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        byte[] payload = source.getMessage();

        int homeId = ((payload[0]) << 24) |
                ((payload[1]) << 16) |
                ((payload[2]) << 8) |
                (payload[3]);
        int controllerId = payload[4];

        return new TransactionIdEvent(homeId, controllerId);
    }
}
