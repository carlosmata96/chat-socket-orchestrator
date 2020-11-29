/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author mata
 */
public class Usuario {
    private String nombre;
    private Session sesion;
    private String color;

    public Usuario() {
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
    }
    public Usuario(String nombre, Session sesion) {
        this.nombre = nombre;
        this.sesion = sesion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
     public Session getSesion() {
        return sesion;
    }

    public void setSesion(Session sesion) {
        this.sesion = sesion;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
   
   

    
}
