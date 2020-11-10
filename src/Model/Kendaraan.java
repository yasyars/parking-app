package Model;

import java.util.regex.Pattern;

public class Kendaraan {
    private String noPlat;
    private String tipe;
    private int owner;
    
    public Kendaraan(){}

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}

