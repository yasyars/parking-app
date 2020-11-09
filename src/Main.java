import Helper.DbConnection;
import ViewController.*;

public class Main {


    public static void main (String[] args)  {
        DbConnection.getConnection();

        Init initForm = new Init();
        initForm.setVisible(true);
    }
}
