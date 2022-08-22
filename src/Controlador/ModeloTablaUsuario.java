/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.ArrayList;
import java.util.List;
import Modelo.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP
 */
public class ModeloTablaUsuario extends AbstractTableModel {

    private String[] columnas = {"Nombre", "Clave", "IdPersona"};
    public static List<Usuario> filas;
    private Usuario usuarioSeleccionado;
    private int indice;

    public ModeloTablaUsuario() {
        filas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return filas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        usuarioSeleccionado = filas.get(rowIndex);
        this.indice = rowIndex;
        switch (columnIndex) {
            case 0:
                return usuarioSeleccionado.getUsuario();
            case 1:
                return usuarioSeleccionado.getClave();
            case 2:
                return usuarioSeleccionado.getIdpersona();

            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Long.class;

            default:
                return Object.class;
        }
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    public List<Usuario> getFilas() {
        return filas;
    }

    public void setFilas(List<Usuario> filas) {
        this.filas = filas;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void actualizar(Usuario p) {
        setUsuarioSelecionado(null);
        if (p != null) {
            filas.add(indice, p);
            fireTableDataChanged();
        }
    }

    public void agregar(Usuario p) {
        if (p != null) {
            filas.add(p);
            fireTableDataChanged();
        }
    }

    public void eliminar(Usuario p) {
        if (p != null) {
            filas.remove(p);
            fireTableDataChanged();
        }

    }
}
