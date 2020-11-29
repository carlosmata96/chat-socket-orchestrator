/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.almacen;



import com.mycompany.entity.Canal;
import com.mycompany.entity.Medico;
import com.mycompany.entity.Paciente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mata
 */
public class Almacenaje {
//  Lista de pacientes que se tendran    
    public static List<Paciente>pacientes=new ArrayList<Paciente>();
// Lista de Medicos que se tendran
    public static List<Medico>medicos=new ArrayList<Medico>();
//  Lista de canales de comunicación activos
    public static List<Canal>canales=new ArrayList<Canal>();
//  Lista de colores para usuarios disponibles
    public static String[] colores;   
    
    static{
        colores=new String[10];
        colores[0]="blue-text";
        colores[1]="red-text";
        colores[2]="green-text";
        colores[3]="purple-text";
        colores[4]="teal-text";
        colores[5]="black-text";
        colores[6]="orange-text";
        colores[7]="grey-text";
        colores[8]="brown-text";
    }

        
    public static void agregarPaciente(Paciente paciente){
        pacientes.add(paciente);
    }
    
    public static void agregarMedico(Medico medico){
        medicos.add(medico);
    }
    
    public static Integer agregarCanal(Canal canal){
        canales.add(canal);
        System.out.print("mira los canales:"+canales.size());
        return canales.size();
    }
    
    public static List<Paciente> getPacientes() {
        return pacientes;
    }
    

    public static List<Medico> getMedicos() {
        return medicos;
    }
    
     public static List<Canal> getCanales() {
        return canales;
    }
     
   
}
