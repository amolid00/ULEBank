/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.history;

import java.text.SimpleDateFormat;
import java.util.Date;

import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author roobre
 */
public final class TransactionHandlerProvider {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyyMMdd");
    private static String lastTimestamp = "";
    private static long currentId = 0;

    /**
     *
     * @return
     */
    public static synchronized Handler getTransactionHandler() {
        final String timestamp = TransactionHandlerProvider.FORMAT
                .format(new Date());
        if (timestamp.compareTo(TransactionHandlerProvider.lastTimestamp) != 0) {
            TransactionHandlerProvider.lastTimestamp = timestamp;
            TransactionHandlerProvider.currentId = 0;
        }

        return new TransactionHandler(++TransactionHandlerProvider.currentId,
                TransactionHandlerProvider.lastTimestamp);
    }

    private TransactionHandlerProvider() {
    }
}