package net.azisaba.buildtool.operation;

import net.azisaba.buildtool.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import xyz.acrylicstyle.storageBox.utils.StorageBox;

import java.util.List;
import java.util.Map;

public class SquareBlockPlace extends  AbstractOperation {

    public SquareBlockPlace(Block startBlock, BlockFace face, long desiredAmount, long availableAmount, Player player, boolean useStorageBox, Map.Entry<Integer, StorageBox> storageBoxEntry) {
        super(startBlock, face, desiredAmount, availableAmount, player, useStorageBox, storageBoxEntry);
    }

    @Override
    public void place() {
        int size = (int) this.amountToPlace;
        if (size <= 0) return;

        Block placementOrigin = startBlock.getRelative(face);
        List<BlockFace> axes = Util.getOrthogonalFaces(this.face);
        if (axes.size() < 2) return;

        BlockFace axis1 = axes.get(0);
        BlockFace axis2 = axes.get(1);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.availableAmount <= 0) return;

                Block targetBlock = placementOrigin.getRelative(axis1, i).getRelative(axis2, j);

                if (targetBlock.getType() == Material.AIR) {
                    BlockState beforeState = targetBlock.getState();
                    BlockPlaceEvent placeEvent = new BlockPlaceEvent(targetBlock, beforeState, startBlock, player.getInventory().getItemInMainHand(), player, true, EquipmentSlot.HAND);
                    Bukkit.getPluginManager().callEvent(placeEvent);

                    if (placeEvent.isCancelled()) {
                        continue;
                    }

                    targetBlock.setType(this.material, true);
                    subtract();
                    this.availableAmount--;
                }
            }
        }
    }
}
