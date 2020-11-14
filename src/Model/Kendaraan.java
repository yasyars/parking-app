package Model;

import javax.swing.*;
import java.util.regex.Pattern;

public interface Kendaraan {

    public String getNoPlat();

    public void setNoPlat(String plat_no) throws Exception;

    public User getOwner();

    public void setOwner(User owner);

    public int getIsParked();

    public void setIsParked(int isParked);

    public boolean isPlatNoValid(String plat_no);

//    public String toString();
//    {
//        return this.getTipe() + " - " + this.getNoPlat();
//    }


}

