package com.oberasoftware.home.zwave.api.events;

/**
 * @author renarj
 */
public interface TransactionEvent extends ZWaveEvent {
    boolean isTransactionCompleted();
}
