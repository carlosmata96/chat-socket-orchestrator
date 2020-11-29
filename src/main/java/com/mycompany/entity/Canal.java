/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author mata
 */
public class Canal {
    private Paciente paciente;
    private Medico medicoPrincipal;
    private Medico medicoAyudante;
    private List<JSONObject>mensajes;

   
    public Canal(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedicoPrincipal() {
        return medicoPrincipal;
    }

    public void setMedicoPrincipal(Medico medicoPrincipal) {
        this.medicoPrincipal = medicoPrincipal;
    }

    public Medico getMedicoAyudante() {
        return medicoAyudante;
    }

    public void setMedicoAyudante(Medico medicoAyudante) {
        this.medicoAyudante = medicoAyudante;
    }
    public void addMensaje(JSONObject obj){
        mensajes.add(obj);
    }
    public List<JSONObject> getMensajes(){
        return this.mensajes;
    }
    
     public void setMensajes(List<JSONObject> mensajes) {
        this.mensajes = mensajes;
    }
    
    
}
