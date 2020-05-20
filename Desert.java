import java.util.Scanner;
import java.io.*;
public class Desert implements Meniu{
    public String chose(){
        System.out.println("\n  Clatite cu sirop de artar \uD83E\uDD5E \n Crema de zahar ars \uD83C\uDF6E \n Inghetata \uD83C\uDF68 \n Gogosi americane cu ciocolata \uD83C\uDF69");
        System.out.println("\n Tell me your chose (write exactly like in the meniu for the good aspect of the command)  \uD83D\uDE04 \n ");
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

        return nou;
    }
    public static void scrie(String s, File Comanda) throws IOException {


        FileWriter f = new FileWriter(Comanda, true);
        f.write(s);
        f.close();


    }
}
