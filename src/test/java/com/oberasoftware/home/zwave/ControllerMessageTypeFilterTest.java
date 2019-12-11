package com.oberasoftware.home.zwave;

import com.oberasoftware.base.event.impl.HandlerEntryImpl;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.filters.ControllerMessageTypeFilter;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.core.ZWaveRawMessage;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author renarj
 */
public class ControllerMessageTypeFilterTest {
    @Test
    public void testControllerFilter() throws Exception {
        ControllerMessageTypeFilter filter = new ControllerMessageTypeFilter();

        Method handlerMethod = TestHandler.class.getMethod("receive", ZWaveRawMessage.class);

        ZWaveRawMessage applicationCommandMessage = new ZWaveRawMessage(ControllerMessageType.ApplicationCommandHandler, MessageType.Request);
        ZWaveRawMessage sendDataCommandMessage = new ZWaveRawMessage(ControllerMessageType.SendData, MessageType.Request);

        assertThat(filter.isFiltered(applicationCommandMessage, new HandlerEntryImpl(null, handlerMethod)), is(false));

        assertThat(filter.isFiltered(sendDataCommandMessage, new HandlerEntryImpl(null, handlerMethod)), is(true));
    }

    private class TestHandler {
        @SupportsConversion(controllerMessage = ControllerMessageType.ApplicationCommandHandler)
        public void receive(ZWaveRawMessage message) {

        }
    }


}
