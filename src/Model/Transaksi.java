package Model;

import java.time.*;
import java.time.format.DateTimeFormatter;

import Helper.Translate;
import Helper.Translate.*;
import Helper.Validasi;

public class Transaksi {
    private int id;
    private Kendaraan kendaraan;
    private Customer user;
    private Area area;
    private Garage garage;
    private String startTime;
    private String endTime;
    private int duration;
    private double totalTransaction;
    private boolean isFirstinMonth;

    public Transaksi(){};

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

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setFirstinMonth(boolean bool){
        this.isFirstinMonth = bool;
    }

    public boolean getFirstInMonth(){
        return this.isFirstinMonth;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(double totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public int getMonthNumberFromStartTime(){
        LocalDateTime start = this.getStartLocalTime();
        String month = start.getMonth().toString().toLowerCase();
        if (month.equals("january")){
            return 1;
        }else if (month.equals("february")){
            return 2;
        }else if (month.equals("march")){
            return 3;
        }else if (month.equals("april")){
            return 4;
        }else if (month.equals("may")){
            return 5;
        }else if (month.equals("june")){
            return 6;
        }else if (month.equals("july")){
            return 7;
        }else if (month.equals("august")){
            return 8;
        }else if (month.equals("september")){
            return 9;
        }else if (month.equals("october")){
            return 10;
        }else if (month.equals("november")){
            return 11;
        }else if (month.equals("december")){
            return 12;
        }else{
            return 0;
        }
    }

    public String getNamaHariFromStartTime(){
        LocalDateTime start = this.getStartLocalTime();
        String day = start.getDayOfWeek().toString();

        return Translate.getIndoHari(day);
    }

    public int getYearFromStartTime(){
        LocalDateTime start = this.getStartLocalTime();
        int year = start.getYear();

        return year;
    }

    public LocalDateTime getStartLocalTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startHour = LocalDateTime.parse(this.getStartTime(), formatter);

        return startHour;
    }

    public LocalDateTime getEndLocalTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endHour = LocalDateTime.parse(this.getEndTime(), formatter);

        return endHour;
    }

    public void setCalculateDuration(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startHour = LocalDateTime.parse(this.getStartTime(), formatter);
        LocalDateTime endHour = LocalDateTime.parse(this.getEndTime(), formatter);
        long milliStart = startHour.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long milliEnd = endHour.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        int dur = (int) Math.ceil( (double)(milliEnd - milliStart) / 3600000);

        this.setDuration(dur);
    }

    public void setCalculateTotalPrice(){
        if (user.getSubscription().equals("Easy")||user.getSubscription().equals("easy")){
            double costSubs = 2000;
            double totalGaragePrice = this.getDuration() * this.kendaraan.getGaragePrice(this.garage);
            System.out.println("getduration: "+this.getDuration() );
            System.out.println("kendaraangarageprice: "+ this.kendaraan.getGaragePrice(this.garage) );
            this.setTotalTransaction(totalGaragePrice + costSubs);
        }else{
            double monthlySubs = 12000;
            double totalGaragePrice = this.getDuration()* this.kendaraan.getGaragePrice(this.garage);
            if (this.isFirstinMonth){
                System.out.println("debug: it is first month");
                 this.setTotalTransaction(totalGaragePrice + monthlySubs);
            }else{
                System.out.println("debug: it is not first month");
                this.setTotalTransaction(totalGaragePrice);
            }

        }
    }
}
