package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class D extends Translate implements Low {

    private static final String NOME = "D";

    @Override
    public String getEnergeticClass() {
        return "D";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Low";
    }

    public String getNome() {
        return NOME;
    }
}