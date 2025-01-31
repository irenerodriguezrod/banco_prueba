/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.banco;

import java.util.Scanner;

public class AplicacionBanco {
/*Es el paquete al que pertenece la clase, el IDE lo hace automáticamente. */

/**
 *
 * @author irene.rodrod.2
 */
    public static void main(String[] args) {
        Cuenta cuenta = null;
        int opcion;
        String codigo, titular;
        float saldo, cantidad;
        Scanner teclado = new Scanner(System.in);

        do {
            System.out.println("1.- Abrir cuenta");
            System.out.println("2.- Ingresar dinero");
            System.out.println("3.- Retirar dinero");
            System.out.println("4.- Consultar saldo");
            System.out.println("5.- Cancelar cuenta");
            System.out.println("0.- Salir");
            System.out.print("Introduzca opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            if (opcion >= 1 && opcion <= 5) {
                System.out.print("Introduzca código de cuenta: ");
                codigo = teclado.nextLine();

                if (opcion == 1) {
                    if (cuenta == null) {
                        System.out.print("Introduzca titular de la cuenta: ");
                        titular = teclado.nextLine();
                        System.out.print("Introduzca saldo cuenta: ");
                        saldo = teclado.nextFloat();
                        cuenta = new Cuenta(codigo, titular, saldo);
                        System.out.println("Cuenta creada con éxito: " + cuenta);
                    } else {
                        System.out.println("No se puede abrir la cuenta, ya existe una cuenta activa.");
                    }
                } else {
                    if (cuenta != null && codigo.equals(cuenta.getCodigo())) {
                        switch (opcion) {
                            case 2 -> {
                                System.out.print("Introduzca cantidad a ingresar: ");
                                cantidad = teclado.nextFloat();
                                cuenta.ingresar(cantidad);
                                System.out.println("Cantidad ingresada");
                                System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                            }
                            case 3 -> {
                                System.out.print("Introduzca la cantidad a retirar: ");
                                cantidad = teclado.nextFloat();
                                if (cuenta.reintegrar(cantidad)) {
                                    System.out.println("Cantidad reintegrada");
                                } else {
                                    System.out.println("No se pudo reintegrar la cantidad. Saldo insuficiente.");
                                }
                                System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                            }
                            case 4 -> {
                                System.out.println("El saldo de su cuenta es: " + cuenta.getSaldo());
                            }
                            case 5 -> {
                                cuenta = null;
                                System.out.println("Cuenta cancelada.");
                            }
                        }
                    } else {
                        System.out.println("Código de cuenta incorrecto o cuenta no existe.");
                    }
                }
            } else if (opcion != 0) {
                System.out.println("ERROR EN LA ENTRADA");
            }

        } while (opcion != 0);

        System.out.println("BYE");
    }
}
