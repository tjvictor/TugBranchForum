package tugbranch.forum;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        /*File file = new File("D:\\迅雷下载\\Download\\");
        for(File item : file.listFiles()){

            if(item.getName().length()<8){
                String[] newName = item.getName().split("\\.");
                if("ts".equals(newName[1])) {
                    String str = String.format("%04d", Integer.parseInt(newName[0]));
                    File newFile = new File("D:\\迅雷下载\\Download\\"+str+".ts");
                    item.renameTo(newFile);
                }
            }
        }*/
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            Date date1 = sdf.parse("2018-04-13 13:28:36");

            System.out.println(date.getTime());
            System.out.println(date1.getTime());
            //System.out.println((date-date1).getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
