//Acest program reprezinta un meniu vitrual pentru un restaurant
//Pentru implementarea lui am folosit Iterator si Factory ca design pattern-uri

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int da_nu = 0;// Am folosit aceasta variabila pentru a verifica daca se poate sau nu incheia comanda clientului

        System.out.println("\uD83C\uDF7D  Welcome to our restaurant! \uD83E\uDD17 \n");
        out.println("Principal menu: \uD83D\uDC47 \n");

        //Am folosit un Iterator pentru a parcurge si afisa meniul principal
        String[] i = new String[]{"1:Ciorba  🍲 ", "2:Garnitura  \uD83E\uDD61", "3:Carne  \uD83C\uDF57", "4:Salata  \uD83E\uDD57", "5:Desert  \uD83C\uDF70", "6:Bauturi  \uD83C\uDF77"};
        Iterator in = new Iterator(i);
        while (in.hasnext()) {
            System.out.println(in.next());
        }
        System.out.println("\n Please chose what do you want from the list by writing the number down here: \n \uD83D\uDC47");
        //Bucla pentru comanda, astfel incat clientul sa isi poata lua cat doreste din fiecare
        while (da_nu == 0) {
            Scanner s = new Scanner(System.in); //Se citeste de la tastatura ce se alege din meniul principal
            String name = s.nextLine();         // Si se pune intr-un string pentru a il putea compara cu valorile din MeniuFactory
            Meniu s1 = MeniuFactory.getMeniu(name);  // Creez variabila s1 de tipul Meniu, care impreuna cu chose() din interfata Meniu si cu MeniuFactory vor face trimitere la urmatoarele clase, Desert,Ciorbe si celelalte care au aceeasi functie ca ele
            s1.chose();                             //De ex. daca scriem 1, s1.chose() va face sa se parcurga clasa Ciorbe

            System.out.println("Anyting else?    0 for NO / other number for YES \n  \uD83D\uDC47");//Aceasta structura de cod, este folosita pentru a vedea daca clientul mai doreste ceva
            Scanner c = new Scanner(System.in);                                          //Astfel, daca va scrie 0, comanda lui se va termina, iar daca va scrie orice altceva, va continua sa isi comande
            Integer alegere = Integer.parseInt(c.nextLine());
            if (alegere==0) {
                da_nu = 1;
                String donee = "✔";
                System.out.println(donee);
            }else
                out.println("Please, move up to see the principal menu, then please write here the number for what do you want. \uD83D\uDC47");
        }
           System.out.println("Your comand is: \n");//Cu ajutorul comenzilor din try, citim din fisierul unde se inregistreaza comanda si o afisam atunci cand este incheiata
           try {
              FileReader f = new FileReader("C:\\development\\workspace\\java-trainning-cluj-04\\_clean_code_projects\\_design_patterns\\_Proiect1\\src\\Comanda.txt");
              BufferedReader ff = new BufferedReader(f);
              String str;
              while((str=ff.readLine())!=null){
                out.println(str);
              }
           }//Pentru a intercepta exceptia(IOexception) care va aparea la try si pentru a functiona astfel ok programul, fara sa se opreasca inainte de momentul in care trebuie
          catch(IOException e){
            System.out.println("Fisierul nu a fost gasit");
          }
            System.out.println("\n Thank you! \uD83E\uDD17 \n Your comand will be procesed in few minutes.");

    }

}
