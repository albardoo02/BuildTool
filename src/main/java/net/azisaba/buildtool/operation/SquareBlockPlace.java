package net.azisaba.buildtool.operation;

import net.azisaba.buildtool.util.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.storageBox.utils.StorageBox;

import java.util.List;
import java.util.Map;

public class SquareBlockPlace extends  AbstractOperation {

    public SquareBlockPlace(Block startBlock, BlockFace face, long amountToPlace, Player player, boolean useStorageBox, Map.Entry<Integer, StorageBox> storageBoxEntry) {
        super(startBlock, face, amountToPlace, player, useStorageBox, storageBoxEntry);
    }

    @Override
    public void place() {
        int size = (int) this.amountToPlace;
        if (size <= 0) return;

        Block centerBlock = startBlock.getRelative(face);

        List<BlockFace> axes = Util.getOrthogonalFaces(this.face);
        if (axes.size() < 2) return;

        BlockFace axis1 = axes.get(0);
        BlockFace axis2 = axes.get(1);

        for (int x = -(size - 1); x <= (size - 1); x++) {
            for (int z = -(size - 1); z <= (size - 1); z++) {
                if (!player.getInventory().contains(this.material)) {
                    return;
                }

                Block relative = centerBlock.getRelative(axis1, x).getRelative(axis2, z);

                if (relative.getType() == Material.AIR) {
                    relative.setType(this.material, true);
                    subtract();
                }
            }
        }
    }
}
