import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class YanChain {

    private static Logger log = LoggerFactory.getLogger(YanChain.class);

    //Difficulty 4-7 recommended for testing. Current LiteCoin difficulty is around 6.4 million, Bitcoin 110 million
    public static final int DIFFICULTY = 6;
    public static ArrayList<Block> blockChain = new ArrayList<>();


    public static void main(String[] args) {

        blockChain.add(new Block("0", "And so it begins..."));
        log.info("Mining block #1...");
        blockChain.get(0).mineBlock(DIFFICULTY);

        blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getHash(), "Block #2"));
        log.info("Mining block #2...");
        blockChain.get(1).mineBlock(DIFFICULTY);

        blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getHash(), "Block #3"));
        log.info("Mining block #3...");
        blockChain.get(2).mineBlock(DIFFICULTY);

        blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getHash(), "Block #4"));
        log.info("Mining block #4...");
        blockChain.get(3).mineBlock(DIFFICULTY);

        if (isChainValid()) {
            log.info("The block chain is valid.");
        } else {
            log.error("Block chain compromised!");
        }

        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        log.info("The current block chain: ");
        log.info(blockChainJson);
    }

    public static boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[DIFFICULTY]).replace('\0', '0');

        for (int i = 1; i < blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                log.error("Current hashes not equal");
                return false;
            }
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                log.error("Previous hashes not equal");
                return false;
            }
            if (!currentBlock.getHash().substring(0, DIFFICULTY).equals(hashTarget)) {
                log.warn("This block hasn't been minded");
                return false;
            }
        }
        return true;
    }
}
