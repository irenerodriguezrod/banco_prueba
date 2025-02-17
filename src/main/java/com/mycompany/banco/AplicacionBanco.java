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
            System.out.println("1- Abrir cuenta");
            System.out.println("2- Operar con cuenta");
            System.out.println("3- Cancelar cuenta");
            System.out.println("4- Listar cuenta");
            System.out.println("5- Consultar total depositos");
            System.out.println("0- Salir");
            System.out.print("Introduzca tu opcion:");

            try {
                //exceptoin para que no salga mensage de error de netbeans cuando se introduzca una letra
                opcion = teclado.nextInt();
                //System.out.println("bien");
            } catch (InputMismatchException ime) {
                //los systems estan ahi para ver que funciona.
                //System.out.println("mal");
                opcion = 1000;
            }
            teclado.nextLine();
            switch (opcion) {
                case 1 -> {
                    System.out.print("Introducir el codigo de la cuenta: ");
                    codigo = teclado.nextLine();

                    System.out.print("Introducir el nombre del titular de la cuenta: ");
                    titular = teclado.nextLine();

                    do {
                        //error es boolean, por lo que se inicializa para llevar el control
                        error = false;
                        try {
                            System.out.print("Introduzca el saldo inicial de la cuenta:  ");
                            /*Las dos siguientes lineas son las que controlan el error del try*/
                            saldo = teclado.nextFloat();
                            cuenta = new Cuenta(codigo, titular, saldo);

                            if (banco.abrirCuenta(cuenta)) {
                                System.out.println("Cuenta creada con exito. ");
                            } else {
                                System.out.println("No se puede abrir la cuenta. " + cuenta);
                            }
                        } catch (IllegalArgumentException ime) {
                            System.out.println(ime.getMessage());
                            error = true;
                        } catch (InputMismatchException ime) {
                            System.out.println("Entrada incorrecta");
                            error = true;
                        } finally {
                            teclado.nextLine();
                        }
                    } while (error);
                }

                case 2 -> {
                    System.out.println("Introduzca codigo de la cuenta: ");
                    codigo = teclado.nextLine();
                    cuenta = banco.buscarCuenta(codigo);
                    if (cuenta != null) {
                        do {
                            System.out.println("1- Ingresar dinero");
                            System.out.println("2- Retirar dinero");
                            System.out.println("3- Consultar saldo");
                            System.out.println("4- Realizar transferencia");
                            System.out.println("5- Consultar movimientos");
                            System.out.println("0- Salir");
                            System.out.println("Introduzca su opcion");
                            try {
                                //excepcion para que no salga mensage de error cuando se introduzca una letra
                                opcion2 = teclado.nextInt();
                            } catch (InputMismatchException | IllegalArgumentException iae) {
                                System.out.println("Entrada incorrecta");
                                opcion2 = teclado.nextInt();
                            }
                            teclado.nextLine();

                            switch (opcion2) {
                                case 1 -> {
                                    do {
                                        error = false;
                                        System.out.print("Introduzca cantidad a ingresar: ");
                                        try {
                                            cantidad = teclado.nextFloat();
                                        } catch (InputMismatchException | IllegalArgumentException iae) {
                                            System.out.println("Entrada incorrecta");
                                            error=true;
                                        } 
                                        
                                    } while (error); 
                                    /*System.out.print("Introduzca cantidad a ingresar: ");
                                    cantidad = teclado.nextFloat();
                                    teclado.nextLine();
                                    cuenta.ingresar(cantidad);
                                    System.out.println("Canitdad ingresada");
                                    System.out.println("Nuevo saldo: " + cuenta.getSaldo());*/
                                }

                                case 2 -> {
                                    System.out.print("Introduzca cantidad a retirar: ");
                                    cantidad = teclado.nextFloat();
                                    teclado.nextLine();
                                    try {
                                        cuenta.reintegrar(cantidad);
                                        System.out.println("Cantidad retirada");
                                        System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                                    } catch (SaldoInsuficienteException ex) {
                                        System.out.println(ex.getMessage());
                                    }
                                }
                                case 3 -> {
                                    System.out.println("El saldo actual de la cuenta es : " + cuenta.getSaldo());
                                }

                                case 4 -> {
                                    System.out.print("Introduzca el codigo de cuenta de destino: ");

                                    try {
                                        codigo = teclado.nextLine();
                                        cuentaDestino = banco.buscarCuenta(codigo);
                                    } catch (InputMismatchException | IllegalArgumentException iae) {
                                        System.out.println("Entrada incorrecta");
                                    }
                                    /*System.out.print("Introduzca el codigo de cuenta de destino: ");
                                    codigo = teclado.nextLine();
                                    cuentaDestino = banco.buscarCuenta(codigo);
                                    if (cuentaDestino != null) {
                                        System.out.println("Transferencia a realizar");
                                        cantidad = teclado.nextFloat();
                                        cuenta.realizarTransferencia(cuentaDestino, cantidad);
                                        System.out.println("Cantidad transferida");
                                        System.out.println("Nuevo saldo: " + cuenta.getSaldo());
                                    } else {
                                        System.out.println("Esta cuetna no existe");
                                    }
                                     */
                                }

                                case 5 -> {
                                    // System.out.println("Estos son los movimientos de la cuenta : \n"+cuenta.listarMovimientos());
                                    for (Movimiento m : cuenta.getMovimientos()) {
                                        System.out.println(m);
                                    }
                                }

                                case 0 -> {
                                    System.out.println("BYE.");
                                }

                                default -> {
                                    System.out.println("Error en la opcion");
                                }
                            }

                        } while (opcion2 != 0);

                    } else {
                        System.out.println("No existe una cuenta con ese codigo");
                    }
                }
                case 3 -> {
                    System.out.println("Introduzca el codigo de la cuenta: ");
                    codigo = teclado.nextLine();
                    cuenta = banco.buscarCuenta(codigo);
                    if (cuenta != null) {
                        System.out.println(cuenta);
                        if (banco.cancelarCuenta(codigo)) {
                            System.out.println("Cuenta cancelada");
                        } else {
                            System.out.println("No se ha podido cancelar la cuenta");
                        }
                    } else {
                        System.out.println("No existe una cuenta con ese codigo");
                    }
                }
                case 4 -> {
                    for (Cuenta c : banco.getCuentas()) {
                        System.out.println(c);
                    }
                }
                case 5 -> {
                    System.out.printf("Total depositos: %f\n", banco.getTotalDepositos());
                }
                case 0 -> {
                    System.out.println("Bye.");
                }
                default -> {
                    System.out.println("Error en la opcion");
                }
            }
        } while (opcion != 0);
    }
}
