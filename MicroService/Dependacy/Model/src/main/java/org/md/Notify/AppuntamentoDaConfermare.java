package org.md.Notify;

public class AppuntamentoDaConfermare extends Notify{

    private static final String TIPO = "Appuntamento Accettato";

    public AppuntamentoDaConfermare() {
        //Serializzazione
    }


    protected AppuntamentoDaConfermare(Builder builder) {
        super(builder);
    }

    @Override
    public String getTipo() {
        return TIPO;
    }

    public static class Builder extends Notify.Builder<AppuntamentoDaConfermare.Builder> {
        public Builder(String message) {
            super(message);
        }

        @Override
        public AppuntamentoDaConfermare build(){
            return new AppuntamentoDaConfermare(this);
        }

    }
}
