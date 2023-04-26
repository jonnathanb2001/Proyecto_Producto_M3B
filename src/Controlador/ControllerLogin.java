/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Proyecto_pv.ManagerFactory;
import Vista.Fr_administracion;
import Vista.fr_inicio;
import Vista.interno.fr_persona;
import Vista.interno.fr_producto;
import Vista.interno.fr_usuario;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;



/**
 *
 * @author HP
 */
public class ControllerLogin {

    private ManagerFactory manage;
    private fr_inicio vista;
    private UsuarioJpaController modelo;

    Persona p = new Persona();
    Fr_administracion veradmin = new Fr_administracion();
    fr_persona verpersona = new fr_persona();
    fr_usuario verusuario = new fr_usuario();
    fr_producto verproducto = new fr_producto();

    public ControllerLogin(ManagerFactory manage, fr_inicio vista, UsuarioJpaController modelo) {
        this.manage = manage;
        this.vista = vista;
        this.modelo = modelo;
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
        iniciarControl();
    }

    public void iniciarControl() {
        vista.getjButtonEntrar().addActionListener(l -> controlLogin());
        vista.getjButtonCerrar().addActionListener(l -> cerrarventana());
        veradmin.getjButtonSalirAdmin().addActionListener(l -> regresarlogin());
        

    }

    public void controlLogin() {

        String usuario = vista.getjTextFieldUsuario().getText();
        String clave = new String(vista.getjPasswordFieldClave().getPassword());
        try {
        Usuario user = modelo.buscarUsuario(usuario, clave);
        
        if (user != null) {
             Resouces.success("AVISO!!", "USUARIO CORRECTO");
            JOptionPane.showMessageDialog(vista, "Usuario Correcto " + "\n" + user.getIdpersona().toString2());
            veradmin.setVisible(true);
            new ControllerAdministrador(veradmin, manage);
            veradmin.setLocationRelativeTo(null);
        } else {
            Resouces.warning("ERROR!!!", "USUARIO INCORRECTO");
        }
        }catch(PersistenceException e){
             Resouces.warning("ERROR!!!", "USUARIO INCORRECTO");
        }
    }

    public void cerrarventana() {
        System.exit(0);
    }

    public void regresarlogin() {
        vista.setVisible(true);
        veradmin.setVisible(false);

    }

    public void crearpersona() {
        verpersona.setVisible(true);
   
    }

    public void crearusuario() {
        verusuario.setVisible(true);

    }

    public void crearproducto() {
        verproducto.setVisible(true);

    }

    public void regresarper_admin() {
        veradmin.setVisible(true);
        verpersona.setVisible(false);

    }

    public void regresarusu_admin() {
        veradmin.setVisible(true);
        verusuario.setVisible(false);

    }

    public void regresarpro_admin() {
        veradmin.setVisible(true);
        verproducto.setVisible(false);

    }
}
