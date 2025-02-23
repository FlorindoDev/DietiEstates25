package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.Translate;

class A4 extends Translate implements High {

    private final String nome = "A4";

    @Override
    public String getEnergeticClass() {
        return "A4";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "High";
    }

    public String getNome() {
        return nome;
    }
}
