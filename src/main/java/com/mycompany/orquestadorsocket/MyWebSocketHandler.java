/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orquestadorsocket;

import com.mycompany.almacen.Almacenaje;
import com.mycompany.entity.Canal;
import org.json.*;
import java.io.IOException;
import javax.websocket.server.ServerEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 *
 * @author mata
 */
@WebSocket
public class MyWebSocketHandler {
    
    
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }
    
    @OnWebSocketError
    public void onError(Session session,Throwable t) {
        System.out.println("Error: " + t.getMessage());
        
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        System.out.println("Connect: " + session.getRemoteAddress().getPort());
        try {
            session.getRemote().sendString("Hello Webbrowser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @OnWebSocketMessage
    public void onMessage(Session session, byte buf[], int offset, int length){
        System.out.print("Tamaño::"+length);
    }

    @OnWebSocketMessage
    public void onMessage(Session session,String message) throws IOException {
        
       JSONObject obj=new JSONObject(message);
       String accion = obj.getString("accion");
       switch(accion){
           case "saludo": 
                //Saludo
               
                 if(obj.getString("rol").equals("Doctor") && obj.getBoolean("presentacion")){
                     if(CanalManager.verificarMedico(obj,session)){
                        UsuarioFactory.generarMedico(obj.getString("nombre"),session,obj.getString("especialidad"),obj.getBoolean("estado"));
                     }
                    
                    System.out.println("Message: " + Almacenaje.getMedicos().size());
                    }
           break;
           case "mensaje": 
               //Enviar Mensaje
               CanalManager.enviar(obj);
           break;
           case "imagen":
               //En caso de enviar una imagen
               CanalManager.enviar(obj);
            break;
           case "cerrar":
               //Cerrar
            break;
           case "agregarDoctor":
               //agregar Doctor Auxiliar
               CanalManager.agregarAuxiliar(obj);
               break;
           case "obtenerDoctores":
               CanalManager.obtenerAyudante(obj);
               break;
           case "agregarPaciente":
               CanalManager.agregarPaciente(obj,session);
               break;
           case "agregarDoctorPrimario":
               CanalManager.agregarDoctor(obj);
               break;
           case "cambiarEstado":
               CanalManager.cambiarEstado(obj);
               break;
           case "confirmarDoctor":
               CanalManager.aceptarAyudante(obj);
               break;
           case "cancelar":
               CanalManager.cancelarConsulta(obj);
               break;
          
          
               
           
           
       }   
    }
}
