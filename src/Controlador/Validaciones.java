/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author HP
 */
public class Validaciones {
    public boolean ValidarTextoConEspacio(String texto) {
        String cadena = texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
        if (cadena.length() >= 3 && cadena.length() <= 20 && cadena.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
            return true;
        } else {
            return false;
        }
    }
    //VALIDAD CEDULA
    public boolean validarCedula(String cedula) {
        boolean val = false;
        //Divide la cadena en los 10 numeros
        //Integer.parseInt sirve para transformar una cadena a entero. 
        //subString es un metodo de string(Desde, hasta)
        if (cedula.matches("\\d{10}")) {
            int d1 = Integer.parseInt(cedula.substring(0, 1));
            int d2 = Integer.parseInt(cedula.substring(1, 2));
            int d3 = Integer.parseInt(cedula.substring(2, 3));
            int d4 = Integer.parseInt(cedula.substring(3, 4));
            int d5 = Integer.parseInt(cedula.substring(4, 5));
            int d6 = Integer.parseInt(cedula.substring(5, 6));
            int d7 = Integer.parseInt(cedula.substring(6, 7));
            int d8 = Integer.parseInt(cedula.substring(7, 8));
            int d9 = Integer.parseInt(cedula.substring(8, 9));
            int d10 = Integer.parseInt(cedula.substring(9));

            //Multiplica todas la posciones impares * 2 y las posiciones pares se multiplica 1
            d1 = d1 * 2;
            if (d1 > 9) {
                d1 = d1 - 9;
            }

            d3 = d3 * 2;
            if (d3 > 9) {
                d3 = d3 - 9;
            }

            d5 = d5 * 2;
            if (d5 > 9) {
                d5 = d5 - 9;
            }

            d7 = d7 * 2;
            if (d7 > 9) {
                d7 = d7 - 9;
            }

            d9 = d9 * 2;
            if (d9 > 9) {
                d9 = d9 - 9;
            }

            // SUMA TODOS LOS  NUMEROS PARES E IMPARES
            int sumpar = d2 + d4 + d6 + d8;
            int sumimp = d1 + d3 + d5 + d7 + d9;
            int total = sumpar + sumimp;

            //DIVIDO MI DECENA SUPERIRO PARA 10 Y SI EL RESULTADO ES DIFERENTE DE 0 SUMA 1
            double decenasuperior = total;
            while (decenasuperior % 10 != 0) {
                decenasuperior = decenasuperior + 1;
            }

            if ((decenasuperior - total) == d10) {
                val = true;
            }
        }
        return val;
    }
    
    //VALIDAR NUMEROS
    public boolean validarNumeros(String x) {
        try {
            double n = Double.parseDouble(x);
            if (n < 0) {
                return false;
            } else {
                if (n >= 1) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
    
    //VALIDAR CORREO
    public boolean validarEmail(String mail) {
        return mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
    
    // VALIDAR DIRECCION
    public boolean validarDirec(String code) {
        return code.matches("[a-zA-Z\\d\\s\\-\\,\\#\\.\\+]+") && code.length() <= 100;
    }
    
    
    //VALIDAR CELULAR
    public boolean validarCelu(String celu) {
        return celu.matches("[^a-zA-Z]|\\d{10}$");
    }
    
}
