/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orquestadorsocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
/**
 *
 * @author mata
 */
public class Orquestador {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
         Server server = new Server(3000);
        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(MyWebSocketHandler.class);
                factory.getPolicy().setMaxBinaryMessageBufferSize(100000);
                factory.getPolicy().setMaxBinaryMessageSize(100000);
                factory.getPolicy().setMaxTextMessageBufferSize(100000);
                factory.getPolicy().setMaxTextMessageSize(100000);
                
            }
        };
        server.setHandler(wsHandler);
        System.out.print("Dando Start");
        server.start();
        System.out.print("Listo para el Join");
        server.join();
        System.out.print("Despues del JOIN");
    }
}
