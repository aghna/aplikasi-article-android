package id.ac.uin_malang.application;

/**
 * Created by lenovo on 29/11/17.
 */

public class ModelListAdmin {
    String id, judul, gambar, isi, tanggal, author;

    public ModelListAdmin(){};

    public ModelListAdmin(String id, String judul, String gambar, String isi, String tanggal, String author) {
        this.id = id;
        this.judul = judul;
        this.gambar = gambar;
        this.isi = isi;
        this.tanggal = tanggal;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
