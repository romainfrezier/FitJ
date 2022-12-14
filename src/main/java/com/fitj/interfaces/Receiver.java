package com.fitj.interfaces;

import java.util.*;

/**
 * Interface générique qui définit une méthode pour recevoir un objet.
 * @author Paul Merceur
 */
public interface Receiver {

    /**
     * Reçoit un Object.
     *
     * @param content Object, le contenu de l'objet
     */
    public void receive(Object content);

}