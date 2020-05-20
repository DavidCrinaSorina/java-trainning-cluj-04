//Implementarea design pattern-ului Factory
//Clasa facuta pentru alegerea caii pe care se va merge
public class MeniuFactory {


        public static Meniu getMeniu(String meniuType) {
            if (meniuType == null) {
                return null;
            }
            if (meniuType.equals("1")) {
                return new Ciorbe();
            }
            if (meniuType.equals("2")) {
                return new Garnitura();
            }
            if (meniuType.equals("3")) {
                return new Carne();
            }
            if (meniuType.equals("4")) {
                return new Salata();
            }
            if (meniuType.equals("5")) {
                return new Desert();
            }
            if (meniuType.equals("6")) {
                return new Bauturi();
            }
            return null;
        }

}
