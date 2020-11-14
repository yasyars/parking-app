package Model;

import java.util.regex.Pattern;

public class Mobil implements Kendaraan{
    private String noPlat;
    private String tipe;
    private User owner;
    private int isParked;

    public Mobil(){ }

    public String getNoPlat(){ return noPlat; }

    public void setNoPlat(String plat_no) throws Exception
    {
        if (isPlatNoValid(plat_no)){
            this.noPlat = plat_no;
        }else{
            throw new Exception("Nomor plat tidak valid");
        }
    }

//    public String getTipe()
//    {
//        return tipe;
//    }
//
//    public void setTipe(String tipe) {
//        this.tipe = tipe;
//    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public int getIsParked()
    {
        return isParked;
    }

    public void setIsParked(int isParked)
    {
        this.isParked = isParked;
    }

    public boolean isPlatNoValid(String plat_no)//, String tipe)
    {
        String regex = "^[A-Z]{2}\\s[0-9]{4}\\s[A-Z]{1}$";
        //String motorRegex = "^[A-Z]{2}\\s[0-9]{3}\\s[A-Z]{2}$";

        Pattern p = Pattern.compile(regex);
        return p.matcher(plat_no).matches();
    }

    public String toString()
    {
        return this.getClass().getSimpleName() + " - " + this.getNoPlat();
    }

}
