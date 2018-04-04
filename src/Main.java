import javax.management.StringValueExp;

//Test de notre blockchain
public class Main {

    public static void main(String[] args){

        //On commence par instancier un objet blockchain
        //on fixe la difficulté à 4 pour le ménage des blocks
        Blockchain blockchain= new Blockchain(4);

        //Ajoutons quelques blocks
        blockchain.addBlock(blockchain.newBlock("tout sur le bitcoin"));
        blockchain.addBlock(blockchain.newBlock("Sylvain Saurel"));
        blockchain.addBlock(blockchain.newBlock("https://www.toutsurlebitcoin.fr"));

        //Vérifions si la blockchain est valide??
        System.out.println("Blockchain valide?" + blockchain.isBlockChainValid());

        //visualiser la blockchain
        System.out.println(blockchain);

        //A cestade en va essayer de corrompre notre blockchain en ajoutant un block avec un previousHash non cohérent
        //avec le block précédent et un index non cohérent
        //Cela permet de tester la validité de notre méthode isBlockchainValid() et pour cela on crée un block sans ménage
        blockchain.addBlock(new Block(15, System.currentTimeMillis(),"aaaabbb","Block invalid"));

        System.out.println("Blockchain valid?"+blockchain.isBlockChainValid());

        System.out.println(blockchain);
    }
}
