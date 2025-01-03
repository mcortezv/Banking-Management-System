package bancoplus;
import bancoplus.excepciones.*;
import java.util.*;

public class Banco {
    protected final static ArrayList<Object> MXN = new ArrayList<>(Arrays.asList(1, "MXN"));
    protected final static ArrayList<Object> USD = new ArrayList<>(Arrays.asList(17, "USD"));
    protected final static ArrayList<Object> EU = new ArrayList<>(Arrays.asList(20, "EU"));
    private final List<Cuenta> cuentas;
    private final LinkedList<Deposito> depositos;
    private final LinkedList<Transferencia> transferencias;
    private final Map<Integer, Operacion> operaciones;

    public Banco() {
        this.cuentas = new LinkedList<>();
        this.depositos = new LinkedList<>();
        this.transferencias = new LinkedList<>();
        this.operaciones = new HashMap<>();
        this.cuentas.add(new Cuenta("1111", 1000));
        this.cuentas.add(new Cuenta("2222", 500));
        this.cuentas.add(new Cuenta("3333", 2000));
    }

    public Cuenta recuperarCuenta(String numeroCuenta){
        return this.cuentas.stream()
                            .filter(cuenta -> cuenta.getNumero().equals(numeroCuenta))
                            .findAny()
                            .orElse(null);
    }
    
    public Deposito depositar(Cuenta cuentaDestino, float cantidad, ArrayList<Object>tipoDivisa) throws ExcepcionSaldoMaximoSuperado {
        cantidad = cantidad * (Integer) tipoDivisa.getFirst();
        if (cantidad + cuentaDestino.getSaldo() > 5000){
            throw new ExcepcionSaldoMaximoSuperado("Saldo maximo superado, Intente de nuevo");
        }
        cuentaDestino.sumarFondos(cantidad);
        Deposito deposito;
        do {
            deposito = new Deposito(cuentaDestino, cantidad, tipoDivisa);
        } while (operaciones.containsKey(deposito.getCodigo()));
        this.depositos.add(deposito);
        this.operaciones.put(deposito.getCodigo(), deposito);
        return deposito;
    }

    public boolean limiteDeposito(Cuenta cuentaDestino){
        GregorianCalendar fechaHoy = new GregorianCalendar();
        int limite = 0;
        for (Deposito deposito: this.depositos){
            if (deposito.getCuentaDestino().equals(cuentaDestino) && deposito.getFecha().get(Calendar.DAY_OF_MONTH) == fechaHoy.get(Calendar.DAY_OF_MONTH) &&
                    deposito.getFecha().get(Calendar.MONTH) == fechaHoy.get(Calendar.MONTH) && deposito.getFecha().get(Calendar.YEAR) == fechaHoy.get(Calendar.YEAR)){
                limite ++;
            }
        }
        return limite >= 3;
    }

    public Cuenta retirar(Cuenta cuenta, float cantidad) throws ExcepcionFondosInsuficientes {
        if (cantidad > cuenta.getSaldo()){
            throw new ExcepcionFondosInsuficientes("Fondos insuficientes, Intente de nuevo");
        }
        cuenta.restarFondos(cantidad);
        return cuenta;
    }

    public Transferencia transferir(Cuenta cuentaOrigen, Cuenta cuentaDestino, float cantidadTransferencia) throws ExcepcionFondosInsuficientes, ExcepcionSaldoMaximoSuperado, ExcepcionCuentasIguales {
        if (cuentaOrigen.equals(cuentaDestino)){
            throw new ExcepcionCuentasIguales("Las cuentas son iguales, Intente de nuevo");
        }
        if (cantidadTransferencia > cuentaOrigen.getSaldo()){
            throw new ExcepcionFondosInsuficientes("Fondos insuficientes, Intente de nuevo");
        }
        if (cantidadTransferencia + cuentaDestino.getSaldo() > 5000){
            throw new ExcepcionSaldoMaximoSuperado("Saldo maximo superado, Intente de nuevo");
        }
        Transferencia transferencia;
        do {
            transferencia = new Transferencia(cuentaOrigen, cuentaDestino, cantidadTransferencia);
        } while (operaciones.containsKey(transferencia.getCodigo()));
        cuentaOrigen.restarFondos(cantidadTransferencia);
        cuentaDestino.sumarFondos(cantidadTransferencia);
        this.transferencias.add(transferencia);
        this.operaciones.put(transferencia.getCodigo(), transferencia);
        return transferencia;
    }

    public LinkedList<Deposito> consultarDepositos(){
        if (depositos.isEmpty()){
            return null;
        }
        return this.depositos;
    }

    public LinkedList<Transferencia> consultarTransferencias(){
        if (transferencias.isEmpty()){
            return null;
        }
        return this.transferencias;
    }

    public Deposito consultarDepositoCodigo(int codigo){
        for (Deposito deposito: depositos){
            if (deposito.getCodigo() == codigo){
                return deposito;
            }
        }
        return null;
    }

    public Transferencia consultarTransferenciaCodigo(int codigo){
        for (Transferencia transferencia: transferencias){
            if (transferencia.getCodigo() == codigo){
                return transferencia;
            }
        }
        return null;
    }

    public Operacion consultarOperacion(int codigo){
        if (operaciones.isEmpty()){
            return null;
        }
        return operaciones.get(codigo);
    }
}