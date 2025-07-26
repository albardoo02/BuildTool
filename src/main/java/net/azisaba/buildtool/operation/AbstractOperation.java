package net.azisaba.buildtool.operation;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.acrylicstyle.storageBox.utils.StorageBox;

import java.util.Map;

public abstract class AbstractOperation implements Operation {

    protected final Player player;
    protected final BlockFace face;
    protected final Material material;
    protected final boolean useStorageBox;
    protected final Map.Entry<Integer, StorageBox> storageBoxEntry;
    protected Block startBlock;
    protected long amountToPlace;
    protected long availableAmount;

    public AbstractOperation(Block startBlock, BlockFace face, long desiredAmount, long availableAmount, Player player, boolean useStorageBox, Map.Entry<Integer, StorageBox> storageBoxEntry) {
        this.startBlock = startBlock;
        this.material = startBlock.getType();
        this.face = face;
        this.amountToPlace = desiredAmount;
        this.availableAmount = availableAmount;
        this.player = player;
        this.useStorageBox = useStorageBox;
        this.storageBoxEntry = storageBoxEntry;
    }

    @Override
    public void subtract() {
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (useStorageBox) {
            StorageBox storage = this.storageBoxEntry.getValue();
            if (storage != null && !storage.isEmpty() && storage.getComponentItemStack() != null && !storage.getComponentItemStack().hasItemMeta()) {
                storage.setAmount(storage.getAmount() - 1L);
                this.player.getInventory().setItem(this.storageBoxEntry.getKey(), storage.getItemStack());
                return;
            }
        }
        player.getInventory().removeItem(new ItemStack(this.material, 1));
    }

    @Override
    public abstract void place();
}