package bancoplus;
import bancoplus.excepciones.*;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final static Scanner scanner = new Scanner(System.in);
    private final static Banco banco = new Banco();

    public static void main(String[] args) {
        if (!"UTF-8".equals(System.out.charset().displayName())) {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        }
        while (true) {
            System.out.println("\nSeleccione una Opción:");
            System.out.println("1. Depositar");
            System.out.println("2. Retirar");
            System.out.println("3. Transferencia");
            System.out.println("4. Consultar Depositos");
            System.out.println("5. Consultar Transferencias");
            System.out.println("6. Consultar Depositos por Codigo");
            System.out.println("7. Consultar Transferencias por Codigo");
            System.out.println("8. Actualizar Monto Transferencia");
            System.out.println("9. Buscar Operacion por Codigo");
            System.out.println("10. Salir");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> solicitarDeposito();
                case "2" -> solicitarRetiro();
                case "3" -> solicitarTransferencia();
                case "4" -> actualizarTransferencia();
                case "5" -> listarDepositos();
                case "6" -> listarTransferencias();
                case "7" -> consultarDepositoCodigo();
                case "8" -> consultarTransferenciaCodigo();
                case "9" -> consultarOperacionCodigo();
                case "10" -> solicitarSalir();
                default -> System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }

    private static Cuenta solicitarCuenta(String nombreCuenta){
        String numeroCuenta;
        Cuenta cuenta;
        while (true) {
            System.out.printf("\nIngrese el número de la Cuenta%s: ", nombreCuenta);
            numeroCuenta = scanner.next();
            if (numeroCuenta.length() != 4) {
                System.out.println("La entrada no es de 4 cifras, Intente de nuevo");
                scanner.nextLine();
                return null;
            } else if (validarDigitosNumeroCuenta(numeroCuenta)) {
                System.out.println("La entrada no es un valor entero, Intente de nuevo");
                scanner.nextLine();
                return null;
            } else {
                cuenta = banco.recuperarCuenta(numeroCuenta);
                if (cuenta == null) {
                    System.out.println("Cuenta No Encontrada");
                    scanner.nextLine();
                    return null;
                }
                scanner.nextLine();
                return cuenta;
            }
        }
    }

    private static void solicitarDeposito(){
        Cuenta cuentaDestino = solicitarCuenta("");
        if (cuentaDestino != null){
            if (banco.limiteDeposito(cuentaDestino)){
                System.out.printf("%nLimite de Depositos Diarios Superado%n");
            } else {
                System.out.println("\nIngrese el tipo de divisa:");
                System.out.println("1. Pesos (MXN)");
                System.out.println("2. Dolares (USD)");
                System.out.println("3. Euros (EU)");
                ArrayList<Object> tipoDivisa = null;
                String opcion = scanner.nextLine();
                switch (opcion) {
                    case "1" -> tipoDivisa = Banco.MXN;
                    case "2" -> tipoDivisa = Banco.USD;
                    case "3" -> tipoDivisa = Banco.EU;
                    default -> System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                }
                float cantidadDeposito;
                while (true){
                    try {
                        System.out.print("Ingrese la cantidad a depositar: ");
                        cantidadDeposito = scanner.nextFloat();
                        if (cantidadDeposito < 0){
                            System.out.println("La cantida no puede ser un valor negativo, Intente de nuevo");
                            scanner.nextLine();
                        } else {
                            System.out.println("%n**** Deposito Realizado ****");
                            System.out.println(banco.depositar(cuentaDestino, cantidadDeposito, tipoDivisa));
                            scanner.nextLine();
                            break;
                        }
                    } catch (ExcepcionSaldoMaximoSuperado ex){
                        System.out.println(ex.getMessage());
                        scanner.nextLine();
                    } catch (InputMismatchException ex){
                        System.out.println("La Cantidad a Depositar Debe ser un Valor Numerico");
                        scanner.nextLine();
                    }
                }
            }
        }
    }

    private static void solicitarRetiro(){
        Cuenta cuenta = solicitarCuenta("");
        if (cuenta != null){
            while (true){
                try {
                    System.out.print("Ingrese la cantidad a retirar: ");
                    float cantidadRetiro = scanner.nextFloat();
                    if (cantidadRetiro < 0){
                        System.out.println("La cantidad no puede ser un valor negativo, Intente de nuevo");
                        scanner.nextLine();
                    } else {
                        banco.retirar(cuenta, cantidadRetiro);
                        System.out.printf("%n**** Retiro Realizado **** %nSaldo actual: %f%n", cuenta.getSaldo());
                        scanner.nextLine();
                        break;
                    }
                } catch (ExcepcionFondosInsuficientes ex){
                    System.out.println(ex.getMessage());
                    scanner.nextLine();
                }
            }
        }
    }

    private static void solicitarTransferencia() {
        Cuenta cuentaOrigen = solicitarCuenta(" Origen");
        Cuenta cuentaDestino = solicitarCuenta(" Destino");
        if (cuentaOrigen != null && cuentaDestino != null){
            while (true){
                try {
                    System.out.print("Ingrese la cantidad a transferir: ");
                    float cantidadTransferencia = scanner.nextFloat();
                    if (cantidadTransferencia < 0){
                        System.out.println("La cantida no puede ser un valor negativo, Intente de nuevo");
                        scanner.nextLine();
                        continue;
                    }
                    if (cantidadTransferencia > 2000){
                        System.out.println("La cantidad no puede exceder los 2000 pesos, Intente de nuevo");
                        scanner.nextLine();
                        continue;
                    }
                    banco.transferir(cuentaOrigen, cuentaDestino, cantidadTransferencia);
                    scanner.nextLine();
                    break;
                } catch (ExcepcionSaldoMaximoSuperado | ExcepcionCuentasIguales | ExcepcionFondosInsuficientes |
                         ExcepcionCuentaNoEcontrada ex){
                    System.out.println(ex.getMessage());
                    scanner.nextLine();
                    break;
                }
            }
        }
    }

    public static void actualizarTransferencia(){
        int codigo = consultarTransferenciaCodigo();
        if (codigo != 0){
            while (true){
                System.out.print("Ingrese el nuevo monto: ");
                float nuevoMonto = scanner.nextFloat();
                if (nuevoMonto < 0){
                    System.out.println("El nuevo monto no puede ser un valor negativo, Intente de nuevo");
                    continue;
                }
                Transferencia transferencia = banco.consultarTransferenciaCodigo(codigo);
                Cuenta cuentaOrigen = transferencia.getCuentaOrigen();
                Cuenta cuentaDestino = transferencia.getCuentaDestino();
                float montoOriginal = transferencia.getMonto();
                cuentaOrigen.sumarFondos(montoOriginal); cuentaDestino.restarFondos(montoOriginal);
                cuentaOrigen.restarFondos(nuevoMonto); cuentaDestino.sumarFondos(nuevoMonto);
                transferencia.setMonto(nuevoMonto);
                transferencia.setFecha(new GregorianCalendar());
                break;
            }
        }
    }

    private static boolean validarDigitosNumeroCuenta(String cuentaDestino){
        for (int i = 0; i < cuentaDestino.length(); i++)
            if (!Character.isDigit(cuentaDestino.charAt(i))){
                return true;
            }
        return false;
    }

    public static void listarDepositos(){
        if (banco.consultarDepositos() == null){
            System.out.println("No se ha registrado ningun deposito");
        } else {
            System.out.println("%n**** Lista de Depositos ****");
            for (Deposito deposito:banco.consultarDepositos()){
                System.out.println(deposito);
            }
        }
    }

    public static void listarTransferencias(){
        if (banco.consultarTransferencias() == null){
            System.out.println("No se ha registrado ninguna transferencia");
        } else {
            System.out.println("%n**** Lista de Transferencias ****");
            for (Transferencia transferencia: banco.consultarTransferencias()){
                System.out.println(transferencia);
            }
        }
    }

    public static void consultarDepositoCodigo(){
        int codigo = 0;
        while (true) {
            try {
                System.out.println("\nIngrese el codigo de deposito:");
                codigo = scanner.nextInt();
            } catch (InputMismatchException ex){
                System.out.println(ex.getMessage());
                scanner.nextLine();
                continue;
            }
            break;
        }
        if (banco.consultarDepositoCodigo(codigo) == null){
            System.out.println("Codigo de deposito no encontrado");
        } else {
            System.out.println(banco.consultarDepositoCodigo(codigo));
        }
    }

    public static int consultarTransferenciaCodigo(){
        int codigo = 0;
        while (true) {
            try {
                System.out.println("\nIngrese el codigo de transferencia:");
                codigo = scanner.nextInt();
            } catch (InputMismatchException ex){
                System.out.println(ex.getMessage());
                scanner.nextLine();
                continue;
            }
            break;
        }
        if (banco.consultarTransferenciaCodigo(codigo) == null){
            System.out.println("Codigo de transferencia no encontrado");
            return 0;
        } else {
            System.out.println(banco.consultarTransferenciaCodigo(codigo));
        }
        return codigo;
    }

    public static void consultarOperacionCodigo(){
        int codigo = 0;
        while (true) {
            try {
                System.out.println("\nIngrese el codigo de operacion:");
                codigo = scanner.nextInt();
            } catch (InputMismatchException ex){
                System.out.println(ex.getMessage());
                scanner.nextLine();
                continue;
            }
            break;
        }
        if (banco.consultarOperacion(codigo) == null){
            System.out.println("Codigo de operacion no encontrado");
        } else {
            System.out.println(banco.consultarOperacion(codigo));
        }
    }

    private static void solicitarSalir(){
        System.out.println("\nGracias por utilizar nuestros servicios.");
        System.exit(0);
    }
}
