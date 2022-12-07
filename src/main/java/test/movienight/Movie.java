package test.movienight;

import java.util.Arrays;

public class Movie {

    private int id;
    private String imgSrc;
    private String nume;
    private String descriere;
    private int durata;
    private String[] genuri;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", imgSrc='" + imgSrc + '\'' +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", durata=" + durata +
                ", genuri=" + Arrays.toString(genuri) +
                '}';
    }

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String[] getGenuri() {
        return genuri;
    }

    public void setGenuri(String[] genuri) {
        this.genuri = genuri;
    }

    public Movie(int id, String imgSrc, String nume, String descriere, int durata, String[] genuri) {
        this.id = id;
        this.imgSrc = imgSrc;
        this.nume = nume;
        this.descriere = descriere;
        this.durata = durata;
        this.genuri = genuri;
    }
}
