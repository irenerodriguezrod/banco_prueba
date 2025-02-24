package com.mycompany.banco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author irene.rodrod.2
 */
public class Cuenta implements Comparable<Cuenta>{

    private String codigo;
    private String titular;
    float saldo;
    List<Movimiento> movimientos;

    /*click derecho - insert code - constructor - hacer click y activar todas - finish */
    /*public class Cuenta implements Comparable{
        this.codigo = codigo;
        this.titular = titular;
        if(saldo>0) {
            this.saldo = saldo;
        }
    }*/
    public Cuenta(String codigo, String titular, float saldo) {
        if (saldo <= 0) {
            throw new IllegalArgumentException("El saldo inicial debe ser positivo");
        }        
        
        this.codigo = codigo;
        this.titular = titular;
        this.saldo = saldo;

        movimientos = new ArrayList<>();
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

    public List<Movimiento> getMovimientos() {
        List<Movimiento> movimientos = null;
        return movimientos;
    }

    /*METODOS SET*/
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setSaldo(float saldo) {
        if (saldo > 0) {
            this.saldo = saldo;
        }
    }
    
    /**
     * Agrega una cantidad de dinero en la cuenta que estemos operando
     * @param cantidad 
     * 
     * @throws IllegalArgumentException en caso de que la cantidad a ingresar sea negativa
     */
    public void ingresar(float cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser positiva");
        }
        saldo += cantidad;
    }
    
    /**
     * 
     * @param cantidad
     * @throws IllegalArgumentException Si la cantidad sea negativa
     * @throws SaldoInsuficienteException  No hay saldo suficiente en la cuenta
     */
    public void reintegrar(float cantidad) throws SaldoInsuficienteException {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad a reintegrar debe ser positiva");
        }
        if (cantidad > saldo) {
            throw new SaldoInsuficienteException("Saldo Insuficiente");
        }

        saldo -= cantidad;
        movimientos.add(new Movimiento(LocalDate.now(), 'R', -cantidad, saldo));
    }
    
    /*public void reintegrar(float cantidad){
        if(cantidad<0){
            throw new IllegalArgumentException("La cantidad a reintegrar debe ser positiva");
        }
    }*/
    
    /**
     * Realiza movimientos de dinero entre dos cuentas
     * 
     * @param destino Cuenta a la que se va a enviar el dinero
     * @param cantidad Que se quiere enviar a la cuenta de {@code destino}
     * @throws NullPointerException La cuenta destino no existe
     * @throws IllegalArgumentException El destino es el mismo que el origen, la cantidad sea negativa o la cantidad a transferir sea mayor al saldo
     */
    public void realizarTransferencia(Cuenta destino, float cantidad) {
        // en caso de que la cuenta no exista
        if (destino == null) {
            throw new NullPointerException(); //siempre que haya un throw, tiene que haber un try catch en el main
        }
        // en caso de que el destino sea el mismo
        if (destino == this) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }
        // en caso de que la cantidad sea negativa
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        // en caso de que la cantidad a transferir sea mayor al saldo
        if (cantidad > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        saldo -= cantidad;
        movimientos.add(new Movimiento(LocalDate.now(), 'T', -cantidad, saldo));
        destino.recibirTransferencia(this, cantidad);
    }

    /**
     * Sirve para que otra cuenta reciba una transferencia
     * 
     * @param origen Cuenta desde la que se ha realizado el movimiento
     * @param cantidad Cantidad que se recibe 
     */
    public void recibirTransferencia(Cuenta origen, float cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            movimientos.add(new Movimiento(LocalDate.now(), 'I', cantidad, saldo));
        }
    }

    public String listarMovimientos() {
        return movimientos.toString();
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

    public int compareTo(Cuenta o) {
        return this.codigo.compareTo(o.codigo);
    }

    
    public static boolean esIbanValido (String codigoIban){ //se hace estatitco y publico xa poder usarlo desde fuera
        String patron="(ES)(\\d{2})(\\d{4})(\\d{4})(\\d{2})(\\d{10})";//patron que debe seguir lo que se introduzca xa q este bien
        Pattern p=Pattern.compile(patron); //comprueba que sintacticamente este bien el patron
        Matcher m=p.matcher(codigoIban); //comprueba el codigo iban con el patron que le hemos dicho anteriormente. devuelve vdero si es valido y false si no lo es
        return m.matches();
    }
}