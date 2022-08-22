/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Modelo.PersonaJpaController;
import Proyecto_pv.ManagerFactory;
import Vista.interno.fr_usuario;
import java.awt.Dimension;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.exceptions.NonexistentEntityException;

/**
 *
 * @author HP
 */
public class ControllerUsuario {

    ModeloTablaUsuario modeloTabla;
    fr_usuario vista;
    ManagerFactory manage;
    UsuarioJpaController modeloUsuario;
    Usuario usuario;
    JDesktopPane panelEscritorio;
    ListSelectionModel listaModeloPersona;

    public ControllerUsuario(fr_usuario vista, ManagerFactory manage, UsuarioJpaController modeloUsuario, JDesktopPane panelEscritorio) {

        this.manage = manage;
        this.modeloUsuario = modeloUsuario;
        this.panelEscritorio = panelEscritorio;
        this.modeloTabla = new ModeloTablaUsuario();
        this.modeloTabla.setFilas(modeloUsuario.findUsuarioEntities());

        if (ControllerAdministrador.vu == null) {
            ControllerAdministrador.vu = new fr_usuario();
            this.vista = ControllerAdministrador.vu;
            this.panelEscritorio.add(this.vista);
            this.vista.getjTableDatosUsuario().setModel(modeloTabla);
            this.vista.show();
            controleventos();
            cargarComboBox();
            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vista.getSize();
            this.vista.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        } else {
            ControllerAdministrador.vu.show();

        }

    }

    public void controleventos() {
        this.vista.getjButtonCrear().addActionListener(l -> guardarUsuario());
        this.vista.getjButtonEditar().addActionListener(l -> editarUsuario());
        this.vista.getjButtonLimpiarDatos().addActionListener(l -> limpiar());
        this.vista.getjButtonEliminar().addActionListener(l -> eliminarUsuario());
        this.vista.getjButtonLimpiarDatos().addActionListener(l -> limpiar());
        //busqueda
        this.vista.getjButtonBuscarCriterio().addActionListener(l -> buscarusuario());
        this.vista.getjRadioButtonMostrarTodo().addActionListener(l -> buscarusuario());

        this.vista.getjButtonLimpiarCriterio().addActionListener(l -> limpiarbuscador());

        this.vista.getjTableDatosUsuario().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaModeloPersona = this.vista.getjTableDatosUsuario().getSelectionModel();
        listaModeloPersona.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    usuarioSeleccionado();
                }

            }
        });
        this.vista.getjButtonEditar().setEnabled(false);
        this.vista.getjButtonEliminar().setEnabled(false);

    }

    public void guardarUsuario() {
        usuario = new Usuario();
        usuario.setUsuario(this.vista.getjTextFieldNombre().getText());
        usuario.setClave(this.vista.getjPasswordFieldClave().getText());
        usuario.setIdpersona((Persona) this.vista.getjComboBoxPersonas().getSelectedItem());
        modeloUsuario.create(usuario);
        modeloTabla.agregar(usuario);
        JOptionPane.showMessageDialog(vista, "Usuario creado correctamente");
    }

    public void usuarioSeleccionado() {
        if (this.vista.getjTableDatosUsuario().getSelectedRow() != -1) {
            usuario = modeloTabla.getFilas().get(this.vista.getjTableDatosUsuario().getSelectedRow());
            this.vista.getjTextFieldNombre().setText(usuario.getUsuario());
            this.vista.getjPasswordFieldClave().setText(usuario.getClave());
            this.vista.getjComboBoxPersonas().setSelectedItem(usuario.getIdpersona());

            //control de botones
            this.vista.getjButtonEditar().setEnabled(true);
            this.vista.getjButtonEliminar().setEnabled(true);
            this.vista.getjButtonCrear().setEnabled(false);
        }
    }

    public void editarUsuario() {
        if (usuario != null) {
            usuario.setUsuario(this.vista.getjTextFieldNombre().getText());
            usuario.setClave(this.vista.getjPasswordFieldClave().getText());
            usuario.setIdpersona((Persona) this.vista.getjComboBoxPersonas().getSelectedItem());
            try {
                modeloUsuario.edit(usuario);
            } catch (Exception e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
            JOptionPane.showMessageDialog(vista, "Usuario editado correctamente");
            limpiar();
        }
    }

    public void eliminarUsuario() {
        if (usuario != null) {
            try {
                modeloUsuario.destroy(usuario.getIdusario());
            } catch (NonexistentEntityException e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
            modeloTabla.eliminar(usuario);
            modeloTabla.actualizar(usuario);
            JOptionPane.showMessageDialog(vista, "Usuario eliminado correctamente");
        }
    }

    public void buscarusuario() {
        if (this.vista.getjRadioButtonMostrarTodo().isSelected()) {
            modeloTabla.setFilas(modeloUsuario.findUsuarioEntities());
            modeloTabla.fireTableDataChanged();
        } else {
            if (!this.vista.getjTextFieldBuscarCriterio().getText().equals("")) {
                modeloTabla.setFilas(modeloUsuario.buscarUsuario(this.vista.getjTextFieldBuscarCriterio().getText()));
                modeloTabla.fireTableDataChanged();
            } else {
                limpiarbuscador();
            }
        }

    }

    public void limpiarbuscador() {
        this.vista.getjTextFieldBuscarCriterio().setText("");
        modeloTabla.setFilas(modeloUsuario.findUsuarioEntities());
        modeloTabla.fireTableDataChanged();
    }

    public void limpiar() {
        this.vista.getjTextFieldNombre().setText("");
        this.vista.getjPasswordFieldClave().setText("");
        this.vista.getjComboBoxPersonas().setSelectedIndex(0);

    }

    public void cargarComboBox() {
        try {
            Vector v = new Vector();
            v.addAll(new PersonaJpaController(manage.getEntityManagerFactory()).findPersonaEntities());
            this.vista.getjComboBoxPersonas().setModel(new DefaultComboBoxModel(v));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Capturando errores cargando combobox");
        }
    }

}
