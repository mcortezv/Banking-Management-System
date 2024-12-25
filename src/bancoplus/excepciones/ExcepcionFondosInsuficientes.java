package bancoplus.excepciones;

public class ExcepcionFondosInsuficientes extends Exception{

    public ExcepcionFondosInsuficientes(String texto){
        super(texto);
    }
}
