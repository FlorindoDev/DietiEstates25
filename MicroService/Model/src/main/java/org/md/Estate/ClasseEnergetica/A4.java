package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.TranslationToJson;

class A4 extends TranslationToJson implements High {

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
