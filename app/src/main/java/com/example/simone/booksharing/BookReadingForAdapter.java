package com.example.simone.booksharing;

/**
 * Created by Utente on 17/05/2016.
 */
public class BookReadingForAdapter {
    public String isbn;
    public String proprietario;
    public String dataprestito;


    BookReadingForAdapter(String isbn,String proprietario,String dataprestito){
        this.dataprestito=dataprestito;
        this.isbn=isbn;
        this.proprietario=proprietario;

    }
}
