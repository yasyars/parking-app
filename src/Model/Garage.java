package Model;

public class Garage {
    private int id;
    private String name;
    private int area;
    private OperationalTime operationalTime;
    private double tarifMotor;
    private double tarifMobil;

    public Garage(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception{
        if (name.replaceAll("\\s", "").equals("")){
            throw new Exception("Nama garage tidak boleh kosong");
        }else {
            this.name = name;
        }
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;

    }


    public OperationalTime getOperationalTime() {
        return operationalTime;
    }

    public void setOperationalTime(OperationalTime operationalTime) throws Exception{
        if (operationalTime.getDay().replaceAll("\\s", "").equals("")){
            throw new Exception("Hari Operasional harus terisi !");
        }else if (operationalTime.getOpenHour().replaceAll("\\s", "").equals("")){
            throw new Exception("Jam buka operasional harus terisi!");
        }else if (operationalTime.getCloseHour().replaceAll("\\s", "").equals("")){
            throw new Exception("Jam tutup operasional harus terisi!");
        }else{
            this.operationalTime = operationalTime;
        }
    }

    public double getTarifMotor() {
        return tarifMotor;
    }

    public void setTarifMotor(double tarif_motor) {
        this.tarifMotor = tarif_motor;
    }

    public double getTarifMobil() {
        return tarifMobil;
    }

    public void setTarifMobil(double tarif_mobil) {
        this.tarifMobil = tarif_mobil;
    }

    public String toString() {
        return "ID: "+ this.getId() + ", " + this.getName();
    }

}
