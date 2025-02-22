package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.TranslationToJson;

class A2 extends TranslationToJson implements High {

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
