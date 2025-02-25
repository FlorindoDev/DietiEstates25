package org.md.Estate.ClasseEnergetica.Mode;

public class ConverterMode {

    private ConverterMode(){}

    public static Mode traslateFromString(String modalita) {
        switch (modalita){
            case "Affitto":
                return new Affitto();
            case "Vendita":
                return new Vendita();
            default:
                return null;

        }
    }
}
