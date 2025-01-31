package com.mycompany.banco;

import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license | Aqui se puede mostrar un mensaje por defecto 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author irene.rodrod.2
 */
public class CuentaCredito extends Cuenta {

    private float LimiteCredito;

    /**
     *
     * @param codigo
     * @param titular
     * @param saldo
     * @param cantidad
     */
    public CuentaCredito(String codigo, String titular, float saldo, float cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            movimientos.add(new Movimiento(LocalDate.now(), TipoMovimiento.REINTEGRO));
        }

    }

    public float getLimitCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(float limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    /**
     *
     * @param cantidad
     */
    
    @Override
    public void reintegrar(float cantidad) {
        float nuevoSaldo = getSaldo() - cantidad;
        if (nuevoSaldo >= -limiteCredito) {
            setSaldo(nuevoSaldo);
        }
    }

}
