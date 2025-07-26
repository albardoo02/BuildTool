package net.azisaba.buildtool.operation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.storageBox.utils.StorageBox;

import java.util.Map;

public class LongLengthPlace extends AbstractOperation {

    public LongLengthPlace(Block startBlock, BlockFace face, long amountToPlace, Player player, boolean useStorageBox, Map.Entry<Integer, StorageBox> storageBoxEntry) {
        super(startBlock, face, amountToPlace, player, useStorageBox, storageBoxEntry);
    }

    @Override
    public void place() {
        Block currentBlock = this.startBlock;
        for (int i = 0; i < this.amountToPlace; i++) {
            if (!player.getInventory().contains(this.material)) {
                break;
            }

            Block targetBlock = currentBlock.getRelative(this.face);
            if (targetBlock.getType() != Material.AIR) {
                break;
            }

            targetBlock.setType(this.material, true);

            this.subtract();

            currentBlock = targetBlock;
        }
    }
}
