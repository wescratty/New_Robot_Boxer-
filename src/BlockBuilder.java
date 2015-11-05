import java.util.ArrayList;

/**
 * Created by Brian Trethewey on 11/4/15.
 */

/**
 * Class to create blocks and container that holds them.
 * using an arraylist for consistancy with Attack Structure
 */
public class BlockBuilder {

private ArrayList<Block> blockList;

    /**
     * Function to create ArrayList and Block
     * @param unusedPoints Integer: number of points left for character creation/ modification not used currently
     *                     but here for future extensibility.
     * @return An arrayList of Block Objects: Used in the creation of A boxer Object
     */
    public ArrayList<Block>buildBlocks(int unusedPoints){
        //create blockList
        blockList = new ArrayList<Block>();
        Block block = new SimpleBlock();
        blockList.add(block);
        return blockList;
    }
}
