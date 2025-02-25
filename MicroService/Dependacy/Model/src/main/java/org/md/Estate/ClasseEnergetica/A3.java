package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class A3 extends Translate implements High {

    private static final String NOME = "A3";

    @Override
    public String getEnergeticClass() {
        return "A3";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "High";
    }

    public String getNome() {
        return NOME;
    }
}