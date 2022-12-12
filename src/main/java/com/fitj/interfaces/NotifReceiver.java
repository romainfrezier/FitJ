package com.fitj.interfaces;

import com.fitj.classes.Notification;

import java.util.*;

/**
 * 
 */
public interface NotifReceiver {

    /**
     * @param content
     */
    public void receive(Notification content);

}