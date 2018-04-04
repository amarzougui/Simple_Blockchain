import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private int difficulty;         //Difficulté pour processus de ménage
    private List<Block> blocks;     // permet de stocker au sein d'une liste java standard les blocks de
                                    // notre chaine de blocks

    //Constructeur
    public Blockchain(int difficulty) {
        this.difficulty = difficulty;
        blocks = new ArrayList<>();

        Block b = new Block(0,System.currentTimeMillis(),null,"First Block");

        b.mineBlock(difficulty);

        blocks.add(b);
    }

    public int getDifficulty() {
        return difficulty;
    }

    //Méthode permettant de renvoyer le dernier block de notre blochchain
    public Block latestBlock(){
        return blocks.get(blocks.size() - 1);
    }

    //Méthode permettant de renvoyer un nouveau bloc avec un index incrémenté de 1 par rapport au précédent dernier
    //block de notre chaine et dont le previous hash sera celui du block précédent
    public Block newBlock(String data){
        Block latestBlock = latestBlock();
        return new Block(latestBlock.getIndex()+1, System.currentTimeMillis(), latestBlock.getHash(),data);
    }

    //Méthode permettant d'ajouter un nouveau block à notre blockchain mais juste avant ca il est nécessaire de miner
    //le block en résolvant la preuve de travail
    public void addBlock(Block b){
        if (b!=null){
            b.mineBlock(difficulty);
            blocks.add(b);
        }

    }

    //Méthode permettant de vérifier si la première méthode de notre blockchain est bien valide
    //Ce block (le Genesis block: premier block du blockchain) est valide si son index est égale à 0
    //et que son previousHash est null et que le hash courant est cohérent
    public boolean isFirstBlockValid(){
        Block firstBlock = blocks.get(0);
        if (firstBlock.getIndex()!=0){
            return false;
        }

        if (firstBlock.getPreviousHash()!=null){
            return false;
        }

        if (firstBlock.getHash()==null || !Block.calculateHash(firstBlock).equals(firstBlock.getHash())){
            return false;
        }

        return true;
    }

    //Méthode permettant de valider si notre premier block est valide comparé au block précédent:
    // - Vérification de l'index de block bien égale à sa valeu - 1 sur le précédent block
    // - Vérification de la cohérence du hash et du previousHash
    // - Vérification de la cohérence du hash de block courant
    public boolean isValidNewBlock(Block newBlock, Block previousBlock) {

        if (newBlock != null && previousBlock != null) {

            if (previousBlock.getIndex() + 1 != newBlock.getIndex()) {
                return false;
            }

            if (newBlock.getPreviousHash() == null || !newBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            if (newBlock.getHash() == null || !Block.calculateHash(newBlock).equals(newBlock.getHash())){
                return false;
            }

            return true;
        }

        return false;
    }

    //Méthode permettant de vérifier la validité de notre blockchain et donc de garantir l'intégrité des données
    // qu'elle contient, pour cela, tous les neouds doivent être valides => vérifier tous les blocks
    public boolean isBlockChainValid() {
        //vérification du premier block
        if (!isFirstBlockValid()) {
            return false;
        }

        //vérification des autres blocks
        for (int i=1; i<blocks.size();  i++ ){
            Block currentBlock = blocks.get(i);
            Block previousBlock = blocks.get(i - 1);

            if(!isValidNewBlock(currentBlock, previousBlock)){
                return false;
            }
        }
        return true;
    }

    //Surcharge de la méthode toString de notre blockchain pour pouvoir afficher le contenu de notre blockchain
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Block block:blocks){

            builder.append(block).append("\n");
        }
        return builder.toString();
    }
}
