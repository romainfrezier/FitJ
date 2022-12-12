package com.fitj.interfaces;

import com.fitj.classes.Paiement;

import java.util.*;

/**
 * 
 */
public interface PaymentReceiver {

    /**
     * @param content
     */
    public void receive(Paiement content);

}