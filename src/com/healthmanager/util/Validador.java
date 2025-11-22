package com.healthmanager.util;

public class Validador {
    
    public static boolean validarDni(String dni) {
        return dni != null && dni.matches("^[0-9]{8}$");
    }

    public static boolean validarCmp(String cmp) {
        return cmp != null && cmp.matches("^[0-9]{5,6}$");
    }

    public static boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("^[0-9]{7,10}$");
    }

    public static boolean validarEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean validarHora(String hora) {
        return hora != null && hora.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    public static boolean esNumero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean noEstaVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}