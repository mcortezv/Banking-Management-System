package bancoplus;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Transferencia implements Operacion{
    public int codigo;
    private GregorianCalendar fecha;
    public Cuenta cuentaOrigen;
    public Cuenta cuentaDestino;
    public float monto;

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, float monto){
        this.codigo = (int) (Math.random() * 99999) + 100000;
        this.fecha = new GregorianCalendar();
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
    }

    @Override
    public int getCodigo(){
        return this.codigo;
    }

    @Override
    public void setFecha(GregorianCalendar fecha){
        this.fecha = fecha;
    }

    @Override
    public GregorianCalendar getFecha(){
        return this.fecha;
    }

    @Override
    public Cuenta getCuentaDestino(){
        return this.cuentaDestino;
    }

    @Override
    public float getMonto(){
        return this.monto;
    }

    public void setMonto(float monto){
        this.monto = monto;
    }

    public Cuenta getCuentaOrigen(){
        return this.cuentaOrigen;
    }

    @Override
    public String toString(){
        return String.format("%nCodigo: %d%n" +
                "Realizada el %02d de %02d de %d a las %02d:%02d%n" +
                "Cuenta Origen: %s ----> Cuenta Destino: %s%n" +
                "Cantidad Transferida: %.2f MXN%n", this.getCodigo(), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE),
                this.getCuentaOrigen().getNumero(), this.getCuentaDestino().getNumero(), this.getMonto());
    }
}