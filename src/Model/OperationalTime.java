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
