package com.oberasoftware.home.zwave;

import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.actions.ZWaveAction;
import com.oberasoftware.home.zwave.api.events.TransactionEvent;

/**
 * @author renarj
 */
public interface TransactionManager {
    int startAction(ZWaveAction action) throws HomeAutomationException;

    void completeTransaction(TransactionEvent transactionEvent) throws HomeAutomationException;

    void cancelTransaction() throws HomeAutomationException;
}
