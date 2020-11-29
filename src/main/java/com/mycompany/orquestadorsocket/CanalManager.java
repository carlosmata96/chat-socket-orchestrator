/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orquestadorsocket;

import com.mycompany.almacen.Almacenaje;
import com.mycompany.entity.Canal;
import com.mycompany.entity.Medico;
import com.mycompany.entity.Paciente;
import com.mycompany.security.Encriptaciones;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;
import org.eclipse.jetty.websocket.api.Session;
import org.json.*;

/**
 *
 * @author mata
 */
public class CanalManager {
    
//  Genera un nuevo canal de comunicacion    
    public static Canal generarCanal(Paciente paciente) throws IOException{
        Canal canal=new Canal(paciente);
      

        return canal;
    }
//  Mensaje de bienvenida
    public static void mensajeBienvenida(JSONObject obj,Canal canal) throws IOException{
      
       System.out.print("La cantidad de canales es:"+Almacenaje.canales.size()+"  ");
        obj.put("canal", Almacenaje.canales.size()-1);
        obj.put("mensaje", "Se ha iniciado la conversación");
        obj.put("nombre", "Sistema");
        canal.setMensajes(new ArrayList<JSONObject>());
        canal.getPaciente().setColor(Almacenaje.colores[Encriptaciones.generateRamdomNumber(0, 8)]);
        obj.put("color", canal.getPaciente().getColor());
        obj.put("medicos", obtenerDoctores());
        canal.getPaciente().getSesion().getRemote().sendString(obj.toString());
    }
    
//  Elimina un canal    
    public static void eliminarCanal(int index){
        System.out.print("\n Cantidad de canales antes:"+Almacenaje.canales.size());
        Almacenaje.canales.remove(index);
        System.out.print("\n Cantidad de canales despues:"+Almacenaje.canales.size());
    }
    
//  Verifica si hay medicos disponibles    
    public static boolean verificarMedicos(){
        return !Almacenaje.getMedicos().isEmpty(); 
    }
// Verifica si el medico ya se encuentra en la lista tanto de doctores en espera asi como iniciando
    public static boolean verificarMedico(JSONObject obj,Session session) throws IOException{
        for(int i=0;i<Almacenaje.getCanales().size();i++){
            System.out.print("verificando canal:"+i
                    + "\n"+Almacenaje.getCanales().size());
           if(Almacenaje.getCanales().get(i).getMedicoPrincipal()!=null){
           if(Almacenaje.getCanales().get(i).getMedicoPrincipal().getNombre()==null ? obj.getString("nombre") ==null : Almacenaje.getCanales().get(i).getMedicoPrincipal().getNombre().equals(obj.getString("nombre")) ){
                System.out.print("Medico ya se encuentra en comunicación\n");
                Almacenaje.getCanales().get(i).getMedicoPrincipal().setSesion(session);
                obj.put("canal",i);
                enviar(obj);
                return false;
            }
           }
        }
        
        for(int i=0;i<Almacenaje.getCanales().size();i++){
            System.out.print("verificando canal:"+i
                    + "\n"+Almacenaje.getCanales().size());
           if(Almacenaje.getCanales().get(i).getMedicoAyudante()!=null){
           if(Almacenaje.getCanales().get(i).getMedicoAyudante().getNombre() == null ? obj.getString("nombre") == null : Almacenaje.getCanales().get(i).getMedicoAyudante().getNombre().equals(obj.getString("nombre"))){
                System.out.print("Medico ya se encuentra en comunicación\n");
                Almacenaje.getCanales().get(i).getMedicoAyudante().setSesion(session);
                obj.put("canal",i);
                enviar(obj);
                return false;
            }
            }
        }
        for(int i=0;i<Almacenaje.getMedicos().size();i++){
            System.out.print("verificando medico:"+i);
            if(Almacenaje.getMedicos().get(i).getNombre() == null ? obj.getString("nombre") == null : Almacenaje.getMedicos().get(i).getNombre().equals(obj.getString("nombre"))){
                System.out.print("Medico ya se encuentra en cola\n");
                Almacenaje.getMedicos().get(i).setSesion(session);
                Almacenaje.getMedicos().get(i).setEstado(false);
                return false;
            }
            System.out.print("Medico no se encuentra en cola, se agregará");
        }
        return true;
    }
//  Obtiene el canal para la comunicación    
    public static Canal obtenerCanal(Integer index){
        System.out.print("Obteniendo el canal:"+index);
        return Almacenaje.getCanales().get(index);
    }
//    Obtener la lista de medicos disponibles
    public static JSONArray obtenerDoctores() throws IOException{
        List<Medico> medicos=Almacenaje.medicos;
        JSONArray JsonMedicos=new JSONArray();
        for(Medico medico: medicos){
            if(medico.getEstado()){
            JSONObject med=new JSONObject();
            med.put("nombre", medico.getNombre());
            med.put("especialidad", medico.getEspecialidad());
            med.put("id", medicos.indexOf(medico));
            JsonMedicos.put(med);
            }
        }
        return JsonMedicos;
    }
    
//  Eliminar un canal de acuerdo a la session almacenada
    public static void eliminarSession(Session session){
        for (Canal canal : Almacenaje.getCanales()){
            if(canal.getPaciente().getSesion().equals(session) || canal.getMedicoPrincipal().getSesion().equals(session)){
                System.out.print("Se encontro el canal fallido: ");
                canal.getMedicoPrincipal().getSesion().close(500, "Error");
                Integer indexCanal=Almacenaje.getCanales().indexOf(canal);
                eliminarCanal(indexCanal);
                
            }
        }
    }
//  Ver el catalogo de doctores secundarios
    public static void obtenerAyudante(JSONObject obj) throws IOException{
        Canal canal=obtenerCanal(obj.getInt("canal"));
        obj.put("medicos", obtenerDoctores());
        canal.getMedicoPrincipal().getSesion().getRemote().sendString(obj.toString());
    }
// Agrega un nuevo doctor al canal
    public static void agregarAuxiliar(JSONObject obj) throws IOException{
        Canal canal=obtenerCanal(obj.getInt("canal"));
        obj.put("medico", obtenerMedico(obj.getInt("nuevoMedico")));
        canal.getPaciente().getSesion().getRemote().sendString(obj.toString());
//        canal.setMedicoAyudante(Almacenaje.medicos.get(obj.getInt("nuevoMedico")));
//        canal.getPaciente().getSesion().getRemote().sendString(obj.toString());
//        canal.getMedicoPrincipal().getSesion().getRemote().sendString(obj.toString());
//        canal.getMedicoAyudante().setColor(Almacenaje.colores[Encriptaciones.generateRamdomNumber(0, 8)]);
//        obj.put("color", canal.getMedicoAyudante().getColor());
//        obj.put("presentacion", true);
//        obj.put("rol", "Ayudante");
//        canal.getMedicoAyudante().getSesion().getRemote().sendString(obj.toString());
    }
//  Obtiene un JSON del medico seleccionado    
    public static JSONObject obtenerMedico(Integer id){
        Medico medico=Almacenaje.medicos.get(id);
        JSONObject obj=new JSONObject();
        obj.put("nombre", medico.getNombre());
        obj.put("especialidad", medico.getEspecialidad());
        return obj;
    }
//  Aceptar ayudante medico
    public static void  aceptarAyudante(JSONObject obj) throws IOException{
        Canal canal=obtenerCanal(obj.getInt("canal"));
        canal.setMedicoAyudante(Almacenaje.medicos.get(obj.getInt("nuevoMedico")));
        canal.getPaciente().getSesion().getRemote().sendString(obj.toString());
        canal.getMedicoPrincipal().getSesion().getRemote().sendString(obj.toString());
        canal.getMedicoAyudante().setColor(Almacenaje.colores[Encriptaciones.generateRamdomNumber(0, 8)]);
        obj.put("color", canal.getMedicoAyudante().getColor());
        obj.put("presentacion", true);
        obj.put("rol", "Ayudante");
        canal.getMedicoAyudante().getSesion().getRemote().sendString(obj.toString());
        obj.put("rol", "Doctor");
        canal.getMedicoPrincipal().getSesion().getRemote().sendString(obj.toString());
        
    }
//  Agregar un paciente y enviar tambien los doctores disponibles
    public static void agregarPaciente(JSONObject obj,Session sesion) throws IOException{
         Integer indexCanal;
         UsuarioFactory.generarPaciente(obj.getString("nombre"), sesion);
                    Canal canal;
                    System.out.print("Boolean para el paciente:::" +obj.has("canal"));
                        if (obj.has("canal")==false){
                        canal= CanalManager.generarCanal(Almacenaje.getPacientes().get(Almacenaje.getPacientes().size()-1));
                        Almacenaje.agregarCanal(canal);
                        }else{
                            canal=obtenerCanal(obj.getInt("canal"));
                        }
                        indexCanal=Almacenaje.getCanales().size()-1;
                        String message="La conexion se he establecido";
                        System.out.println("Message: " + Almacenaje.getPacientes().size());
                        
                        CanalManager.mensajeBienvenida(obj,canal);
    }
//  Agrega un doctor primario y se lo asigna al paciente
    public static void agregarDoctor(JSONObject obj) throws IOException{
        Canal canal=obtenerCanal(obj.getInt("canal"));
        canal.setMedicoPrincipal(Almacenaje.medicos.remove(obj.getInt("nuevoMedico")));
        canal.getMedicoPrincipal().setColor(Almacenaje.colores[Encriptaciones.generateRamdomNumber(0, 8)]);
        obj.put("color", canal.getMedicoPrincipal().getColor());
        obj.put("presentacion", true);
        canal.getMedicoPrincipal().getSesion().getRemote().sendString(obj.toString());
        obj.put("color", canal.getPaciente().getColor());
        System.out.print("El paciente agrego un medico:"+obj);
        canal.getPaciente().getSesion().getRemote().sendString(obj.toString());
        
    }
//    Cambiar Estado del medico
    public static void cambiarEstado(JSONObject obj){
        for(int i=0;i<Almacenaje.getMedicos().size();i++){
           if(Almacenaje.getMedicos().get(i).getNombre() == null ? obj.getString("nombre") == null : Almacenaje.getMedicos().get(i).getNombre().equals(obj.getString("nombre"))){
               Almacenaje.getMedicos().get(i).setEstado(obj.getBoolean("estado"));
           }
        }   
    }
//  Cancela la consulta, y elimina el doctor de el canal
    public static void cancelarConsulta(JSONObject obj){
        Canal canal=obtenerCanal(obj.getInt("canal"));
        System.out.print(obj.getString("rol"));
        if(obj.getString("rol").equals("Doctor")){
            System.out.print("Cantidad de Medicos antes:"+Almacenaje.medicos.size());
            Almacenaje.agregarMedico(canal.getMedicoPrincipal());
            System.out.print("Cantidad de Medicos despues:"+Almacenaje.medicos.size());
            eliminarCanal(obj.getInt("canal"));
            
        }else if(obj.get("rol")=="Ayudante"){
            System.out.print("\n Eliminando ayudante de canal:"+obj.getInt("canal"));
            Almacenaje.medicos.add(canal.getMedicoAyudante());
            canal.setMedicoAyudante(null);
        }
        
    }
    
    
   
//    Envia un mensaje normal
    public static void enviar(JSONObject obj) throws IOException{
        Canal canal=obtenerCanal(obj.getInt("canal"));
        
        if(obj.getString("rol").equals("Doctor")){
            obj.put("color", canal.getMedicoPrincipal().getColor());
        }else if(obj.getString("rol").equals("Paciente")){
            obj.put("color", canal.getPaciente().getColor());
        }else{
            obj.put("color", canal.getMedicoAyudante().getColor());
        }
        canal.addMensaje(obj);
        canal.getPaciente().getSesion().getRemote().sendString(obj.toString());
       
        canal.getMedicoPrincipal().getSesion().getRemote().sendString(obj.toString());
        
        //Caso de enviar a ayudante
        if(canal.getMedicoAyudante()!=null){
        canal.getMedicoAyudante().getSesion().getRemote().sendString(obj.toString());
        }
    }
    
    
    
    
}
