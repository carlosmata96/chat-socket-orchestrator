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
public class Paciente extends Usuario{

    public Paciente() {
    }

    public Paciente(String nombre, Session sesion) {
        super(nombre, sesion);
    }
    
    
}
