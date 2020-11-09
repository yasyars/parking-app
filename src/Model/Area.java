package Model;

public class Area {
    private int id;
    private String name;

    public Area(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.replaceAll("\\s", "").equals("")){
            throw new Exception("Nama area tidak boleh kosong");
        }else{
            this.name = name;
        }
    }
    public String toString() {
        return "ID: " + this.getId() +", "+this.getName();
    }


}
