package com.example.simone.booksharing;

/**
 * Created by Utente on 19/06/2016.
 */
public class Prestito {
    public enum Stato{incorso,nonconfermato,inrestituzione,storico};
    public static Stato setStato(String s){
        switch (s){
            case "incorso":
                return Stato.incorso;
            case "inrestituzione":
                return Stato.inrestituzione;
            case "nonconfermato":
                return Stato.nonconfermato;
            case "storico":
                return Stato.storico;
            default:
                return null;
        }
    }
}
