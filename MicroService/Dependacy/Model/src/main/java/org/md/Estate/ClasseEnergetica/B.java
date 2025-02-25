package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class B extends Translate implements Medium {

    private static final String nome = "B";

    @Override
    public String getEnergeticClass() {
        return "B";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Medium";
    }

    public String getNome() {
        return nome;
    }
}
