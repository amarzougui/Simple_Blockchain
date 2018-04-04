public class Utils {

    //Cette méthode static retournera en sortie une chaine de caractères comportant autant de zéro
    // que le nombre passé en entrée de la méthode
    public static String zeros(int length){

        StringBuilder builder= new StringBuilder();
        for (int i=0; i<length;  i++ ){
            builder.append('0');
        }
        return builder.toString();
    }
}
