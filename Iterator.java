//Implementare iterator pentru creerea meniului principal
public class Iterator {
    private  String[] i;
    private int j;
    public Iterator(String[] i) {
        this.i = i;  // elementul i al meniului se initializeaza de la
        this.j = 0; // pozitia 0,1,2...5 in cazul acestui proiect;
    }
    public boolean hasnext() {
        boolean a=this.j<this.i.length;

        return a;
    }
    public String next() {

        return this.i[this.j++];
    }
}
