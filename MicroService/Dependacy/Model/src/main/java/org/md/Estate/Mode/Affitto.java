package org.md.Estate.Mode;

import org.md.Serializzazione.Translate;

public class Affitto extends Translate implements Mode{

    private static final String NAME = "Affitto";

    @Override
    public String getName() {
        return NAME;
    }
}
