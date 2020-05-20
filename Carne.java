import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.Scanner;
public class Carne implements Meniu {
    @Override
    public String chose(){
        System.out.println("\n Gratar de porc \uD83E\uDD69 \n Gratar din piept de pui \uD83E\uDD69 \n Pulpa de rata rotisata \uD83C\uDF57 \n Pulpa de pui la cuptor \uD83C\uDF57 \n Hotdog \uD83C\uDF2D \n Burito  \uD83C\uDF2F");
        System.out.println("\n Tell me your chose (please write exactly like in the meniu for the good aspect of the command)  \uD83D\uDE04 \n  ");
        System.out.println("If you want more, just put, for example 2x before your choice \n \uD83D\uDC47");
        Scanner s = new Scanner(System.in);
        String nou = s.nextLine();
        try {
            File f = new File("C:\\development\\workspace\\java-trainning-cluj-04\\_clean_code_projects\\_design_patterns\\_Proiect1\\src\\Comanda.txt");
            scrie("\n", f);
            scrie(nou, f);
        } catch (IOException e) {
            System.out.println("da");
        }
      return "";
    }
    public static void scrie(String s, File Comanda) throws IOException {


        FileWriter f = new FileWriter(Comanda, true);
        f.write(s);
        f.close();


    }
}
