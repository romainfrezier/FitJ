package com.fitj.interfaces;

import com.fitj.classes.Client;

import java.util.*;

/**
 * Une interface qui définit une méthode pour envoyer un objet à un destinataire.
 * @author Paul Merceur
 */
public interface Sendable {

    /**
     * Envoie un objet à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer l'objet
     */
    public void send(Client destinataire);
}