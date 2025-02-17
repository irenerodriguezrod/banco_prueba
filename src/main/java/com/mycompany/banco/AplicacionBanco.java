package com.mycompany.banco;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AplicacionBanco {

    /**
     * Método principal de la aplicación bancaria. Presenta un menú de opciones
     * para administrar cuentas bancarias.
     *
     * @param args
     * @see Banco
     * @see Cuenta
     * @see Movimiento
     */
    public static void main(String[] args) {
        Banco banco;
        Cuenta cuenta = null;
        Cuenta cuentaDestino = null;
        String codigo, titular;
        float saldo = 0, cantidad;
        int opcion, opcion2;
        boolean error;

        Scanner teclado = new Scanner(System.in);
        banco = new Banco("Banco Sauces");
        
        do {
            // Menú principal
            System.out.println("1- Abrir cuenta");
            System.out.println("2- Operar con cuenta");
            System.out.println("3- Cancelar cuenta");
            System.out.println("4- Listar cuenta");
            System.out.println("5- Consultar total depositos");
            System.out.println("0- Salir");
            System.out.print("Introduzca tu opcion: ");

            try {
                opcion = teclado.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Entrada incorrecta");
                opcion = 1000; // Valor no válido para salir del ciclo
            }

            teclado.nextLine();

            switch (opcion) {
                case 1 -> {
                    // Abrir cuenta
                    System.out.print("Introducir el código de la cuenta: ");
                    codigo = teclado.nextLine();
                    System.out.print("Introducir el nombre del titular de la cuenta: ");
                    titular = teclado.nextLine();

                    do {
                        error = false;
                        try {
                            System.out.print("Introduzca el saldo inicial de la cuenta: ");
                            saldo = teclado.nextFloat();
                            cuenta = new Cuenta(codigo, titular, saldo);
                            if (banco.abrirCuenta(cuenta)) {
                                System.out.println("Cuenta creada con éxito.");
                            } else {
                                System.out.println("No se puede abrir la cuenta. " + cuenta);
                            }
                        } catch (IllegalArgumentException | InputMismatchException ime) {
                            System.out.println("Entrada incorrecta: " + ime.getMessage());
                            error = true;
                        } 
                    } while (error);
                }
                case 2 -> {
                    // Operar con cuenta
                    System.out.println("Introduzca código de la cuenta: ");
                    codigo = teclado.nextLine();
                    cuenta = banco.buscarCuenta(codigo);
                    if (cuenta != null) {
                        do {
                            // Menú de operaciones con cuenta
                            System.out.println("1- Ingresar dinero");
                            System.out.println("2- Retirar dinero");
                            System.out.println("3- Consultar saldo");
                            System.out.println("4- Realizar transferencia");
                            System.out.println("5- Consultar movimientos");
                            System.out.println("0- Salir");
                            System.out.print("Introduzca su opción: ");
                            try {
                                opcion2 = teclado.nextInt();
                            } catch (InputMismatchException ime) {
                                System.out.println("Entrada incorrecta");
                                teclado.nextLine(); // Limpiar el buffer
                                opcion2 = 1000; // Valor no válido
                            }
                            teclado.nextLine(); // Limpiar el buffer
                            switch (opcion2) {
                                case 1 -> {
                                    // Ingresar dinero
                                    do {
                                        error = false;
                                        System.out.print("Introduzca cantidad a ingresar: ");
                                        try {
                                            cantidad = teclado.nextFloat();
                                            if (cantidad <= 0) {
                                                System.out.println("La cantidad debe ser positiva.");
                                                error = true;
                                            } else {
                                                cuenta.ingresar(cantidad);
                                                System.out.println("Cantidad ingresada correctamente.");
                                                System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                                            }
                                        } catch (InputMismatchException ime) {
                                            System.out.println("Entrada incorrecta, por favor ingrese un número válido.");
                                            error = true;
                                            teclado.nextLine(); // Limpiar el buffer
                                        }
                                    } while (error);
                                }
                                case 2 -> {
                                    // Retirar dinero
                                    System.out.print("Introduzca cantidad a retirar: ");
                                    try {
                                        cantidad = teclado.nextFloat();
                                        teclado.nextLine();
                                        cuenta.reintegrar(cantidad);
                                        System.out.println("Cantidad retirada.");
                                        System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                                    } catch (InputMismatchException | SaldoInsuficienteException ex) {
                                        System.out.println(ex.getMessage());
                                    }
                                }
                                case 3 -> {
                                    // Consultar saldo
                                    System.out.println("El saldo actual de la cuenta es: " + cuenta.getSaldo());
                                }
                                case 4 -> {
                                    // Realizar transferencia
                                    System.out.print("Introduzca el código de cuenta de destino: ");
                                    codigo = teclado.nextLine();
                                    cuentaDestino = banco.buscarCuenta(codigo);
                                    if (cuentaDestino == null) {
                                        System.out.println("La cuenta de destino no existe.");
                                    } else {
                                        System.out.print("Introduzca la cantidad a transferir: ");
                                        try {
                                            cantidad = teclado.nextFloat();
                                            teclado.nextLine();
                                            cuenta.realizarTransferencia(cuentaDestino, cantidad);
                                            System.out.println("Transferencia realizada.");
                                        } catch (IllegalArgumentException iae) {
                                            System.out.println(iae.getMessage());
                                        }
                                    }
                                }
                                case 5 -> {
                                    // Consultar movimientos
                                    for (Movimiento m : cuenta.getMovimientos()) {
                                        System.out.println(m);
                                    }
                                }
                                case 0 -> {
                                    System.out.println("BYE.");
                                }
                                default -> {
                                    System.out.println("Opción no válida.");
                                }
                            }
                        } while (opcion2 != 0);
                    } else {
                        System.out.println("No existe una cuenta con ese código.");
                    }
                }
                case 3 -> {
                    // Cancelar cuenta
                    System.out.println("Introduzca el código de la cuenta: ");
                    codigo = teclado.nextLine();
                    cuenta = banco.buscarCuenta(codigo);
                    if (cuenta != null) {
                        if (banco.cancelarCuenta(codigo)) {
                            System.out.println("Cuenta cancelada.");
                        } else {
                            System.out.println("No se ha podido cancelar la cuenta.");
                        }
                    } else {
                        System.out.println("No existe una cuenta con ese código.");
                    }
                }
                case 4 -> {
                    // Listar cuentas
                    for (Cuenta c : banco.getCuentas()) {
                        System.out.println(c);
                    }
                }
                case 5 -> {
                    // Consultar total depósitos
                    System.out.printf("Total depósitos: %f\n", banco.getTotalDepositos());
                }
                case 0 -> {
                    System.out.println("Bye.");
                }
                default -> {
                    System.out.println("Opción no válida.");
                }
            }
        } while (opcion != 0);
        teclado.close(); // Cerrar el Scanner al final
    }
}
