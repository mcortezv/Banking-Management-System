package bancoplus;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Deposito implements Operacion{
    private int codigo;
    private GregorianCalendar fecha;
    private Cuenta cuentaDestino;
    private float cantidad;
    private float tipoDivisa;
    private String moneda;

     public Deposito(Cuenta cuentaDestino, float cantidad, float tipoDivisa){
         this.codigo = (int) (Math.random() * 99999) + 100000;
         this.fecha = new GregorianCalendar();
         this.cuentaDestino = cuentaDestino;
         this.cantidad = cantidad;
         this.tipoDivisa = tipoDivisa;
         if (this.tipoDivisa == Banco.MXN){
             this.moneda = "MXN";
         } else if (this.tipoDivisa == Banco.USD){
             this.moneda = "USD";
         } else if (this.tipoDivisa == Banco.EU){
             this.moneda = "EU";
         }
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

    public Cuenta getCuentaDestino(){
        return this.cuentaDestino;
    }

    public float getCantidad(){
        return this.cantidad;
    }

    public float getTipoDivisa(){
        return this.tipoDivisa;
    }

    public String getMoneda(){
        return this.moneda;
    }

    @Override
    public String toString(){
         return String.format("%nCodigo: %d%n" +
         "Realizado el %02d de %02d de %d a las %02d:%02d%n" +
         "Cuenta Destino: %s%n" +
                 "Cantidad Depositada: %.2f %s%n" +
                 "Tipo Cambio: 1 %s = %.2f MXN%n" +
                 "Saldo de la Cuenta: %.2f MXN%n", this.getCodigo(), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                 Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR),
                 Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE),
                 this.getCuentaDestino().getNumero(), this.getCantidad(), this.getMoneda(), this.getMoneda(),
                 this.getTipoDivisa(), this.getCuentaDestino().getSaldo());
    }
}
