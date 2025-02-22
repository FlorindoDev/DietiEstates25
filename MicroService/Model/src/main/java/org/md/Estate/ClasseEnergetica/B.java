package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.TranslationToJson;

class B extends TranslationToJson implements Medium {

    private final String nome = "B";

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
