package com.skripsi.coba.model;

/**
 * Created by gandhi on 9/27/17.
 */

public class Model_Pencarian {
    private String NoAyat;
    private String NamaSurah;
    private String TextIndo;
    private String TextArab;



    private String NoSurah;

    public Model_Pencarian(){}
    public Model_Pencarian(String NoAyat, String NamaSurah, String TextIndo, String TextArab,String NoSurah){
        this.NoAyat = NoAyat;
        this.NamaSurah = NamaSurah;
        this.TextIndo = TextIndo;
        this.TextArab = TextArab;
        this.NoSurah = NoSurah;
    }

    public String getNoAyat() {
        return NoAyat;
    }

    public void setNoAyat(String noAyat) {
        NoAyat = noAyat;
    }

    public String getNamaSurah() {
        return NamaSurah;
    }

    public void setNamaSurah(String namaSurah) {
        NamaSurah = namaSurah;
    }

    public String getTextArab() {
        return TextArab;
    }

    public void setTextArab(String textArab) {
        TextArab = textArab;
    }

    public String getTextIndo() {
        return TextIndo;
    }

    public void setTextIndo(String textIndo) {
        TextIndo = textIndo;
    }
    public String getNoSurah() {
        return NoSurah;
    }

    public void setNoSurah(String noSurah) {
        NoSurah = noSurah;
    }
}
