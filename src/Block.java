import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
//Création de la classe block qui modélise les blocks d'une blockchain
public class Block{

    private int index;      //Index (numéro) du block
    private long timestamp;     //Enregistre une information sur la date de création
    private String hash;    //Assurer l'intégrité des données du block
    private String previousHash;        //Permet de lier le block courant au block pécédent
    private String data;        //Données
    private int nance;      //Nombre d'essaies réalisées avant de résoudre la preuve de travail


    //Constructeur de notre Block
    public Block(int index, long timestamp, String previousHash, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.data = data;
        nance = 0;
        hash = Block.calculateHash(this);
    }

    //tous nos getter nécessaires pour accèder aux données de notre Block en lecture
    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }


    //Méthode qui renvoie toutes les informations de notre block sous forme de chaines de caractères
    public String str(){
        return index+timestamp+previousHash+data+nance;
    }

    //Pour pouvoir visualiser plus facilement le contenu de notre block (lors des différents tests sur les blockchain)
    //Rendu visuel text de notre block
    public String toString(){
        StringBuilder builder= new StringBuilder();
            builder.append("Block#").append(index).append("[previousHash:").append(previousHash).append(",").append("timestamp:").append(new Date(timestamp)).append(",").append("data").append(data).append(",").append("hash:").append(hash).append("]");
            return builder.toString();
    }

    //Méthode static permettant de calculer le hash d'un block passé en entrée,
    //ce hash est calculé grâce à un algorithme de cryptographie SHA256 (disponible
    //en standard dans le SDK Java)
    public static String calculateHash(Block block) {

        if (block != null){
            MessageDigest digest=null;
            try{
                digest = MessageDigest.getInstance("SHA-256");
            }catch(NoSuchAlgorithmException e){
                return null;
            }
            String txt= block.str();


            //Application de la méthode digest pour récupérer le hash du block

            final byte bytes[]= digest.digest(txt.getBytes());
            final StringBuilder builder = new StringBuilder();

            for (final byte b:bytes){
                String hex= Integer.toHexString(0xff & b);
                if (hex.length()==1){
                builder.append('0');
                }
                builder.append(hex);
            }
            return builder.toString();
        }
        return null;
    }

    //Méthode pour attaquer le block (preuve de travail)
    public void mineBlock(int difficulty){
        nance = 0;      //Nombre d'essaies réalisées avant de résoudre la preuve de travail durant le processus de
                        //ménage pour le block courant. La difficulté déterminera le nombre de zéro que nous aurons
                        //au début du hash qu'on veut obtenir pour le block courant


        //Tantque le hash calculé est différent du hash attendu, nous continuons le processus de ménage.
        //Quand la preuve de travail est résolue, le block est miné (attaqué) et la valeur hash est correctement
        //citée au block courant
        while(!getHash().substring(0,difficulty).equals(Utils.zeros(difficulty))){
            nance++;
            hash = Block.calculateHash(this);
        }
    }
}