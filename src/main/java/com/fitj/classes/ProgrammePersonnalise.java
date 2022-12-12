package com.fitj.classes;

import java.util.*;

/**
 * Cette classe représente un ProgrammePersonnalise, qui est un type de Programme, personnalisé pour un client.
 * @see Programme
 */
public class ProgrammePersonnalise extends Service {

    /**
     * Constructeur par défaut
     */
    public ProgrammePersonnalise() {
    }

    /**
     * Reçoit un commentaire pour le programme personnalisé.
     *
     * @param content Avis, le contenu du commentaire
     */
    @Override
    public void receive(Avis content) {

    }

    /**
     * Envoie le programme personnalisé à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer le programme
     */
    @Override
    public void send(Client destinataire) {

    }
}