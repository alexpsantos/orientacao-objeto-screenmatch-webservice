package br.com.alex.screenmatch.modelos;

public class TituloOmdb {

    String title;
    String year;
    String runtime;

    @Override
    public String toString() {
        return "Nome: " + this.title + " (" + this.year+ ")";
    }

}
