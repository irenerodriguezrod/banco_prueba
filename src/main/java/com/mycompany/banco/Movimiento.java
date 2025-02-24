/*
 * Clase que representa un movimiento bancario.
 * Contiene información sobre la fecha, tipo de movimiento,
 * cantidad y saldo después del movimiento.
 */
package com.mycompany.banco;

/**
 * La clase {@code Movimiento} modela los posibles movimientos que se pueden hacer con la clase {@code Cuenta}
 * @author irene.rodrod.2
 * @since 1.0
 */
import java.time.LocalDate;

/**
 * La clase {@code Movimiento} modela los posibles movimientos que se pueden hacer con la clase {@code Cuenta}
 * @author irene.rodrod.2
 * @since 1.0
 */
public class Movimiento {
    private LocalDate fecha;
    private char tipo;
    private float cantidad;
    private float saldo;

    /**
     * La clase {@code Movimiento} modela los posibles movimientos que se pueden hacer con la clase {@code Cuenta}
     * 
     * @param fecha     la fecha de los movimientos 
     * @param tipo      es para saber qué tipo de movimiento es: puede ser añadir dinero o enviar dinero
     * @param cantidad  la cantidad de dinero que se ha movido entre cuentas
     * @param saldo     el total de saldo de una cuenta 
     */
    public Movimiento(LocalDate fecha, char tipo, float cantidad, float saldo) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.saldo = saldo;
    }

    Movimiento(LocalDate now, TipoMovimiento tipoMovimiento, float f, float saldo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
}