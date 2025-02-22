package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class C extends Translate implements Medium {

    private final String nome = "C";

    @Override
    public String getEnergeticClass() {
        return "C";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Medium";
    }

    public String getNome() {
        return nome;
    }
}