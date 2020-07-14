//am introdul jocul cu calculatorul
import java.util.*;
import javax.swing.*;
public class ABChess{
    static String chessBoard[][]={
            {"r","k","b","q","a","b","k","r"},//piesele negre: p=pawn; r=Rook; k=Knight; q=Quen; a=King; b=Bishop
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","K","B","Q","A","B","K","R"}};//piesele albe: P=pawn; R=Rook; K=Knight; Q=Quen; A=King; B=Bishop
    static int positionWhiteKing, positionBlackKing;
    static int humanIsWhite=-1;//1=omul este alb, 0=omul este negru => pentru a vedea cu ce piese joaca omul
    static int globalDepth=4;
    public static void main(String[] args) {
        while (!"A".equals(chessBoard[positionWhiteKing/8][positionWhiteKing%8])) {positionWhiteKing++;}//da locatia regelui
        while (!"a".equals(chessBoard[positionBlackKing/8][positionBlackKing%8])) {positionBlackKing++;}//da locatia regelui

        JFrame f=new JFrame("Chess Game");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TheInterface ui=new TheInterface();
        f.add(ui);
        f.setSize(495, 515);
        f.setVisible(true);
        System.out.println(sortMoves(posibleMoves()));
        //pentru meniul de inceput
       Object[] option={"Computer","Human"};
       humanIsWhite=JOptionPane.showOptionDialog(null, "Who is white?", "Start Menu", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        if (humanIsWhite==0) {
            long startTime=System.currentTimeMillis();
            makeMove( alphaBeta(globalDepth, 1000000, -1000000, "", 0));
            long endTime=System.currentTimeMillis();
            System.out.println("Takes "+(endTime-startTime)+" milliseconds");
            flipBoard();
            f.repaint();
        }
        makeMove("7655 ");
        undoMove("7655 ");
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }
    public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {

        String list=posibleMoves();
        if (depth==0 || list.length()==0) {return move+(Rating.rating(list.length(), depth)*(player*2-1));}
        list=sortMoves(list);
        player=1-player;//either 1 or 0
        for (int i=0;i<list.length();i+=5) {
            makeMove(list.substring(i,i+5));
            flipBoard();
            String returnString=alphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            flipBoard();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value<=beta) {beta=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            } else {
                if (value>alpha) {alpha=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            }
            if (alpha>=beta) {
                if (player==0) {return move+beta;} else {return move+alpha;}
            }
        }
        if (player==0) {return move+beta;} else {return move+alpha;}
    }
    public static void flipBoard() {
        String temp;
        for (int i=0;i<32;i++) {
            int r=i/8, c=i%8;
            if (Character.isUpperCase(chessBoard[r][c].charAt(0))) {
                temp=chessBoard[r][c].toLowerCase();
            } else {
                temp=chessBoard[r][c].toUpperCase();
            }
            if (Character.isUpperCase(chessBoard[7-r][7-c].charAt(0))) {
                chessBoard[r][c]=chessBoard[7-r][7-c].toLowerCase();
            } else {
                chessBoard[r][c]=chessBoard[7-r][7-c].toUpperCase();
            }
            chessBoard[7-r][7-c]=temp;
        }
        int kingTemp=positionWhiteKing;
        positionWhiteKing=63-positionBlackKing;
        positionBlackKing=63-kingTemp;
    }
    public static void makeMove(String move) {
        if (move.charAt(4)!='P') {//miscare obisnuita
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];//destinatia ia valoarea pionului pe care vrem sa il mutam
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";//locul unde era pionul se elibereaza
            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
                positionWhiteKing=8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
            }
        } else {
            //daca pionul promoveaza(oricare ar fi el pawn, bishop etc la fel si sus
            chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";//eliberam locatia curenta a pionului
            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));//locatia noua a pionului
        }
    }
    public static void undoMove(String move) {
        if (move.charAt(4)!='P') {
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
                positionWhiteKing=8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
            }
        } else {
            //daca pawn promoveaza
            chessBoard[1][Character.getNumericValue(move.charAt(0))]="P";
            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
        }
    }
    public static String posibleMoves() { //mergem prin fiecare piesa sa vedem miscarile posibile, iar la final o sa avem o lista cu ele
        String list="";
        for (int i=0; i<64; i++) {
            switch (chessBoard[i/8][i%8]) {
                case "P": list+=posibleP(i);
                    break;
                case "R": list+=posibleR(i);
                    break;
                case "K": list+=posibleK(i);
                    break;
                case "B": list+=posibleB(i);
                    break;
                case "Q": list+=posibleQ(i);
                    break;
                case "A": list+=posibleA(i);
                    break;
            }
        }
        return list;
    }
    //posibile miscari ale lui pawn
    public static String posibleP(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//prinde piesa
                if (Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i>=16) {//cand avem piesa rivala si pawn ii de la pozitia 16 in sus
                    oldPiece=chessBoard[r-1][c+j];//inlocuim vechea piesa de pe locul unde vrem sa mutam pawn
                    chessBoard[r][c]=" ";//eliberam de pe tabla locul lui pawn
                    chessBoard[r-1][c+j]="P";//punem pawn pe noua locatie a lui pawn
                    if (kingSafe()) {
                        list=list+r+c+(r-1)+(c+j)+oldPiece;
                    }
                    chessBoard[r][c]="P";
                    chessBoard[r-1][c+j]=oldPiece;
                }
            } catch (Exception e) {}
            try {//promotie&capturare
                if (Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i<16) {
                    String[] temp={"Q","R","B","K"};
                    for (int k=0; k<4; k++) {
                        oldPiece=chessBoard[r-1][c+j];
                        chessBoard[r][c]=" ";
                        chessBoard[r-1][c+j]=temp[k];
                        if (kingSafe()) {
                            //coloana1,coloana2,piesa capturata,noua piesa,P
                            list=list+c+(c+j)+oldPiece+temp[k]+"P";
                        }
                        chessBoard[r][c]="P";
                        chessBoard[r-1][c+j]=oldPiece;
                    }
                }
            } catch (Exception e) {}
        }
        try {//pentru mutare in sus cu o casuta
            if (" ".equals(chessBoard[r-1][c]) && i>=16) {
                oldPiece=chessBoard[r-1][c];
                chessBoard[r][c]=" ";
                chessBoard[r-1][c]="P";
                if (kingSafe()) {
                    list=list+r+c+(r-1)+c+oldPiece;
                }
                chessBoard[r][c]="P";
                chessBoard[r-1][c]=oldPiece;
            }
        } catch (Exception e) {}
        try {
            if (" ".equals(chessBoard[r-1][c]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=chessBoard[r-1][c];
                    chessBoard[r][c]=" ";
                    chessBoard[r-1][c]=temp[k];
                    if (kingSafe()) {
                        //column1,column2,captured-piece,new-piece,P
                        list=list+c+c+oldPiece+temp[k]+"P";
                    }
                    chessBoard[r][c]="P";
                    chessBoard[r-1][c]=oldPiece;
                }
            }
        } catch (Exception e) {}
        try {//pentru mutarea la doua casute in sus
            if (" ".equals(chessBoard[r-1][c]) && " ".equals(chessBoard[r-2][c]) && i>=48) {
                oldPiece=chessBoard[r-2][c];
                chessBoard[r][c]=" ";
                chessBoard[r-2][c]="P";
                if (kingSafe()) {
                    list=list+r+c+(r-2)+c+oldPiece;
                }
                chessBoard[r][c]="P";
                chessBoard[r-2][c]=oldPiece;
            }
        } catch (Exception e) {}
        return list;
    }
    //posibile miscari ale lui rook
    public static String posibleR(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            try {
                while(" ".equals(chessBoard[r][c+temp*j]))//pentru verticala
                {
                    oldPiece=chessBoard[r][c+temp*j];
                    chessBoard[r][c]=" ";//eliminam rook de la locatia curenta
                    chessBoard[r][c+temp*j]="R";//punem pe noua locatia rook
                    if (kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r][c+temp*j]=oldPiece;
                    temp++;
                }
                //cand putem lua o piesa a inamicului de pe locul nde mutam rook
                if (Character.isLowerCase(chessBoard[r][c+temp*j].charAt(0))) {
                    oldPiece=chessBoard[r][c+temp*j];
                    chessBoard[r][c]=" ";
                    chessBoard[r][c+temp*j]="R";
                    if (kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r][c+temp*j]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(chessBoard[r+temp*j][c]))//pentru orizontala
                {
                    oldPiece=chessBoard[r+temp*j][c];
                    chessBoard[r][c]=" ";
                    chessBoard[r+temp*j][c]="R";
                    if (kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r+temp*j][c]=oldPiece;
                    temp++;
                }
                //cand putem lua o piesa a inamicului de pe locul nde mutam rook
                if (Character.isLowerCase(chessBoard[r+temp*j][c].charAt(0))) {
                    oldPiece=chessBoard[r+temp*j][c];
                    chessBoard[r][c]=" ";
                    chessBoard[r+temp*j][c]="R";
                    if (kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r+temp*j][c]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
        }
        return list;
    }
    //posibile miscari ale lui knight
    public static String posibleK(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isLowerCase(chessBoard[r+j][c+k*2].charAt(0)) || " ".equals(chessBoard[r+j][c+k*2])) {//verificam daca unde vrem sa mutam ii o piesa rivala
                        oldPiece=chessBoard[r+j][c+k*2];//atunci inlocuim piesa cu a noastra cu knight
                        chessBoard[r][c]=" ";//eliberam locatia unde a fost knight
                        if (kingSafe()) {
                            list=list+r+c+(r+j)+(c+k*2)+oldPiece;
                        }
                        chessBoard[r][c]="K";
                        chessBoard[r+j][c+k*2]=oldPiece;
                    }
                } catch (Exception e) {}
                try {//la felca si mai sus, doar ca pentru alte miscari ale lui knight
                    if (Character.isLowerCase(chessBoard[r+j*2][c+k].charAt(0)) || " ".equals(chessBoard[r+j*2][c+k])) {
                        oldPiece=chessBoard[r+j*2][c+k];
                        chessBoard[r][c]=" ";
                        if (kingSafe()) {
                            list=list+r+c+(r+j*2)+(c+k)+oldPiece;
                        }
                        chessBoard[r][c]="K";
                        chessBoard[r+j*2][c+k]=oldPiece;
                    }
                } catch (Exception e) {}
            }
        }
        return list;
    }
    //posibile miscari ale lui bishop (aproape identic cu quen)
    public static String posibleB(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    while(" ".equals(chessBoard[r+temp*j][c+temp*k]))
                    {
                        oldPiece=chessBoard[r+temp*j][c+temp*k];
                        chessBoard[r][c]=" ";
                        chessBoard[r+temp*j][c+temp*k]="B";
                        if (kingSafe()) {
                            list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                        }
                        chessBoard[r][c]="B";
                        chessBoard[r+temp*j][c+temp*k]=oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
                        oldPiece=chessBoard[r+temp*j][c+temp*k];
                        chessBoard[r][c]=" ";
                        chessBoard[r+temp*j][c+temp*k]="B";
                        if (kingSafe()) {
                            list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                        }
                        chessBoard[r][c]="B";
                        chessBoard[r+temp*j][c+temp*k]=oldPiece;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        return list;
    }
    //posibile miscari ale lui queen
    public static String posibleQ(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {
                    try {//se face pana cand ii posibila o miscare, altfel prinde eroarea
                        while(" ".equals(chessBoard[r+temp*j][c+temp*k]))//cat timp locul unde vfrem sa mutam regina ii gol
                        {
                            oldPiece=chessBoard[r+temp*j][c+temp*k];//locul unde merge regina
                            chessBoard[r][c]=" ";//golim locul unde era regina
                            chessBoard[r+temp*j][c+temp*k]="Q";
                            if (kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }

                            chessBoard[r][c]="Q";
                            chessBoard[r+temp*j][c+temp*k]=oldPiece;
                            temp++;//cu ajutorul lui temp mergem cu regina pana unde ii posibil de mers
                        }
                        if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
                            oldPiece=chessBoard[r+temp*j][c+temp*k];
                            chessBoard[r][c]=" ";
                            chessBoard[r+temp*j][c+temp*k]="Q";
                            if (kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }
                            chessBoard[r][c]="Q";
                            chessBoard[r+temp*j][c+temp*k]=oldPiece;
                        }
                    } catch (Exception e) {}
                    temp=1;
                }
            }
        }
        return list;
    }
    //posibile miscari ale lui king
    public static String posibleA(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        //regele poate face 8 mmiscari in total in jurul lui cu un pas: in fata, in spate, pe diagonala, in dreapta, in stanga
        for (int j=0;j<9;j++) {
            if (j!=4) {
                try {//pentru a evita erorile date de miscarile ilegale, de ex, unde avem pionii lui sau unde se termina tabla de sah
                    if (Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) {
                        oldPiece=chessBoard[r-1+j/3][c-1+j%3]; // pentru a memora daca a fost sau nu o piesa a oponentului unde se va muta regele
                        chessBoard[r][c]=" ";//locatia regelui o golim
                        chessBoard[r-1+j/3][c-1+j%3]="A";//noua locatie a regelui
                        int kingTemp=positionWhiteKing;
                        positionWhiteKing=i+(j/3)*8+j%3-9;
                        if (kingSafe()) { //verificam daca este ok miscarea regelui astfel incat sa nu avem sah mat
                            list=list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
                        }
                        chessBoard[r][c]="A";
                        chessBoard[r-1+j/3][c-1+j%3]=oldPiece;
                        positionWhiteKing=kingTemp;
                    }
                } catch (Exception e) {}
            }
        }

        return list;
    }
    public static String sortMoves(String list) {
        int[] score=new int [list.length()/5];
        for (int i=0;i<list.length();i+=5) {
            makeMove(list.substring(i, i+5));
            score[i/5]=-Rating.rating(-1, 0);
            undoMove(list.substring(i, i+5));
        }
        String newListA="", newListB=list;
        for (int i=0;i<Math.min(6, list.length()/5);i++) {//first few moves only
            int max=-1000000, maxLocation=0;
            for (int j=0;j<list.length()/5;j++) {
                if (score[j]>max) {max=score[j]; maxLocation=j;}
            }
            score[maxLocation]=-1000000;
            newListA+=list.substring(maxLocation*5,maxLocation*5+5);
            newListB=newListB.replace(list.substring(maxLocation*5,maxLocation*5+5), "");
        }
        return newListA+newListB;
    }
    public static boolean kingSafe() {
        //bishop/queen
        int temp=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {//cat timp pe diagonala ii spatiu gol pana sa dea de bishop/queen
                    while(" ".equals(chessBoard[positionWhiteKing/8+temp*i][positionWhiteKing%8+temp*j])) {temp++;}
                    if ("b".equals(chessBoard[positionWhiteKing/8+temp*i][positionWhiteKing%8+temp*j]) ||
                            "q".equals(chessBoard[positionWhiteKing/8+temp*i][positionWhiteKing%8+temp*j])) {
                        return false;//king ii in pericol pt ca avem pe pozitie bishop/queen
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        //rook/queen
        for (int i=-1; i<=1; i+=2) {
            try {//pentru miscarile pe orizontala
                while(" ".equals(chessBoard[positionWhiteKing/8][positionWhiteKing%8+temp*i])) {temp++;}
                if ("r".equals(chessBoard[positionWhiteKing/8][positionWhiteKing%8+temp*i]) ||
                        "q".equals(chessBoard[positionWhiteKing/8][positionWhiteKing%8+temp*i])) {
                    return false;//returneaza fals cand regele ii in pericol
                }
            } catch (Exception e) {}
            temp=1;
            try {//pentru miscarile pe verticala
                while(" ".equals(chessBoard[positionWhiteKing/8+temp*i][positionWhiteKing%8])) {temp++;}
                if ("r".equals(chessBoard[positionWhiteKing/8+temp*i][positionWhiteKing%8]) ||
                        "q".equals(chessBoard[positionWhiteKing/8+temp*i][positionWhiteKing%8])) {
                    return false;//returneaza fals cand regele ii in pericol
                }
            } catch (Exception e) {}
            temp=1;
        }
        //knight
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("k".equals(chessBoard[positionWhiteKing/8+i][positionWhiteKing%8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("k".equals(chessBoard[positionWhiteKing/8+i*2][positionWhiteKing%8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        //pawn => aici ii cand king ajunge inafara celor doua randuri alocate initial pionilor lui si ii amenintat de pawn
        if (positionWhiteKing>=16) {
            try {
                if ("p".equals(chessBoard[positionWhiteKing/8-1][positionWhiteKing%8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ("p".equals(chessBoard[positionWhiteKing/8-1][positionWhiteKing%8+1])) {
                    return false;
                }
            } catch (Exception e) {}
            //king
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("a".equals(chessBoard[positionWhiteKing/8+i][positionWhiteKing%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }
}