package bankingManagementSystem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Deposito implements Operacion {
    private int codigo;
    private GregorianCalendar fecha;
    private Cuenta cuentaDestino;
    private float monto;
    private ArrayList<Object> tipoDivisa;

    public Deposito(Cuenta cuentaDestino, float monto, ArrayList<Object> tipoDivisa){
        this.codigo = (int) (Math.random() * 99999) + 100000;
        this.fecha = new GregorianCalendar();
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.tipoDivisa = tipoDivisa;
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

    public ArrayList<Object> getTipoDivisa(){
        return this.tipoDivisa;
    }

    @Override
    public String toString(){
        return String.format("%nCodigo: %d%n" +
                        "Realizado el %02d de %02d de %d a las %02d:%02d%n" +
                        "Cuenta Destino: %s%n" +
                        "Cantidad Depositada: %.2f %s%n" +
                        "Tipo Cambio: 1 %s = %d MXN%n" +
                        "Saldo de la Cuenta: %.2f MXN%n", this.getCodigo(), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE),
                this.getCuentaDestino().getNumero(), this.getMonto(), this.getTipoDivisa().get(1), this.getTipoDivisa().get(1),
                (int) this.getTipoDivisa().get(0), this.getCuentaDestino().getSaldo());
    }
}