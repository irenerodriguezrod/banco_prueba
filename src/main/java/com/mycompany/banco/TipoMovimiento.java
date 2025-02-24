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