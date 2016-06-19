package com.example.simone.booksharing;

/**
 * Created by Utente on 17/05/2016.
 */


public class BookSharedForAdapter {
    public Prestito.Stato stato;
    public String richiedente;
    public String ISBN;
    public String dataPrestito;

    BookSharedForAdapter(String stato, String ISBN, String dataPrestito,String richiedente){

        this.dataPrestito=dataPrestito;
        this.ISBN=ISBN;
        this.richiedente=richiedente;
        this.stato=Prestito.setStato(stato);

    }
}
