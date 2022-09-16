/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Proyecto_pv.ManagerFactory;
import Vista.interno.*;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.exceptions.NonexistentEntityException;

/**
 *
 * @author HP
 */
public class ControllerProducto {

    ModeloTablaProducto modeloTabla;
    fr_producto vista;
    ManagerFactory manage;
    ProductoJpaController modeloProducto;
    Producto producto;
    JDesktopPane panelEscritorio;
    ListSelectionModel listaModeloPersona;

    public ControllerProducto(fr_producto vista, ManagerFactory manage, ProductoJpaController modeloProducto, JDesktopPane panelEscritorio) {

        this.manage = manage;
        this.modeloProducto = modeloProducto;
        this.panelEscritorio = panelEscritorio;
        this.modeloTabla = new ModeloTablaProducto();
        this.modeloTabla.setFilas(modeloProducto.findProductoEntities());

        if (ControllerAdministrador.vpr == null) {
            ControllerAdministrador.vpr = new fr_producto();
            this.vista = ControllerAdministrador.vpr;
            this.panelEscritorio.add(this.vista);
            this.vista.getjTableDatosProducto().setModel(modeloTabla);
            this.vista.show();
            controleventos();
            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vista.getSize();
            this.vista.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        } else {
            ControllerAdministrador.vpr.show();

        }

    }

    public void controleventos() {

        this.vista.getjButtonCrear().addActionListener(l -> guardarProducto());
        this.vista.getjButtonEditar().addActionListener(l -> editarProducto());
        this.vista.getjButtonLimpiarDatos().addActionListener(l -> limpiar());
        this.vista.getjButtonEliminar().addActionListener(l -> eliminarProducto());
        this.vista.getjButtonLimpiarDatos().addActionListener(l -> limpiar());
        //busqueda
        this.vista.getjButtonBuscarCriterio().addActionListener(l -> buscarproducto());
        this.vista.getjRadioButtonMostrarTodo().addActionListener(l -> buscarproducto());

        this.vista.getjButtonLimpiarCriterio().addActionListener(l -> limpiarbuscador());
        this.vista.getjButtonReporteGeneral().addActionListener(l -> reporteGeneral());
        this.vista.getjButtonReporteIndividual().addActionListener(l->reporteIndividual());

        this.vista.getjTableDatosProducto().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaModeloPersona = this.vista.getjTableDatosProducto().getSelectionModel();
        listaModeloPersona.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    productoSeleccionado();
                }

            }

        });
        this.vista.getjButtonEditar().setEnabled(false);
        this.vista.getjButtonEliminar().setEnabled(false);

    }

    public void guardarProducto() {
        if (validacionesProducto() == false) {
        } else {
            producto = new Producto();
            producto.setNombre(this.vista.getjTextFieldNombre().getText());
            double precio = Double.parseDouble(this.vista.getjTextFieldPrecio().getText());
            producto.setPrecio(precio);
            int cantidad = Integer.parseInt(this.vista.getjTextFieldCantidad().getText());
            producto.setCantidad(cantidad);
            modeloProducto.create(producto);
            modeloTabla.agregar(producto);
            Resouces.success("AVISO!!!", "PRODUCTO GUARDADO CORRECTAMENTE");
        }
    }

    public void productoSeleccionado() {
        if (this.vista.getjTableDatosProducto().getSelectedRow() != -1) {
            producto = modeloTabla.getFilas().get(this.vista.getjTableDatosProducto().getSelectedRow());
            this.vista.getjTextFieldNombre().setText(producto.getNombre());
            String cantidad = String.valueOf(producto.getCantidad());
            this.vista.getjTextFieldCantidad().setText(cantidad);
            String precio = String.valueOf(producto.getPrecio());
            this.vista.getjTextFieldPrecio().setText(precio);
            //control de botones
            this.vista.getjButtonEditar().setEnabled(true);
            this.vista.getjButtonEliminar().setEnabled(true);
            this.vista.getjButtonCrear().setEnabled(false);
        }
    }

    public void editarProducto() {
        if (producto != null) {
            producto.setNombre(this.vista.getjTextFieldNombre().getText());
            int cantidad = Integer.parseInt(this.vista.getjTextFieldCantidad().getText());
            producto.setCantidad(cantidad);
            double precio = Double.parseDouble(this.vista.getjTextFieldPrecio().getText());
            producto.setPrecio(precio);

            try {
                modeloProducto.edit(producto);
            } catch (Exception e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
            Resouces.success("AVISO!!!", "PRODUCTO EDITADO CORRECTAMENTE");

            limpiar();
        }
    }

    public void eliminarProducto() {
        if (producto != null) {
            try {
                modeloProducto.destroy(producto.getIdproducto());
            } catch (NonexistentEntityException e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
            modeloTabla.eliminar(producto);
            modeloTabla.actualizar(producto);
            Resouces.success("AVISO!!!", "PRODUCTO ELIMINADO CORRECTAMENTE");
        }
    }

    public void buscarproducto() {
        if (this.vista.getjRadioButtonMostrarTodo().isSelected()) {
            modeloTabla.setFilas(modeloProducto.findProductoEntities());
            modeloTabla.fireTableDataChanged();
        } else {
            if (!this.vista.getjTextFieldBuscarCriterio().getText().equals("")) {
                modeloTabla.setFilas(modeloProducto.buscarProducto(this.vista.getjTextFieldBuscarCriterio().getText()));
                modeloTabla.fireTableDataChanged();
            } else {
                limpiarbuscador();
            }
        }

    }

    public void limpiarbuscador() {
        this.vista.getjTextFieldBuscarCriterio().setText("");
        modeloTabla.setFilas(modeloProducto.findProductoEntities());
        modeloTabla.fireTableDataChanged();
    }

    public void limpiar() {
        this.vista.getjTextFieldNombre().setText("");
        this.vista.getjTextFieldPrecio().setText("");
        this.vista.getjTextFieldCantidad().setText("");

    }
    //llamar

    public void reporteGeneral() {
        Resouces.imprimirReeporte(ManagerFactory.getConnection(manage.getEntityManagerFactory().createEntityManager()), "/reportes/Producto.jasper",new HashMap());
    }
    
    public void reporteIndividual(){
        if(producto != null){
            Map parameters = new HashMap();
            parameters.put("id",producto.getIdproducto());
            
            Resouces.imprimirReeporte(ManagerFactory.getConnection(manage.getEntityManagerFactory().createEntityManager()), "/reportes/IndividualProducto.jasper", parameters);
        }else{
            Resouces.warning("ATENCIÓN!!!", "PRODUCTO NO SELECCIONADA");
        }
    }

    public boolean validacionesProducto() {
        Validaciones validar = new Validaciones();
        boolean validado = false;
        if (!this.vista.getjTextFieldNombre().getText().isEmpty()) {
            if (validar.ValidarTextoConEspacio(this.vista.getjTextFieldNombre().getText())) {

                if (!this.vista.getjTextFieldPrecio().getText().isEmpty()) {
                    if (validar.validarNumeros(this.vista.getjTextFieldPrecio().getText())) {

                        //segunda  valid
                        if (!this.vista.getjTextFieldCantidad().getText().isEmpty()) {
                            if (validar.validarNumeros(this.vista.getjTextFieldCantidad().getText())) {

                                validado = true;

                            } else {
                                Resouces.warning("AVISO!!!", "CANTIDAD INCORRECTO");
                            }
                        } else {
                            Resouces.warning("AVISO!!!", "CANTIDAD VACIO");
                        }
                    } else {
                        Resouces.warning("AVISO!!!", "PRECIO INCORRECTO");
                    }
                } else {
                    Resouces.warning("AVISO!!!", "PRECIO VACIO");
                }

            } else {
                Resouces.warning("AVISO!!!", "NOMBRE INCORRECTO");

            }
        } else {
            Resouces.warning("AVISO!!!", "NOMBRE VACIO");

        }
        return validado;
    }
}
