package Helper;

public class Validasi {
    public static boolean isEmpty(String s){
        if (s.replaceAll("\\s", "").equals("")){
            return true;
        }else {
            return false;
        }
    }

}
