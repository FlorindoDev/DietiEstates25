package org.md.Estate.ClasseEnergetica;

import org.md.Serializzazione.TranslationToJson;

class D extends TranslationToJson implements Low {

    private final String nome = "D";

    @Override
    public String getEnergeticClass() {
        return "D";
    }

    @Override
    public String getEnergeticRangeClass() {
        return "Low";
    }

    public String getNome() {
        return nome;
    }
}