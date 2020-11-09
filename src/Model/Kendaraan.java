package Model;

import java.util.regex.Pattern;

public class Kendaraan {
    private String noPlat;
    private String tipe;
    private User owner;
    private int isParked;

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) throws Exception {
        if (isPlatNoValid(noPlat,this.tipe)){
            this.noPlat = noPlat;
        }else{
            throw new Exception("Nomor plat tidak valid");
        }
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getIsParked() {
        return isParked;
    }

    public void setIsParked(int isParked) {
        this.isParked = isParked;
    }

    public boolean isPlatNoValid(String plat_no, String tipe){
        String mobilRegex = "^[A-Z]{2}[0-9]{4}[A-Z]{1}$";
        String motorRegex = "^[A-Z]{2}[0-9]{3}[A-Z]{2}$";

        if (tipe.equals("Mobil")){
            Pattern p = Pattern.compile(mobilRegex);
            return p.matcher(plat_no).matches();
        } else {
            Pattern p = Pattern.compile(motorRegex);
            return p.matcher(plat_no).matches();
        }
    }


}

