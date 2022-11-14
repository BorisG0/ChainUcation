package DataObjects;

import java.util.ArrayList;

public class BlockChain {
    private ArrayList<Block> blocks;

    public BlockChain(){
        this.blocks = new ArrayList<>();
    }

    public void addBlock(Block block){
        if(blocks.size() > 0){
            block.setPrev(blocks.get(blocks.size() - 1).getHash());
        }else{
            block.setPrev("none");
        }
        blocks.add(block);
    }

    public String toString(){
        String s = "";

        for(Block b: blocks){
            s += b.toString() + "\n";
        }

        return s;
    }
}
