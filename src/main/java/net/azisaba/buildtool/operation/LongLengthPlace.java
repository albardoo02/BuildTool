package net.azisaba.buildtool.operation;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import xyz.acrylicstyle.storageBox.utils.StorageBox;

import java.util.Map;

public class LongLengthPlace extends AbstractOperation {

    public LongLengthPlace(Block startBlock, BlockFace face, long desiredAmount, long availableAmount, Player player, boolean useStorageBox, Map.Entry<Integer, StorageBox> storageBoxEntry) {
        super(startBlock, face, desiredAmount, availableAmount, player, useStorageBox, storageBoxEntry);
    }

    @Override
    public void place() {
        Block currentBlock = this.startBlock;
        long placeLimit = Math.min(this.amountToPlace, this.availableAmount);

        for (int i = 0; i < placeLimit; i++) {
            Block targetBlock = currentBlock.getRelative(this.face);
            if (targetBlock.getType() != Material.AIR) {
                break;
            }

            BlockState beforeState = targetBlock.getState();
            BlockPlaceEvent placeEvent = new BlockPlaceEvent(targetBlock, beforeState, currentBlock, player.getInventory().getItemInMainHand(), player, true, EquipmentSlot.HAND);
            Bukkit.getPluginManager().callEvent(placeEvent);

            if (placeEvent.isCancelled()) {
                break;
            }

            targetBlock.setType(this.material, true);
            this.subtract();
            currentBlock = targetBlock;
        }
    }
}
