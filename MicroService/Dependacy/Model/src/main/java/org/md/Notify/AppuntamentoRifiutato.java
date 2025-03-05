package org.md.Notify;

import org.exc.DietiEstateException;
import org.md.Notify.Interfacce.NotifySeparators;

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

    @Override
    public void separator(NotifySeparators split) throws DietiEstateException {
        split.separator(this);
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
