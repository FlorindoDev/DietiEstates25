package org.md.Estate.Status;


public class ConverterStatus {

    private ConverterStatus(){}
    public static Status traslateFromString(String stato) {

        switch (stato){
            case "Nuovo":
                return new New();

            case "Ottimo":
                return new Excellent();

            case "Buono":
                return new Good();

            case "Da ristrutturare":
                return new ToBeRenovated();

            default:
                return  null;

        }


    }
}
