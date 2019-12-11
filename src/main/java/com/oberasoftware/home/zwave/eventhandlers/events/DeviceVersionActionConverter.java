package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.DeviceVersionAction;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class DeviceVersionActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(DeviceVersionActionConverter.class);

    public static final int VERSION_COMMAND_CLASS_GET = 0x13;

    @EventSubscribe
    public ActionConvertedEvent convert(DeviceVersionAction source, int callbackId) throws HomeAutomationException {
        LOG.info("Sending action: {}", source);
        int nodeId = source.getNodeId();

        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, nodeId)
                .addCommandClass(CommandClass.VERSION)
                .addInt(VERSION_COMMAND_CLASS_GET)
                .addInt(source.getCommandClass().getClassCode())
                .callback(callbackId)
                .construct();
    }
}
