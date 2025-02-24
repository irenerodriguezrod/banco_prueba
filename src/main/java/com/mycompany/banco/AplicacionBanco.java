package com.mycompany.banco;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AplicacionBanco {
/*Es el paquete al que pertenece la clase, el IDE lo hace automáticamente. */

/**
 *
 * @author irene.rodrod.2
 */
    public static void main(String[] args) {
        Cuenta cuenta = null;
        Cuenta cuentaDestino = null;
        Cuenta cuentaDestino = null;
        String codigo, titular;
        float saldo = 0, cantidad = 0;
        int opcion, opcion2;
        boolean error;

        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar la configuración del logger", ex);
        }

        Scanner teclado = new Scanner(System.in);
        banco = new Banco("Banco Sauces");
        Handler controlador = new FileHandler("./registro.log", true);
        logger.addHandler(controlador); // Aseguramos que el handler esté añadido al logger

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

            teclado.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> {
                    // Abrir cuenta
                    do {
                        error = false;
                        try {
                            System.out.print("Introducir el código de la cuenta: ");
                            codigo = teclado.nextLine();
                            while (!Cuenta.esIbanValido(codigo)) { // Controla que el IBAN esté bien introducido
                                System.out.println("Error en el IBAN.");
                                logger.log(Level.WARNING, "IBAN inválido ingresado: {0}", codigo);
                                System.out.print("Introducir el código de la cuenta: ");
                                codigo = teclado.nextLine();
                            }

                            System.out.print("Introducir el nombre del titular de la cuenta: ");
                            titular = teclado.nextLine();

                            // Controlar el saldo antes de continuar
                            do {
                                System.out.print("Introduzca el saldo inicial de la cuenta: ");
                                saldo = teclado.nextFloat();
                                if (saldo < 0) {
                                    System.out.println("Error, saldo incorrecto. El saldo no puede ser negativo");
                                    logger.log(Level.WARNING, "Saldo introducido incorrecto: {0}", saldo);
                                }
                            } while (saldo < 0);  // Repite mientras el saldo sea negativo

                            cuenta = new Cuenta(codigo, titular, saldo);
                            if (banco.abrirCuenta(cuenta)) {
                                System.out.println("Cuenta creada con éxito.");
                            } else {
                                System.out.println("No se puede abrir la cuenta. " + cuenta);
                            }
                        } catch (IllegalArgumentException | InputMismatchException ex) {
                            System.out.println("Entrada incorrecta: " + ex.getMessage());
                            error = true;
                        }
                    } while (error);
                }
                case 2 -> {
                    // Operar con cuenta
                    try {
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
                                teclado.nextLine(); // Limpiar el buffer después de la opción

                                switch (opcion2) {
                                    case 1 -> {
                                        // Ingresar dinero
                                        do {
                                            error = false;
                                            try {
                                                System.out.print("Introduzca cantidad a ingresar: ");
                                                cantidad = teclado.nextFloat();
                                                if (cantidad <= 0) {
                                                    System.out.println("La cantidad debe ser positiva.");
                                                    error = true;
                                                    logger.log(Level.WARNING, "Cantidad introducida negativa: {0}", cantidad);
                                                }
                                            } catch (InputMismatchException ime) {
                                                System.out.println("Entrada incorrecta, por favor ingrese un número válido.");
                                                teclado.nextLine(); // Limpiar el buffer
                                                error = true;
                                            }
                                            if (!error) {
                                                cuenta.ingresar(cantidad);
                                                System.out.println("Cantidad ingresada correctamente.");
                                                System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                                            }
                                        } while (error);
                                    }
                                    case 2 -> {
                                        // Retirar dinero
                                        try {
                                            System.out.print("Introduzca cantidad a retirar: ");
                                            cantidad = teclado.nextFloat();
                                            teclado.nextLine(); // Limpiar el buffer
                                            cuenta.reintegrar(cantidad);
                                            System.out.println("Cantidad retirada.");
                                            System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                                        } catch (InputMismatchException ime) {
                                            System.out.println("Entrada incorrecta, por favor ingrese un número válido.");
                                            teclado.nextLine(); // Limpiar el buffer
                                        } catch (SaldoInsuficienteException ex) {
                                            System.out.println(ex.getMessage());
                                            logger.log(Level.WARNING, "Intento de retirar más de lo disponible: {0}", cantidad);
                                        } catch (Exception ex) {
                                            System.out.println("Se ha producido un error al intentar retirar dinero.");
                                            logger.log(Level.SEVERE, "Error al retirar dinero: ", ex);
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
                                            logger.log(Level.WARNING, "Cuenta de destino inexistente: {0}", cuentaDestino);
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
                            logger.log(Level.WARNING, "El código introducido no corresponde con ninguna cuenta");
                        }
                    } catch (Exception ex) {
                        System.out.println("Se ha producido un error inesperado al operar con la cuenta.");
                        logger.log(Level.SEVERE, "Error inesperado al operar con la cuenta: ", ex);
                    }
                }

                case 3 -> {
                    // Cancelar cuenta
                    try {
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
                            logger.log(Level.WARNING, "El código introducido no corresponde con ninguna cuenta");
                        }
                    } catch (NullPointerException ex) {
                        System.out.println("Se ha producido un error inesperado: la cuenta no pudo ser encontrada.");
                        logger.log(Level.SEVERE, "NullPointerException: ", ex);
                    } catch (Exception ex) {
                        System.out.println("Se ha producido un error al intentar cancelar la cuenta: " + ex.getMessage());
                        logger.log(Level.SEVERE, "Error al cancelar la cuenta: ", ex);
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

        teclado.close(); // Cerrar scanner
    }
}
