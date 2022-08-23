/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.interno.fr_persona;
import java.awt.Dimension;
import javax.swing.JDesktopPane;
import Proyecto_pv.*;
import Modelo.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.exceptions.NonexistentEntityException;

/**
 *
 * @author HP
 */
public class ControllerPersona {

    ModeloTablaPersona modeloTabla;
    fr_persona vista;
    ManagerFactory manage;
    PersonaJpaController modeloPersona;
    Persona persona;
    JDesktopPane panelEscritorio;
    ListSelectionModel listaModeloPersona;

    public ControllerPersona(fr_persona vista, ManagerFactory manage, PersonaJpaController modeloPersona, JDesktopPane panelEscritorio) {

        this.manage = manage;
        this.modeloPersona = modeloPersona;
        this.panelEscritorio = panelEscritorio;
        this.modeloTabla = new ModeloTablaPersona();
        this.modeloTabla.setFilas(modeloPersona.findPersonaEntities());

        if (ControllerAdministrador.vp == null) {
            ControllerAdministrador.vp = new fr_persona();
            this.vista = ControllerAdministrador.vp;

            this.panelEscritorio.add(this.vista);
            this.vista.getjTableDatosPersonas().setModel(modeloTabla);
            this.vista.show();
            controleventos();
            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vista.getSize();
            this.vista.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        } else {
            ControllerAdministrador.vp.show();

        }

    }

    public void controleventos() {

        this.vista.getjButtonCrear().addActionListener(l -> guardarPersona());
        this.vista.getjButtonEditar().addActionListener(l -> editarPersona());
        this.vista.getjButtonEliminar().addActionListener(l -> eliminarPersona());
        this.vista.getjButtonLimpiarDatos().addActionListener(l -> limpiar());
        //busqueda
        this.vista.getjButtonBuscarCriterio().addActionListener(l -> buscarpersona());
        this.vista.getjRadioButtonMostrarTodo().addActionListener(l -> buscarpersona());
        
        this.vista.getjButtonLimpiarCriterio().addActionListener(l -> limpiarbuscador());
        
        this.vista.getjTableDatosPersonas().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaModeloPersona = this.vista.getjTableDatosPersonas().getSelectionModel();
        listaModeloPersona.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    personaSeleccionada();
                }

            }

        });
        this.vista.getjButtonEditar().setEnabled(false);
        this.vista.getjButtonEliminar().setEnabled(false);
        

    }

    public void personaSeleccionada() {
        if (this.vista.getjTableDatosPersonas().getSelectedRow() != -1) {
            persona = modeloTabla.getFilas().get(this.vista.getjTableDatosPersonas().getSelectedRow());
            this.vista.getjTextFieldNombre().setText(persona.getNombre());
            this.vista.getjTextFieldApellido().setText(persona.getApellido());
            this.vista.getjTextFieldCedula().setText(persona.getCedula());
            this.vista.getjTextFieldCelular().setText(persona.getCelular());
            this.vista.getjTextFieldCorreo().setText(persona.getCorreo());
            this.vista.getjTextFieldDireccion().setText(persona.getDireccion());
            //control de botones
            this.vista.getjButtonEditar().setEnabled(true);
            this.vista.getjButtonEliminar().setEnabled(true);
            this.vista.getjButtonCrear().setEnabled(false);
        }
    }

    public void guardarPersona() {
        persona = new Persona();
        persona.setNombre(this.vista.getjTextFieldNombre().getText());
        persona.setApellido(this.vista.getjTextFieldApellido().getText());
        persona.setCedula(this.vista.getjTextFieldCedula().getText());
        persona.setCelular(this.vista.getjTextFieldCelular().getText());
        persona.setCorreo(this.vista.getjTextFieldCorreo().getText());
        persona.setDireccion(this.vista.getjTextFieldDireccion().getText());
        modeloPersona.create(persona);
        modeloTabla.agregar(persona);
        Resouces.success("AVISO!!!!", "PERSONA GUARDADA");
        
    }

    public void editarPersona() {
        if (persona != null) {
            persona.setNombre(this.vista.getjTextFieldNombre().getText());
            persona.setApellido(this.vista.getjTextFieldApellido().getText());
            persona.setCedula(this.vista.getjTextFieldCedula().getText());
            persona.setCelular(this.vista.getjTextFieldCelular().getText());
            persona.setCorreo(this.vista.getjTextFieldCorreo().getText());
            persona.setDireccion(this.vista.getjTextFieldDireccion().getText());
            try {
                modeloPersona.edit(persona);
            } catch (Exception e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
            JOptionPane.showMessageDialog(vista, "Persona editada correctamente");
            limpiar();
        }
    }

    public void eliminarPersona() {
        if (persona != null) {
            try {
                modeloPersona.destroy(persona.getIdpersona());
            } catch (NonexistentEntityException e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
            modeloTabla.eliminar(persona);
            modeloTabla.actualizar(persona);
            JOptionPane.showMessageDialog(vista, "Persona eliminada correctamente");
        }
    }

    public void limpiar() {
        this.vista.getjTextFieldNombre().setText("");
        this.vista.getjTextFieldApellido().setText("");
        this.vista.getjTextFieldCedula().setText("");
        this.vista.getjTextFieldCelular().setText("");
        this.vista.getjTextFieldCorreo().setText("");
        this.vista.getjTextFieldDireccion().setText("");
        persona = null;
        this.vista.getjButtonEditar().setEnabled(false);
        this.vista.getjButtonEliminar().setEnabled(false);
        this.vista.getjButtonCrear().setEnabled(true);
        this.vista.getjTableDatosPersonas().getSelectionModel().clearSelection();

    }

    public void buscarpersona() {
        if (this.vista.getjRadioButtonMostrarTodo().isSelected()) {
            modeloTabla.setFilas(modeloPersona.findPersonaEntities());
            modeloTabla.fireTableDataChanged();
        } else {
            if (!this.vista.getjTextFieldBuscarCriterio().getText().equals("")) {
                modeloTabla.setFilas(modeloPersona.buscarPersona(this.vista.getjTextFieldBuscarCriterio().getText()));
                modeloTabla.fireTableDataChanged();
            } else {
                limpiarbuscador();
            }
        }

    }

    public void limpiarbuscador() {
        this.vista.getjTextFieldBuscarCriterio().setText("");
        modeloTabla.setFilas(modeloPersona.findPersonaEntities());
        modeloTabla.fireTableDataChanged();
    }

}
