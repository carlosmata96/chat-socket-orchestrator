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
public class Medico extends Usuario{
    
    private boolean estado;
    private String especialidad;

   

   
    public Medico(String nombre,Session sesion,String especialidad,boolean estado) {
        super(nombre,sesion);
        System.out.print("nombre->"+nombre+"--puerto->"+sesion.getRemoteAddress().getPort()+"--especialidad->"+especialidad+"--Estado->"+estado);
        this.especialidad=especialidad;
        this.estado=estado;
    }
    
     public Medico() {
         super();
    }
    
    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
     public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
