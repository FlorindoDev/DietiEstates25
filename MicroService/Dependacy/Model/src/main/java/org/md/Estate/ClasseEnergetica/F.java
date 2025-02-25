package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class F extends Translate implements Low {
    private static final String NOME = "F";
    @Override
    public String getEnergeticClass() {
        return "F";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Low";
    }

    public String getNome() {
        return NOME;
    }
}
