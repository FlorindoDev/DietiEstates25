package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class A4 extends Translate implements High {

    private static final String NOME = "A4";

    @Override
    public String getEnergeticClass() {
        return "A4";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "High";
    }

    public String getNome() {
        return NOME;
    }
}
