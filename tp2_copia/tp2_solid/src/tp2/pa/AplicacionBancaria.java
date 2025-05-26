package tp2.pa;

import java.util.Scanner;

public class AplicacionBancaria {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int opc=0;
            do{
                System.out.println("1. Desea iniciar sesion ");
                System.out.println("2. Desea crear cuenta ");
                opc=scanner.nextInt();
            }while(opc!=1 || opc!=2);
            
            String pin ="", idCuenta= "", nombre ="";
            double saldoInicial = 0;
            switch(opc){
                case 1:
                    // Registro inicial del usuario
                    System.out.print("Introduce tu nombre: ");
                    nombre = scanner.nextLine();
                    
                    System.out.print("Introduce el ID de la cuenta: ");
                    idCuenta = scanner.nextLine();
                    
                    System.out.print("Introduce tu PIN (contraseña): ");
                    pin = scanner.nextLine();
                    
                case 2:
                    System.out.print("Introduce tu nombre: ");
                    nombre = scanner.nextLine();
                    
                    System.out.print("Introduce el ID de la cuenta: ");
                    idCuenta = scanner.nextLine();
                    
                    System.out.print("Introduce el saldo inicial: ");
                    saldoInicial = Double.parseDouble(scanner.nextLine());
                    
                    System.out.print("Introduce tu PIN (contraseña): ");
                    pin = scanner.nextLine();
                    
            }    Usuario usuario = new Usuario(nombre, idCuenta, saldoInicial, pin);
            Transaccion transaccion = new Transaccion();
            DetalleUsuario detalle = new DetalleUsuario();
            Notificacion notificacion = new NotificacionEmail(usuario);
            int opcion;
            do {
                System.out.println("\n=== Menú de operaciones ===");
                System.out.println("1. Consultar saldo");
                System.out.println("2. Depositar dinero");
                System.out.println("3. Retirar dinero");
                System.out.println("4. Transferencia");
                System.out.println("5. Cambiar titular");
                System.out.println("6. Bloquear cuenta");
                System.out.println("7. Cambiar PIN");
                System.out.println("8. Solicitar préstamo");
                System.out.println("9. Ver detalles de cuenta");
                System.out.println("0. Salir");
                System.out.print("Elige una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        System.out.println("Saldo actual: $" + usuario.getSaldo());
                        break;
                    case 2:
                        System.out.print("Monto a depositar: ");
                        double deposito = Double.parseDouble(scanner.nextLine());
                        transaccion.depositar(usuario, deposito);
                        detalle.imprimirTransaccion("Depósito", deposito, usuario);
                        break;
                    case 3:
                        System.out.print("Monto a retirar: ");
                        double retiro = Double.parseDouble(scanner.nextLine());
                        if (transaccion.retirar(usuario, retiro)) {
                            detalle.imprimirTransaccion("Retiro", retiro, usuario);
                        } else {
                            System.out.println("Fondos insuficientes.");
                        }
                        break;
                    case 4:
                        System.out.print("Nombre del receptor: ");
                        String nombreReceptor = scanner.nextLine();
                        System.out.print("ID del receptor: ");
                        String idReceptor = scanner.nextLine();
                        Usuario receptor = new Usuario(nombreReceptor, idReceptor, 0, "0000");
                        System.out.print("Monto a transferir: ");
                        double montoTransferencia = Double.parseDouble(scanner.nextLine());
                        if (transaccion.transferir(usuario, receptor, montoTransferencia)) {
                            System.out.println("Transferencia exitosa a " + nombreReceptor);
                            detalle.imprimirTransaccion("Transferencia", montoTransferencia, usuario);
                        } else {
                            System.out.println("Fondos insuficientes para transferir.");
                        }
                        break;
                    case 5:
                        System.out.print("Nuevo titular: ");
                        String nuevoTitular = scanner.nextLine();
                        usuario.cambiarTitular(nuevoTitular);
                        System.out.println("Titular actualizado con éxito.");
                        break;
                    case 6:
                        usuario.bloquearCuenta();
                        System.out.println("Cuenta bloqueada.");
                        break;
                    case 7:
                        System.out.print("Nuevo PIN: ");
                        String nuevoPin = scanner.nextLine();
                        usuario.cambiarPin(nuevoPin);
                        System.out.println("PIN actualizado.");
                        break;
                    case 8:
                        System.out.print("Monto del préstamo solicitado: ");
                        double montoPrestamo = Double.parseDouble(scanner.nextLine());
                        transaccion.solicitarPrestamo(usuario, montoPrestamo);
                        System.out.println("Préstamo aprobado.");
                        break;
                    case 9:
                        detalle.imprimirDetallesCuenta(usuario);
                        break;
                    case 0:
                        System.out.println("Gracias por usar la aplicación bancaria.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
                
            } while (opcion != 0);
        }
    }
}
