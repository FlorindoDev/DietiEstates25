package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class A2 extends Translate implements High {

    private final String nome = "A2";
    @Override
    public String getEnergeticClass() {
        return "A2";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "High";
    }

    public String getNome() {
        return nome;
    }
}
