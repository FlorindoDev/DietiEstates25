package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.TranslationToJson;

class A3 extends TranslationToJson implements High {

    private final String nome = "A3";

    @Override
    public String getEnergeticClass() {
        return "A3";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "High";
    }

    public String getNome() {
        return nome;
    }
}