package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parkir {
    private int id;
    private Kendaraan kendaraan;
    private User user;
    private Area area;
    private Garage garage;
    private String startTime;

    public Parkir(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(Kendaraan kendaraan) {
        this.kendaraan = kendaraan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {

        this.startTime = startTime;
    }

    public LocalDateTime getStartLocalTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startHour = LocalDateTime.parse(this.getStartTime(), formatter);

        return startHour;
    }

    public int compareStopParkingTime(LocalDateTime dt){

        int cmpDateTime = dt.toLocalTime().compareTo(this.getStartLocalTime().toLocalTime());
        int cmpDate = dt.toLocalDate().compareTo(this.getStartLocalTime().toLocalDate());

        if (cmpDate==0 && cmpDateTime> 0){
            return 1;
        }else if (cmpDate==0 && cmpDateTime==0){
            return 0;
        }else{
            return -1;
        }

    }
}
