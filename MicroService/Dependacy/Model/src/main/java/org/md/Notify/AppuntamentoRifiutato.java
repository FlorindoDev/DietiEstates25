package org.md.Notify;

public class AppuntamentoRifiutato extends Notify{

    private final String tipo = "Appuntamento Rifiutato";
    public AppuntamentoRifiutato() {
        //Serializzazione
    }

    protected AppuntamentoRifiutato(AppuntamentoRifiutato.Builder builder) {
        super(builder);
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    public static class Builder extends Notify.Builder<AppuntamentoRifiutato.Builder> {
        public Builder(String message) {
            super(message);
        }
        @Override
        public AppuntamentoRifiutato build(){
            return new AppuntamentoRifiutato(this);
        }

    }
}
