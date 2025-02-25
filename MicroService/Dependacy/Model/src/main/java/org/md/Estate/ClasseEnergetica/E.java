package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class E extends Translate implements Low {

    private static final String NOME = "E";
    @Override
    public String getEnergeticClass() {
        return "E";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Low";
    }

    public String getNome() {
        return NOME;
    }
}
