package Model;

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

}
