package bancoplus;
import bancoplus.excepciones.ExcepcionCuentaNoEcontrada;
import bancoplus.excepciones.ExcepcionCuentasIguales;
import bancoplus.excepciones.ExcepcionFondosInsuficientes;
import bancoplus.excepciones.ExcepcionSaldoMaximoSuperado;
import java.util.*;


public class Banco {
    protected final static float MXN = 1;
    protected final static float USD = 17;
    protected final static float EU = 20;
    private final List<Cuenta> cuentas;
    private final LinkedList<Deposito> depositos;
    private final LinkedList<Transferencia> transferencias;
    private final GregorianCalendar fechaHoy;
    private final Map<Integer, Operacion> operaciones;

    public Banco() {
        this.cuentas = new LinkedList<>();
        this.depositos = new LinkedList<>();
        this.transferencias = new LinkedList<>();
        this.fechaHoy = new GregorianCalendar();
        this.operaciones = new HashMap<>();
        this.cuentas.add(new Cuenta("1111", 1000));
        this.cuentas.add(new Cuenta("2222", 500));
        this.cuentas.add(new Cuenta("3333", 2000));
    }

    private Cuenta recuperarCuenta(String numeroCuenta){
        Cuenta cuentaRecuperada = this.cuentas.stream()
                            .filter(cuenta -> cuenta.getNumero().equals(numeroCuenta))
                            .findAny()
                            .orElse(null);
        return cuentaRecuperada;
    }

    public void validarCuenta(String numeroCuenta) throws ExcepcionCuentaNoEcontrada {
        Cuenta validarCuenta = this.cuentas.stream()
                .filter(cuenta -> cuenta.getNumero().equals(numeroCuenta))
                .findAny()
                .orElse(null);
        if (validarCuenta == null){
            throw new ExcepcionCuentaNoEcontrada("Cuenta no encontrada");
        }
    }
    
    public Cuenta depositar(String numeroCuenta, float cantidad, float tipoDivisa) throws ExcepcionSaldoMaximoSuperado {
        Cuenta cuenta = this.recuperarCuenta(numeroCuenta);
        if (cantidad + cuenta.getSaldo() > 5000){
            throw new ExcepcionSaldoMaximoSuperado("Saldo maximo superado, Intente de nuevo");
        }
        cuenta.sumarFondos(cantidad);
        Deposito deposito;
        do {
            deposito = new Deposito(cuenta, cantidad, tipoDivisa);
        } while (operaciones.containsKey(deposito.getCodigo()));
        this.depositos.add(deposito);
        this.operaciones.put(deposito.getCodigo(), deposito);
        return cuenta;
    }

    public boolean limiteDeposito(){
        int limite = 0;
        for (Deposito deposito: this.depositos){
            if (deposito.getFecha().get(Calendar.DAY_OF_MONTH) == this.fechaHoy.get(Calendar.DAY_OF_MONTH) &&
                    deposito.getFecha().get(Calendar.MONTH) == this.fechaHoy.get(Calendar.MONTH) &&
                    deposito.getFecha().get(Calendar.YEAR) == this.fechaHoy.get(Calendar.YEAR)){
                limite ++;
            }
        }
        if (limite >= 3){
            return true;
        }
        return false;
    }

    public Cuenta retirar(String numeroCuenta, float cantidad) throws ExcepcionFondosInsuficientes {
        Cuenta cuenta = this.recuperarCuenta(numeroCuenta);
        if (cantidad > cuenta.getSaldo()){
            throw new ExcepcionFondosInsuficientes("Fondos insuficientes, Intente de nuevo");
        }
        cuenta.restarFondos(cantidad);
        return cuenta;
    }

    public void transferir(String cuentaOrigen, String cuentaDestino, float cantidadTransferencia) throws ExcepcionFondosInsuficientes, ExcepcionSaldoMaximoSuperado, ExcepcionCuentasIguales {
        Cuenta origen = recuperarCuenta(cuentaOrigen);
        Cuenta destino = recuperarCuenta(cuentaDestino);
        if (origen.equals(destino)){
            throw new ExcepcionCuentasIguales("Las cuentas son iguales, Intente de nuevo");
        }
        if (cantidadTransferencia > origen.getSaldo()){
            throw new ExcepcionFondosInsuficientes("Fondos insuficientes, Intente de nuevo");
        }
        if (cantidadTransferencia + destino.getSaldo() > 5000){
            throw new ExcepcionSaldoMaximoSuperado("Saldo maximo superado, Intente de nuevo");
        }
        Transferencia transferencia;
        do {
            transferencia = new Transferencia(origen, destino, cantidadTransferencia);
        } while (operaciones.containsKey(transferencia.getCodigo()));
        origen.restarFondos(cantidadTransferencia);
        destino.sumarFondos(cantidadTransferencia);
        this.transferencias.add(transferencia);
        this.operaciones.put(transferencia.getCodigo(), transferencia);
        System.out.println(transferencia);
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
        if (depositos.isEmpty()){
            return null;
        }
        for (Deposito deposito: depositos){
            if (deposito.getCodigo() == codigo){
                return deposito;
            }
        }
        return null;
    }

    public Transferencia consultarTransferenciaCodigo(int codigo){
        if (transferencias.isEmpty()){
            return null;
        }
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
        } else {
            return operaciones.get(codigo);
        }
    }
}
