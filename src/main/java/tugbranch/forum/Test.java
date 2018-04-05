package tugbranch.forum;


import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("D:\\迅雷下载\\Download\\");
        for(File item : file.listFiles()){

            if(item.getName().length()<8){
                String[] newName = item.getName().split("\\.");
                if("ts".equals(newName[1])) {
                    String str = String.format("%04d", Integer.parseInt(newName[0]));
                    File newFile = new File("D:\\迅雷下载\\Download\\"+str+".ts");
                    item.renameTo(newFile);
                }
            }
        }
    }
}
