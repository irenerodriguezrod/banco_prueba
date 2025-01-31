package com.mycompany.banco;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code Banco} modela las diferentes acciones que pueden hacerse con
 * una cuenta.
 *
 * @author irene.rodrod.2
 * @since 2.0
 * @version 3.0
 */
public class Banco {
    private String nombre;
    private List<Cuenta> cuentas;
 
    public Banco(String nombre) {
        this.nombre = nombre;
        cuentas=new ArrayList<>();
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public boolean abrirCuenta(Cuenta cuenta){
        boolean cuentaAbierta=false;
        if(cuenta!=null && !cuentas.contains(cuenta)){
        cuentas.add(cuenta);
        cuentaAbierta=true;
        }else{
        cuentaAbierta=false;
        }
        return cuentaAbierta;
    }
   //if(buscarCuenta(Cuenta.getCodigo())==null{ return cuentas.add(cuenta)}
    public Cuenta buscarCuenta(String codigo){
        for(Cuenta elemento:cuentas){
        if (elemento.getCodigo().equals(codigo)){
            return elemento;
        }
        }
        return null;
    }
    //if
    public boolean cancelarCuenta(String codigo){
        boolean cuentaEliminada = false;
        Cuenta cuentaAEliminiar=buscarCuenta(codigo);
        if(cuentas.contains(cuentaAEliminiar)){
            cuentas.remove(cuentaAEliminiar);
            cuentaEliminada=true;
        }
        return cuentaEliminada;
    }
    //no hace falta controlar : return cuentas.remove(new Cuenta(codigo))
    public float getTotalDepositos(){
        float acumulador=0;
        for(Cuenta c:cuentas){
            acumulador+=c.getSaldo();
        }
        return acumulador;
    }
}