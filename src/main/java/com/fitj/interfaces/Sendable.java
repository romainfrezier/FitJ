package com.fitj.interfaces;

import com.fitj.classes.Client;

import java.util.*;

/**
 * 
 */
public interface Sendable {

    /**
     * @param destinataire
     */
    public void send(Client destinataire);

}