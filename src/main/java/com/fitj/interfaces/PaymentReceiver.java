package com.fitj.interfaces;

import com.fitj.classes.Paiement;

import java.util.*;

/**
 * Interface qui définit une méthode pour recevoir un paiement.
 * @see Receiver
 * @author Paul Merceur
 */
public interface PaymentReceiver {

    /**
     * Reçoit le paiement.
     *
     * @param content Paiement, le contenu du paiement
     */
    public void receive(Paiement content);

}