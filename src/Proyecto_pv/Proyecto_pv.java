/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto_pv;

import Controlador.ControllerLogin;
import Modelo.UsuarioJpaController;
import Vista.fr_inicio;

/**
 *
 * @author HP
 */
public class Proyecto_pv {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ManagerFactory manager = new ManagerFactory();
        fr_inicio vista = new fr_inicio();
        UsuarioJpaController modelo= new UsuarioJpaController(manager.getEntityManagerFactory());
        ControllerLogin controlador = new ControllerLogin(manager,vista,modelo);
        
    }
    
}
