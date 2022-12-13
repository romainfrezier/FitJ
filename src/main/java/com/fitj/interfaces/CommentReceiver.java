package com.fitj.interfaces;

import com.fitj.classes.Avis;

import java.util.*;

/**
 * Interface qui définit une méthode pour recevoir un commentaire (ou un avis).
 * @see Receiver
 */
public interface CommentReceiver {

    /**
     * Reçoit un commentaire.
     *
     * @param content Avis, le contenu du commentaire
     */
    public void receive(Avis content);

}