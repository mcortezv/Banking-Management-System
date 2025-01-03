package bankingManagementSystem;
public class Cuenta {
    private final String numero;
    private float saldo;

    public Cuenta(String numero, float saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public float getSaldo() {
        return saldo;
    }

    public void restarFondos(float cantidad){
        this.saldo -= cantidad;
    }

    public void sumarFondos(float cantidad){
        this.saldo += cantidad;
    }

    @Override
    public boolean equals(Object cuentaDestino){
        if (cuentaDestino == null){
            return false;
        }
        if (this.getClass() != cuentaDestino.getClass()){
            return false;
        }
        Cuenta cuenta = (Cuenta) cuentaDestino;
        return this.getNumero().equals(cuenta.getNumero());
    }

    @Override
    public String toString(){
        return String.format("Codigo de Cuenta: %s%n" +
                "Saldo Actual: %.2f", this.getNumero(), this.getSaldo());
    }
}