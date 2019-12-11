package com.oberasoftware.home.zwave.threading;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oberasoftware.home.zwave.api.LocalZwaveSession;
import com.oberasoftware.home.zwave.api.ZWaveSession;
import com.oberasoftware.home.zwave.api.actions.SwitchAction;
import com.oberasoftware.home.zwave.api.actions.SwitchAction.STATE;
import com.oberasoftware.home.zwave.api.events.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.messages.types.GenericDeviceClass;
import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.ZWaveNode;

/**
 * @author renarj
 */
public class LocalZwaveSessionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalZwaveSessionTest.class);
    private static final ZWaveSession SESSION = new LocalZwaveSession().connect();

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting test application");

        while (!SESSION.isNetworkReady()) {
            LOGGER.info("Network not ready yet, sleeping");
            sleepUninterruptibly(1, TimeUnit.SECONDS);
        }

        LOGGER.info("Network is ready");
        sleepUninterruptibly(5, SECONDS);

        int level = Integer.parseInt(args[0]);
        for (ZWaveNode node : SESSION.getDeviceManager().getNodes()) {
            while (node.getAvailability() != NodeAvailability.AVAILABLE) {
                SESSION.getDeviceManager().setNodeAvailability(node.getNodeId(), NodeAvailability.AVAILABLE);
                sleepUninterruptibly(1, TimeUnit.SECONDS);
                LOGGER.info("Node.toString() -> {}, hence sleeping for 1 Second.", node);

                node = SESSION.getDeviceManager().getNode(node.getNodeId());
            }

            LOGGER.info("Node.toString() -> {}", node);
            Optional<NodeIdentifyEvent> nodeInfo = node.getNodeInformation();
            if (nodeInfo.isPresent() && nodeInfo.get().getGenericDeviceClass() == GenericDeviceClass.MULTILEVEL_SWITCH) {
                if (level == 0)
                    SESSION.doAction(new SwitchAction(node.getNodeId(), 0, STATE.OFF, level));
                else
                    SESSION.doAction(new SwitchAction(node.getNodeId(), 0, STATE.ON, level));
            } else if (nodeInfo.isPresent() && nodeInfo.get().getGenericDeviceClass() == GenericDeviceClass.BINARY_SWITCH) {
                if (level == 0)
                    SESSION.doAction(new SwitchAction(node.getNodeId(), STATE.OFF));
                else 
                    SESSION.doAction(new SwitchAction(node.getNodeId(), STATE.ON));
            }
        }

        LOGGER.info("Actions done");
        sleepUninterruptibly(3, SECONDS);
        System.exit(0);
    }
}
