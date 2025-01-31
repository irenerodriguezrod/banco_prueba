/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

import java.util.Objects;

/**
 *
 * @author irene.rodrod.2
 */
public class Cuenta {
    private String codigo;
    private String titular;
    private float saldo;

    /*click derecho - insert code - constructor - hacer click y activar todas - finish */
    public Cuenta(String codigo, String titular, float saldo) {
        this.codigo = codigo;
        this.titular = titular;
        if(saldo>0) {
            this.saldo = saldo;
        }
    }
    
    /*MÉTODOS GET*/
    public String getCodigo() {
        return codigo;
    }

    public String getTitular() {
        return titular;
    }

    public float getSaldo() {
        return saldo;
    }

    /*METODOS SET*/
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setSaldo(float saldo) {
        if(saldo>0){
            this.saldo = saldo;
        }
    }
    
    public void ingresar(float cantidad) {
        if(cantidad>0){
            saldo+=cantidad; /*los atributos tienen otro color, y si se toca CTRL y se pone el ratón encima, lleva al enlace en el que se declaró la clase*/
        }
    }
    
    public void reintegrar(float cantidad) {
        if(cantidad>0 && cantidad<=saldo) {
            saldo-=cantidad;
        }
    }

    /*OVERRIDE: significa que ya existía el codigo*/
    @Override
    public String toString() {
        return codigo + "," + titular + "," + saldo;
    }

    /*EQUALS TIENE QUE SER IGUAL AL HASHCODE PARA HACER BIEN LA COMPARACION*/
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cuenta other = (Cuenta) obj;
        return Objects.equals(this.codigo, other.codigo);
    }
    
}