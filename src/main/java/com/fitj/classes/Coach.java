package com.fitj.classes;

import com.fitj.interfaces.DemandReceiver;
import com.fitj.interfaces.PaymentReceiver;

import java.util.*;

/**
 * 
 */
public class Coach extends Client implements PaymentReceiver, DemandReceiver {

    /**
     * Default constructor
     */
    public Coach() {
    }

    /**
     * @param content
     */
    public void receive(Paiement content) {
        // TODO implement here
    }

    /**
     * @param content
     */
    public void receive(Demande content) {
        // TODO implement here
    }

    @Override
    public void receive(Notification content) {

    }

    @Override
    public void receive(Produit content) {

    }
}