package bancoplus;
import bancoplus.excepciones.ExcepcionCuentaNoEcontrada;
import bancoplus.excepciones.ExcepcionCuentasIguales;
import bancoplus.excepciones.ExcepcionFondosInsuficientes;
import bancoplus.excepciones.ExcepcionSaldoMaximoSuperado;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {
    private final static Scanner scanner = new Scanner(System.in);
    private final static Banco banco = new Banco();

    public static void main(String[] args) {
        if(!"UTF-8".equals(System.out.charset().displayName())){
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        }
        while (true) {
            try {
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
                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1 -> {
                        solicitarDeposito();
                    }
                    case 2 -> {
                        solicitarRetiro();
                    }
                    case 3 -> {
                        solicitarTransferencia();
                    }
                    case 4 -> {
                        consultarDepositos();
                    }
                    case 5 -> {
                        consultarTransferencias();
                    }
                    case 6 -> {
                        consultarDepositoCodigo();
                    }
                    case 7 -> {
                        consultarTransferenciaCodigo();
                    }
                    case 8 -> {
                        actualizarTransferencia();
                    }
                    case 9 -> {
                        consultarOperacionCodigo();
                    }
                    case 10 -> {
                        solicitarSalir();
                    }
                    default -> System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                }
            } catch (InputMismatchException ex){
                System.out.println("La entrada no es un valor entero, Intente de nuevo");
                scanner.nextLine();
            }
        }
    }

    private static void solicitarDeposito(){
        if (banco.limiteDeposito()){
            System.out.printf("%nLimite de Depositos Diarios Superado%n");
        } else {
            String cuentaDestino = "";
            while (true){
                try {
                    System.out.print("\nIngrese el número de cuenta destino: ");
                    cuentaDestino = scanner.next();
                    if (cuentaDestino.length() != 4){
                        System.out.println("La entrada no es de 4 cifras, Intente de nuevo");
                        scanner.nextLine();
                        continue;
                    }
                    if (!validarDigitosNumeroCuenta(cuentaDestino)){
                        System.out.println("La entrada no es un valor entero, Intente de nuevo");
                        scanner.nextLine();
                        continue;
                    }
                    banco.validarCuenta(cuentaDestino);
                } catch (ExcepcionCuentaNoEcontrada ex){
                    System.out.print(ex.getMessage());
                    continue;
                }
                break;
            }
            while (true) {
                try {
                    System.out.println("\nIngrese el tipo de divisa:");
                    System.out.println("1. Pesos (MXN)");
                    System.out.println("2. Dolares (USD)");
                    System.out.println("3. Euros (EU)");
                    int opcion = scanner.nextInt();
                    switch (opcion) {
                        case 1 -> {
                            depositar(cuentaDestino, Banco.MXN);
                        }
                        case 2 -> {
                            depositar(cuentaDestino, Banco.USD);
                        }
                        case 3 -> {
                            depositar(cuentaDestino, Banco.EU);
                        }
                        default -> System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    }
                } catch (InputMismatchException ex){
                    System.out.println("La entrada no es un valor entero, Intente de nuevo");
                    scanner.nextLine();
                    continue;
                }
                break;
            }
        }
    }

    private static void depositar(String cuentaDestino, float tipoDivisa){
        while (true){
            try {
                System.out.print("Ingrese la cantidad a depositar: ");
                float cantidadDeposito = scanner.nextFloat();
                if (cantidadDeposito < 0){
                    System.out.println("La cantida no puede ser un valor negativo, Intente de nuevo");
                    continue;
                }
                Cuenta cuenta = banco.depositar(cuentaDestino, cantidadDeposito * tipoDivisa, tipoDivisa);
                System.out.println("%n**** Deposito Realizado ****");
                System.out.println(cuenta);
                break;
            } catch (InputMismatchException ex){
                System.out.println("La entrada no es un valor entero, Intente de nuevo");
                scanner.nextLine();
            } catch (ExcepcionSaldoMaximoSuperado ex){
                System.out.println(ex.getMessage());
                scanner.nextLine();
                break;
            }
        }
    }

    private static void solicitarRetiro(){
        String cuentaDestino = "";
        while (true){
            try {
                System.out.print("\nIngrese el número de cuenta destino: ");
                cuentaDestino = scanner.next();
                if (cuentaDestino.length() != 4){
                    System.out.println("La entrada no es de 4 cifras, Intente de nuevo");
                    scanner.nextLine();
                    continue;
                }
                if (!validarDigitosNumeroCuenta(cuentaDestino)){
                    System.out.println("La entrada no es un valor entero, Intente de nuevo");
                    scanner.nextLine();
                    continue;
                }
                banco.validarCuenta(cuentaDestino);
            } catch (ExcepcionCuentaNoEcontrada ex){
                System.out.print(ex.getMessage());
                continue;
            }
            break;
        }
        while (true){
            try {
                System.out.print("Ingrese la cantidad a retirar: ");
                float cantidadRetiro = scanner.nextFloat();
                if (cantidadRetiro < 0){
                    System.out.println("La cantidad no puede ser un valor negativo, Intente de nuevo");
                    continue;
                }
                Cuenta cuenta = banco.retirar(cuentaDestino, cantidadRetiro);
                System.out.printf("%n**** Retiro Realizado **** %nSaldo actual: %f%n", cuenta.getSaldo());
                break;
            } catch (ExcepcionFondosInsuficientes ex){
                System.out.println(ex.getMessage());
                scanner.nextLine();
            }
        }
    }

    private static void solicitarTransferencia() {
        String cuentaOrigen = "";
        while (true) {
            try {
                System.out.print("\nIngrese el número de cuenta destino: ");
                cuentaOrigen = scanner.next();
                if (cuentaOrigen.length() != 4) {
                    System.out.println("La entrada no es de 4 cifras, Intente de nuevo");
                    scanner.nextLine();
                    continue;
                }
                if (!validarDigitosNumeroCuenta(cuentaOrigen)) {
                    System.out.println("La entrada no es un valor entero, Intente de nuevo");
                    scanner.nextLine();
                    continue;
                }
                banco.validarCuenta(cuentaOrigen);
            } catch (ExcepcionCuentaNoEcontrada ex) {
                System.out.print(ex.getMessage());
                continue;
            }
            break;
        }
        String cuentaDestino = "";
        while (true) {
            try {
                System.out.print("\nIngrese el número de cuenta destino: ");
                cuentaDestino = scanner.next();
                if (cuentaDestino.length() != 4) {
                    System.out.println("La entrada no es de 4 cifras, Intente de nuevo");
                    scanner.nextLine();
                    continue;
                }
                if (!validarDigitosNumeroCuenta(cuentaDestino)) {
                    System.out.println("La entrada no es un valor entero, Intente de nuevo");
                    scanner.nextLine();
                    continue;
                }
                banco.validarCuenta(cuentaDestino);
            } catch (ExcepcionCuentaNoEcontrada ex) {
                System.out.print(ex.getMessage());
                continue;
            }
            break;
        }
        while (true){
            try {
                System.out.print("Ingrese la cantidad a transferir: ");
                float cantidadTransferencia = scanner.nextFloat();
                if (cantidadTransferencia < 0){
                    System.out.println("La cantida no puede ser un valor negativo, Intente de nuevo");
                    continue;
                }
                if (cantidadTransferencia > 2000){
                    System.out.println("La cantidad no puede exceder los 2000 pesos, Intente de nuevo");
                    continue;
                }
                banco.transferir(cuentaOrigen, cuentaDestino, cantidadTransferencia);
                break;
            } catch (ExcepcionSaldoMaximoSuperado ex){
                System.out.println(ex.getMessage());
                scanner.nextLine();
            } catch (ExcepcionFondosInsuficientes ex){
                System.out.println(ex.getMessage());
                scanner.nextLine();
            } catch (ExcepcionCuentasIguales ex) {
                System.out.println(ex.getMessage());
                scanner.nextLine();
                break;
            }
        }
    }

    private static boolean validarDigitosNumeroCuenta(String cuentaDestino){
        for (int i = 0; i < cuentaDestino.length(); i++)
            if (!Character.isDigit(cuentaDestino.charAt(i))){
                return false;
            }
        return true;
    }

    public static void consultarDepositos(){
        if (banco.consultarDepositos() == null){
            System.out.println("No se ha registrado ningun deposito");
        } else {
            System.out.println("%n**** Lista de Depositos ****");
            for (Deposito deposito:banco.consultarDepositos()){
                System.out.println(deposito);
            }
        }
    }

    public static void consultarTransferencias(){
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
