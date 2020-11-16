package Model;

import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static Helper.Translate.getHariEnglish;

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

    public Boolean isOpen(DateTimePicker dateTimePicker){
        //string dalam bentuk format form


        LocalDate dateLocal= dateTimePicker.datePicker.getDate();
        LocalTime timeLocal= dateTimePicker.timePicker.getTime();

        LocalDateTime localDateTime = LocalDateTime.of(dateLocal,timeLocal);
//
//        try{
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy h:mma");
//            dateTime = LocalDateTime.parse(str, formatter);
//        }catch(Exception e){
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mma");
//            dateTime = LocalDateTime.parse(str, formatter);
//        }

        int startCompare = localDateTime.toLocalTime().compareTo(this.getOpenHourTime());
        int endCompare = localDateTime.toLocalTime().compareTo(this.getCloseHourTime());

        if (!localDateTime.format(DateTimeFormatter.ofPattern("EEEE")).equals(getHariEnglish(this.getDay()))){
            return false;
        }else if(startCompare<0 || endCompare>0){
            return false;
        }
        return true;
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
