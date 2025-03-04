package org.md.Notify;

import org.exc.DietiEstateException;
import org.md.Notify.Interfacce.NotifySeparators;

public class AppuntamentoDaConfermare extends Notify{

    private final String tipo = "Appuntamento Accettato";

    public AppuntamentoDaConfermare() {
        //Serializzazione
    }


    protected AppuntamentoDaConfermare(Builder builder) {
        super(builder);
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public void separator(NotifySeparators split) throws DietiEstateException {
        split.separator(this);
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
