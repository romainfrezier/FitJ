package com.fitj.interfaces;

import com.fitj.classes.Demande;

import java.util.*;

/**
 * Interface qui définit une méthode pour recevoir une demande.
 * @see Receiver
 */
public interface DemandReceiver {

    /**
     * Reçoit une demande.
     *
     * @param content Demande, le contenu de la demande
     */
    public void receive(Demande content);

}