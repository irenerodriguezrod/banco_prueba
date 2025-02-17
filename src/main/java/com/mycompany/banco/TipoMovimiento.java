/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license | Aqui se puede mostrar un mensaje por defecto 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

/**
 *
 * @author irene.rodrod.2
 */
public enum TipoMovimiento {
    INGRESO('I'), REINTEGRO('R'), TRANSFERENCIA('T');
    private final char atributo;
    
    private TipoMovimiento(char atributo){
        this.atributo=atributo;
    }
    
    public char getAtrbuto(){
        return atributo;
    }
}
