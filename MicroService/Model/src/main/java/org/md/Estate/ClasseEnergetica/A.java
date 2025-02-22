package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.TranslationToJson;

class A extends TranslationToJson implements Medium {

    private final String nome = "A";

    @Override
    public String getEnergeticClass() {
        return "A";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Medium";
    }

    public String getNome() {
        return nome;
    }
}
