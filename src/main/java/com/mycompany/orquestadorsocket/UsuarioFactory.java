/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orquestadorsocket;

import com.mycompany.almacen.Almacenaje;
import com.mycompany.entity.Medico;
import com.mycompany.entity.Paciente;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author mata
 */
public class UsuarioFactory {
    
    public static void generarMedico(String nombre,Session sesion,String especialidad,boolean estado){
        Almacenaje.agregarMedico(new Medico(nombre,sesion,especialidad,estado));
    }
    
    public static void generarPaciente(String nombre,Session sesion){
        Almacenaje.agregarPaciente(new Paciente(nombre,sesion));
    }
    
}
