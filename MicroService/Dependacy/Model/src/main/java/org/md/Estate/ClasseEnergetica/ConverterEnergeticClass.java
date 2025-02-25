package org.md.Estate.ClasseEnergetica;


public class ConverterEnergeticClass {

    private ConverterEnergeticClass(){}
    public static EnergeticClass traslateFromString(String classeenergetica) {

        switch (classeenergetica){
            case "A":
                return new A();

            case "A2":
                return new A2();

            case "A3":
                return new A3();

            case "A4":
                return new A4();

            case "B":
                return new B();

            case "C":
                return new C();

            case "D":
                return new D();

            case "E":
                return new E();

            case "F":
                return new F();

            default:
                return  null;

        }
    }
}
