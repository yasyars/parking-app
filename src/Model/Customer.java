package Model;

import java.util.List;

public class Customer extends User{
    private String subscription;

    public Customer(){
        super();
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public List<Kendaraan> getListOfKendaraan() {
        return listOfKendaraan;
    }

    public void setListOfKendaraan(List<Kendaraan> listOfKendaraan) {
        this.listOfKendaraan = listOfKendaraan;
    }

    private List<Kendaraan> listOfKendaraan;


}
