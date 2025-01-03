package exceptions;

public class FondosInsuficientesException extends Exception{

    public FondosInsuficientesException(String texto){
        super(texto);
    }
}