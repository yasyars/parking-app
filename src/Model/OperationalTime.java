package Model;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class OperationalTime {
    private String day;
    private String openHour;
    private String closeHour;

    public OperationalTime(){};

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpenHour() {
        return openHour;
    }

    public Boolean isOpen(String dateTime){
        //string dalam bentuk format form


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mma");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        int startCompare = localDateTime.toLocalTime().compareTo(this.getOpenHourTime());
        int endCompare = localDateTime.toLocalTime().compareTo(this.getCloseHourTime());


        if (!localDateTime.format(DateTimeFormatter.ofPattern("EEEE")).equals(getHariEnglish(this.getDay()))){
            return false;
        }else if(startCompare<0 || endCompare>0){
            return false;
        }

        return true;

    }

    public String getHariEnglish(String hari){
        hari = hari.toLowerCase();
        if (hari.equals("senin")){
            return "Monday";
        }else if (hari.equals("selasa")){
            return "Tuesday";
        }else if (hari.equals("rabu")){
            return "Wednesday";
        }else if (hari.equals("kamis")){
            return "Thursday";
        }else if (hari.equals("jumat")||hari.equals("jum'at")){
            return "Friday";
        }else if (hari.equals("sabtu")){
            return "Saturday";
        }else if (hari.equals("minggu")){
            return "Sunday";
        }else{
            return null;
        }
    }

    public LocalTime getOpenHourTime(){

//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mma");
        LocalTime openHour = LocalTime.parse(this.getOpenHour());
        return openHour;
    }

    public LocalTime getCloseHourTime(){

        LocalTime closeHour = LocalTime.parse(this.getCloseHour());
        return closeHour;
    }

    public void setOpenHour(String openHour) {

        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }
}
