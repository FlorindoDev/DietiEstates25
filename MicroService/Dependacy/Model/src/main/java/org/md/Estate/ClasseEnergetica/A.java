package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class A extends Translate implements Medium {

    private static final String NOME = "A";

    @Override
    public String getEnergeticClass() {
        return "A";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Medium";
    }

    public String getNome() {
        return NOME;
    }
}
