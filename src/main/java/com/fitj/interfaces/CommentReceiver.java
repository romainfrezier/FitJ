package com.fitj.interfaces;

import com.fitj.classes.Avis;

import java.util.*;

/**
 * 
 */
public interface CommentReceiver {

    /**
     * @param content
     */
    public void receive(Avis content);

}