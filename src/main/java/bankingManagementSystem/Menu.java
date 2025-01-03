package bankingManagementSystem;
import exceptions.*;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Menu {
    private final static Scanner scanner = new Scanner(System.in);
    private final static Banco banco = new Banco();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nSeleccione una Opción:");
            System.out.println("1. Depositar                    6. Listar Transferencias");
            System.out.println("2. Retirar                      7. Consultar Depositos por Codigo");
            System.out.println("3. Transferencia                8. Consultar Transferencias por Codigo");
            System.out.println("4. Actualizar Transferencia     9. Buscar Operacion por Codigo");
            System.out.println("5. Listar Depositos             10. Salir\n");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1": {
                    solicitarDeposito();
                    break;
                }
                case "2": {
                    solicitarRetiro();
                    break;
                }
                case "3": {
                    solicitarTransferencia();
                    break;
                }
                case "4": {
                    actualizarTransferencia();
                    break;
                }
                case "5": {
                    listarDepositos();
                    break;
                }
                case "6": {
                    listarTransferencias();
                    break;
                }
                case "7": {
                    consultarDepositoCodigo();
                    break;
                }
                case "8": {
                    consultarTransferenciaCodigo();
                    break;
                }
                case "9": {
                    consultarOperacionCodigo();
                    break;
                }
                case "10": {
                    solicitarSalir();
                    break;
                }
                default: {
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");}
            }
        }
    }

    private static Cuenta solicitarCuenta(String nombreCuenta){
        while (true) {
            System.out.printf("Ingrese el número de la Cuenta%s: ", nombreCuenta);
            String numeroCuenta = scanner.nextLine();
            if (numeroCuenta.length() != 4) {
                System.out.println("%n**** La entrada no es de 4 cifras, Intente de nuevo ****");
                return null;
            }
            if (validarDigitosNumeroCuenta(numeroCuenta)) {
                System.out.println("%n**** La entrada no es un valor entero, Intente de nuevo ****");
                return null;
            }
            Cuenta cuenta = banco.recuperarCuenta(numeroCuenta);
            if (cuenta == null) {
                System.out.println("%n**** Cuenta No Encontrada ****");
                return null;
            }
            return cuenta;
        }
    }

    private static void solicitarDeposito(){
        Cuenta cuentaDestino = solicitarCuenta("");
        if (cuentaDestino != null){
            if (banco.limiteDeposito(cuentaDestino)){
                System.out.printf("%nLimite de Depositos Diarios Superado%n");
            } else {
                ArrayList<Object> tipoDivisa = null;
                while (true){
                    System.out.println("\nIngrese el tipo de divisa:");
                    System.out.println("1. Pesos (MXN)");
                    System.out.println("2. Dolares (USD)");
                    System.out.println("3. Euros (EU)");
                    String opcion = scanner.nextLine();
                    switch (opcion) {
                        case "1": {
                            tipoDivisa = Banco.MXN;
                            break;
                        }
                        case "2": {
                            tipoDivisa = Banco.USD;
                            break;
                        }
                        case "3": {
                            tipoDivisa = Banco.EU;
                            break;
                        }
                        default: {
                            System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                            continue;
                        }
                    }
                    break;
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
                            System.out.println(banco.depositar(cuentaDestino, cantidadDeposito, tipoDivisa));
                            System.out.println("%n**** Deposito Realizado ****");
                            scanner.nextLine();
                            break;
                        }
                    } catch (SaldoMaximoSuperadoException ex){
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
                        System.out.printf("%n**** Retiro Realizado **** %nSaldo actual: %f%n", banco.retirar(cuenta, cantidadRetiro).getSaldo());
                        scanner.nextLine();
                        break;
                    }
                } catch (FondosInsuficientesException ex){
                    System.out.println(ex.getMessage());
                    scanner.nextLine();
                } catch (InputMismatchException ex){
                    System.out.println("La Cantidad a Depositar Debe ser un Valor Numerico");
                    scanner.nextLine();
                }
            }
        }
    }

    private static void solicitarTransferencia() {
        Cuenta cuentaOrigen = solicitarCuenta(" Origen");
        if (cuentaOrigen != null){
            Cuenta cuentaDestino = solicitarCuenta(" Destino");
            if (cuentaDestino != null){
                while (true){
                    try {
                        System.out.print("Ingrese la cantidad a transferir: ");
                        float cantidadTransferencia = scanner.nextFloat();
                        if (cantidadTransferencia < 0){
                            System.out.println("La cantida no puede ser un valor negativo, Intente de nuevo");
                            scanner.nextLine();
                        } else if (cantidadTransferencia > 2000){
                            System.out.println("La cantidad no puede exceder los 2000 pesos, Intente de nuevo");
                            scanner.nextLine();
                        } else {
                            System.out.println(banco.transferir(cuentaOrigen, cuentaDestino, cantidadTransferencia));
                            scanner.nextLine();
                            break;
                        }
                    } catch (SaldoMaximoSuperadoException | CuentasIgualesException | FondosInsuficientesException ex){
                        System.out.println(ex.getMessage());
                        scanner.nextLine();
                        break;
                    }
                }
            }
        }
    }

    public static void actualizarTransferencia(){
        Transferencia transferencia = consultarTransferenciaCodigo();
        if (transferencia != null){
            while (true){
                try {
                    System.out.print("Ingrese el nuevo monto: ");
                    float nuevoMonto = scanner.nextFloat();
                    if (nuevoMonto < 0){
                        System.out.println("El nuevo monto no puede ser un valor negativo, Intente de nuevo");
                        scanner.nextLine();
                    } else {
                        Cuenta cuentaOrigen = transferencia.getCuentaOrigen();
                        Cuenta cuentaDestino = transferencia.getCuentaDestino();
                        float montoOriginal = transferencia.getMonto();
                        cuentaOrigen.sumarFondos(montoOriginal); cuentaDestino.restarFondos(montoOriginal);
                        cuentaOrigen.restarFondos(nuevoMonto); cuentaDestino.sumarFondos(nuevoMonto);
                        transferencia.setMonto(nuevoMonto);
                        transferencia.setFecha(new GregorianCalendar());
                        System.out.println(transferencia);
                        scanner.nextLine();
                        break;
                    }
                } catch (InputMismatchException ex){
                    System.out.println("La Cantidad a Depositar Debe ser un Valor Numerico");
                    scanner.nextLine();
                }
            }
        }
    }

    public static void listarDepositos(){
        LinkedList<Deposito> depositos = banco.consultarDepositos();
        if (depositos == null){
            System.out.println("No se ha registrado ningun deposito");
        } else {
            System.out.println("%n**** Lista de Depositos ****");
            for (Deposito deposito:depositos){
                System.out.println(deposito);
            }
        }
    }

    public static void listarTransferencias(){
        LinkedList<Transferencia> transferencias = banco.consultarTransferencias();
        if (transferencias == null){
            System.out.println("No se ha registrado ninguna transferencia");
        } else {
            System.out.println("%n**** Lista de Transferencias ****");
            for (Transferencia transferencia: transferencias){
                System.out.println(transferencia);
            }
        }
    }

    public static void consultarDepositoCodigo(){
        try {
            System.out.print("\nIngrese el codigo de deposito: ");
            int codigo = scanner.nextInt();
            if (banco.consultarDepositoCodigo(codigo) == null){
                System.out.println("Codigo de deposito no encontrado");
                scanner.nextLine();
            } else {
                System.out.println(banco.consultarDepositoCodigo(codigo));
                scanner.nextLine();
            }
        } catch (InputMismatchException ex){
            System.out.println("El Codigo de Deposito Debe ser un Valor Numerico");
            scanner.nextLine();
        }
    }

    public static Transferencia consultarTransferenciaCodigo(){
        try {
            System.out.print("\nIngrese el codigo de transferencia: ");
            int codigo = scanner.nextInt();
            Transferencia transferencia = banco.consultarTransferenciaCodigo(codigo);
            if (transferencia == null){
                System.out.println("Codigo de transferencia no encontrado");
                scanner.nextLine();
            } else {
                System.out.println(banco.consultarTransferenciaCodigo(codigo));
                scanner.nextLine();
                return transferencia;
            }
        } catch (InputMismatchException ex){
            System.out.println("El Codigo de Transferencia Debe ser un Valor Numerico");
            scanner.nextLine();
        }
        return null;
    }

    public static void consultarOperacionCodigo(){
        try {
            System.out.print("\nIngrese el codigo de operacion: ");
            int codigo = scanner.nextInt();
            if (banco.consultarOperacion(codigo) == null){
                System.out.println("Codigo de operacion no encontrado");
                scanner.nextLine();
            } else {
                System.out.println(banco.consultarOperacion(codigo));
                scanner.nextLine();
            }
        } catch (InputMismatchException ex){
            System.out.println("El Codigo de Operacion Debe ser un Valor Numerico");
            scanner.nextLine();
        }
    }

    private static boolean validarDigitosNumeroCuenta(String cuenta){
        for (int i = 0; i < cuenta.length(); i++)
            if (!Character.isDigit(cuenta.charAt(i))){
                return true;
            }
        return false;
    }

    private static void solicitarSalir(){
        System.out.println("\nGracias por utilizar nuestros servicios.");
        System.exit(0);
    }
}