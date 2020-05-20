import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Salata implements Meniu{
    @Override
    public String chose(){
        System.out.println("\n Salata de rosii \uD83C\uDF45 \n Salata asortata ( castravete, ceapa, rosii ) \uD83E\uDD57");
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
