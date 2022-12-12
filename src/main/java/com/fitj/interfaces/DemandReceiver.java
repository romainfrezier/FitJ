package com.fitj.interfaces;

import com.fitj.classes.Demande;

import java.util.*;

/**
 * 
 */
public interface DemandReceiver {

    /**
     * @param content
     */
    public void receive(Demande content);

}