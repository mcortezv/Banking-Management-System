package bancoplus;
import java.util.GregorianCalendar;

public interface Operacion {
    public abstract int getCodigo();
    public abstract void setFecha(GregorianCalendar fecha);
    public abstract GregorianCalendar getFecha();
    public abstract Cuenta getCuentaDestino();
    public abstract float getMonto();
}