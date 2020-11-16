package Helper;

import java.time.LocalDateTime;

public class Translate {

    public static String getIndoHari(String day){
        day = day.toLowerCase();
        if (day.equals("monday")){
            return "Senin";
        }else if (day.equals("tuesday")){
            return "Selasa";
        }else if (day.equals("wednesday")){
            return "Rabu";
        }else if (day.equals("thursday")){
            return "Kamis";
        }else if (day.equals("friday")){
            return "Jumat";
        }else if (day.equals("saturday")){
            return "Sabtu";
        }else if (day.equals("sunday")){
            return "Minggu";
        }else{
            return null;
        }
    }

    public static String getHariEnglish(String hari){
        hari = hari.toLowerCase();
        if (hari.equals("senin")){
            return "Monday";
        }else if (hari.equals("selasa")){
            return "Tuesday";
        }else if (hari.equals("rabu")){
            return "Wednesday";
        }else if (hari.equals("kamis")){
            return "Thursday";
        }else if (hari.equals("jumat")||hari.equals("jum'at")){
            return "Friday";
        }else if (hari.equals("sabtu")){
            return "Saturday";
        }else if (hari.equals("minggu")){
            return "Sunday";
        }else{
            return null;
        }
    }

}
