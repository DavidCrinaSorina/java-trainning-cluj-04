//Comentariile de aici sunt la fel si pentru clasele Desert, Bauturi, Salata, Garnitura si Carne
import java.util.Scanner;
import java.io.*;

public class Ciorbe implements Meniu{
    @Override
   public  String chose() {  //In urma parcurgerii lui MeniuFactory ,prin intermediul lui chose() din interfata Meniu s-a ajuns la parcurgerea  acestei clase
        System.out.println(" \n Ciorba Taraneasca \n Ciorba de burta \n Ciorba de Perisoare");
        System.out.println("\n Tell me your choise (please, write exactly like in the meniu for the good aspect of the command)  \uD83D\uDE04 \n ");
        System.out.println("If you want more, just put, for example 2x before your choice\n \uD83D\uDC47");
        Scanner s = new Scanner(System.in);
        String nou = s.nextLine();
         //Mai sus am construit meniul iar mai jos, cu ajutorul codului din try si a metodei scrie() scriem in fisierul deja creat, alegera pe care o face clientul din meniul din aceasta clasa prin intermediul lui -nou-, fara a interveni erori

        try {
            File f = new File("C:\\development\\workspace\\java-trainning-cluj-04\\_clean_code_projects\\_design_patterns\\_Proiect1\\src\\Comanda.txt");
            scrie("\n", f);
            scrie(nou, f);
        } catch (IOException e) {//prindem eroarea care apare cand vrem sa scriem ceva in fisier
            System.out.println("eroare");
        }
        return "";
    }
    public static void scrie(String s, File Comanda) throws IOException {

        FileWriter f = new FileWriter(Comanda, true);
        f.write(s);
        f.close();


    }

}

