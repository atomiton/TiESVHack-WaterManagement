package com.atomiton.watermanagement.ngo;

import com.atomiton.watermanagement.ngo.util.WebSocketClient;

/**
 * The purpose of this class is to start listening events over websocket.
 * @author pramodmali
 */
public class WebSocket 
{
    /**
     * Method to start listening events over websocket.
     * @param args
     */
    public static void main( String[] args )
    {
    	NGOServices ngoServices = new NGOServices();
        ngoServices.installedAllInstumentsAndHireAttendent();
    	WebSocketClient.startSubscription();
    }
}
