package exceptions;

public class SaldoMaximoSuperadoException extends Exception{

    public SaldoMaximoSuperadoException(String texto){
        super(texto);
    }
}