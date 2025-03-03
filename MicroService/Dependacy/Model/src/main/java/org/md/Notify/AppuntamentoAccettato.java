package org.md.Notify;

public class AppuntamentoAccettato extends Notify{

    private final String tipo = "Appuntamento Accettato";

    public AppuntamentoAccettato() {
        //Server per la seralizzazione
    }

    protected AppuntamentoAccettato(Builder builder) {
        super(builder);
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    public static class Builder extends Notify.Builder<AppuntamentoAccettato.Builder> {
        public Builder(String message) {
            super(message);

        }

        @Override
        public AppuntamentoAccettato build(){
            return new AppuntamentoAccettato(this);
        }

    }

}
