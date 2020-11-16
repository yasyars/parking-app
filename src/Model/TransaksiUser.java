package Model;

public class TransaksiUser {
    private int id;
    private Kendaraan kendaraan;
    private Customer user;
    private Area area;
    private Garage garage;
    private String startTime;
    private String endTime;
    private String duration;
    private double totalTransaction;
    private User owner;

    public TransaksiUser(){};

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(double totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
