package bancoplus.excepciones;

public class ExcepcionSaldoMaximoSuperado extends Exception{

    public ExcepcionSaldoMaximoSuperado(String texto){
        super(texto);
    }
}
