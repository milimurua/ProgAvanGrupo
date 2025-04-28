package tp2.pa;

import java.util.Scanner;

public class AplicacionBancaria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce tu nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce el ID de la cuenta: ");
        String idCuenta = scanner.nextLine();
        System.out.print("Introduce el saldo inicial: ");
        double saldoInicial = Double.parseDouble(scanner.nextLine());

        Usuario usuario = new Usuario(nombre, idCuenta, saldoInicial);
        Transaccion transaccion = new Transaccion();
        DetalleUsuario detalle = new DetalleUsuario();
        Notificacion notificacion = new NotificacionEmail(usuario);

        // Ejemplo de operaciones
        transaccion.depositar(usuario, 500);
        detalle.imprimirTransaccion("Depósito", 500, usuario);

        boolean exito = transaccion.retirar(usuario, 200);
        if (exito) {
            detalle.imprimirTransaccion("Retirada", 200, usuario);
        } else {
            detalle.imprimirTransaccion("Retirada fallida", 200, usuario);
        }

        detalle.imprimirDetallesCuenta(usuario);
        notificacion.notificar("¡Operación completada con éxito!");

        scanner.close();
    }
}



