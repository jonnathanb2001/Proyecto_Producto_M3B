/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.*;
import Proyecto_pv.*;
import Vista.interno.fr_persona;
import Modelo.*;
import Vista.interno.fr_producto;
import Vista.interno.fr_usuario;

/**
 *
 * @author HP
 */
public class ControllerAdministrador {

    

    Fr_administracion vista;
    ManagerFactory manage;
    
    

    public ControllerAdministrador(Fr_administracion vista, ManagerFactory manage) {
        this.vista = vista;
        this.manage = manage;
        controleventos();
    }

    public void controleventos() {
        this.vista.getjMenuItemPersona().addActionListener(l -> cargarvistaPersona());
        this.vista.getjMenuItemUsuario().addActionListener(l->cargarvistaUsuario());
        this.vista.getjMenuItemProducto().addActionListener(l->cargarvistaProducto());
    }

    public static fr_persona vp;
    

    public void cargarvistaPersona() {
        new ControllerPersona(vp, manage, new PersonaJpaController(manage.getEntityManagerFactory()), this.vista.getjDesktopPaneEscritorio());
    }
    
    public static fr_usuario vu;
    
    public void cargarvistaUsuario() {
        new ControllerUsuario(vu, manage, new UsuarioJpaController(manage.getEntityManagerFactory()), this.vista.getjDesktopPaneEscritorio());
    }
    public static fr_producto vpr;
    
    public void cargarvistaProducto() {
        new ControllerProducto(vpr, manage, new ProductoJpaController(manage.getEntityManagerFactory()), this.vista.getjDesktopPaneEscritorio());
    }

}
