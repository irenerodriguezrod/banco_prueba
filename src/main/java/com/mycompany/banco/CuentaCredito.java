package com.mycompany.banco;

import java.time.LocalDate;

/**
 *
 * @author irene.rodrod.2
 */
public abstract class CuentaCredito extends Cuenta {
    private float limiteCredito;
 
    /**
     * Constructor que inicializa una cuenta de crédito con su código, titular, saldo inicial y límite de crédito.
     *
     * @param codigo        Código único de la cuenta.
     * @param titular       Nombre del titular de la cuenta.
     * @param saldo         Saldo inicial de la cuenta.
     * @param limiteCredito Límite de crédito permitido para la cuenta.
     */
    public CuentaCredito(String codigo, String titular, float saldo, float limiteCredito) {
        super(codigo, titular, saldo);
        this.limiteCredito = limiteCredito;
    }
 
    /**
     * Obtiene el límite de crédito de la cuenta.
     *
     * @return Límite de crédito de la cuenta.
     */
    public float getLimiteCredito() {
        return limiteCredito;
    }
 
    /**
     * Establece el límite de crédito de la cuenta.
     *
     * @param limiteCredito Nuevo límite de crédito.
     */
    public void setLimiteCredito(float limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
 
    /**
     * Realiza un reintegro de la cuenta. Permite retirar dinero incluso si el saldo es insuficiente,
     * siempre que no se supere el límite de crédito.
     *
     * @param cantidad Cantidad a retirar.
     * @throws com.mycompany.banco.SaldoInsuficienteException - En caso de error, salta la siguiente excepción
     */
    @Override
    public void reintegrar(float cantidad) throws SaldoInsuficienteException {
        if(cantidad > saldo){
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
        if (cantidad <0){
            throw new IllegalArgumentException("La cantidad a reintegrar debe ser positiva");
        }
            saldo -= cantidad;
            movimientos.add(new Movimiento(LocalDate.now(), TipoMovimiento.REINTEGRO, -cantidad, saldo));
    }
 
    /**
     * Devuelve una representación en cadena de la cuenta de crédito.
     *
     * @return Cadena con la información de la cuenta de crédito.
     */
    @Override
    public String toString() {
        return super.toString() + ", Límite de crédito: " + limiteCredito;
    }
}