package org.md.Notify;

public class AppuntamentoRifiutato extends Notify{

    private static final String TIPO = "Appuntamento Rifiutato";
    public AppuntamentoRifiutato() {
        //Serializzazione
    }

    protected AppuntamentoRifiutato(AppuntamentoRifiutato.Builder builder) {
        super(builder);
    }

    @Override
    public String getTipo() {
        return TIPO;
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
