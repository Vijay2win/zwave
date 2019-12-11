package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.BatteryEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static java.lang.Integer.toHexString;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class BatteryConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(BatteryConverter.class);

    private static final int BATTERY_GET = 0x02;
    private static final int BATTERY_REPORT = 0x03;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.BATTERY)
    public BatteryEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Received battery information from node: {}", source.getNodeId());
        byte[] payload = source.getPayload();

        int command = payload[0];
        switch (command) {
            case BATTERY_REPORT:
                int batteryLevel = payload[1];

                LOG.debug("Node: {} battery level report: {}", source.getNodeId(), batteryLevel);
                return new BatteryEvent(source.getNodeId(), batteryLevel);
            case BATTERY_GET:
            default:
                LOG.warn("Battery command: {} not implemented reported by node: {}", toHexString(command), source.getNodeId());
        }

        return null;
    }
}
