package net.azisaba.buildtool.listener;

import net.azisaba.buildtool.BuildTool;
import net.azisaba.buildtool.data.PlayerData;
import net.azisaba.buildtool.data.PlayerManager;
import net.azisaba.buildtool.operation.LongLengthPlace;
import net.azisaba.buildtool.operation.Operation;
import net.azisaba.buildtool.operation.SquareBlockPlace;
import net.azisaba.buildtool.util.Constants;
import net.azisaba.buildtool.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import xyz.acrylicstyle.storageBox.utils.StorageBox;
import xyz.acrylicstyle.storageBox.utils.StorageBoxUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BuildToolPlaceListener implements Listener {

    private final BuildTool plugin;
    private final PlayerManager playerManager;
    private final Set<UUID> cooldownSet = new HashSet<>();

    public BuildToolPlaceListener(BuildTool plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();
        if (cooldownSet.contains(player.getUniqueId()) || !Util.isBuildItem(event.getItem())) {
            return;
        }

        if (!plugin.isWorldEnabled(player.getWorld().getName())) {
            return;
        }

        Block clicked = event.getClickedBlock();
        if (clicked == null) {
            return;
        }

        Material targetMaterial = clicked.getType();
        Map.Entry<Integer, StorageBox> sbEntry = getStorageBox(player, targetMaterial);

        boolean useStorageBoxForItem = (sbEntry != null);

        long totalAmount;
        if (player.getGameMode() == GameMode.CREATIVE) {
            totalAmount = Integer.MAX_VALUE;
        } else {
            totalAmount = calculateTotalAmount(player, targetMaterial, sbEntry);
        }
        if (totalAmount == 0) {
            return;
        }

        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        long amountToPlace = data.getOperationAmount();
        Operation.OperationType type = data.getOperationType();
        BlockFace face = event.getBlockFace();

        Operation operation;
        if (type == Operation.OperationType.SQUARE_BLOCK_PLACE) {
            operation = new SquareBlockPlace(clicked, face, amountToPlace, totalAmount, player, useStorageBoxForItem, sbEntry);
        } else {
            operation = new LongLengthPlace(clicked, face, amountToPlace, totalAmount, player, useStorageBoxForItem, sbEntry);
        }
        operation.place();

        plugin.getLogger().info(player.getName() + "はBuildToolを使用した");
        setCoolDown(player);
    }

    private long calculateTotalAmount(Player player, Material material, Map.Entry<Integer, StorageBox> sbEntry) {
        long amount = 0;
        if (plugin.isStorageBoxEnabled() && sbEntry != null) {
            amount += sbEntry.getValue().getAmount();
        }
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item != null && item.getType() == material && !item.hasItemMeta()) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

    private Map.Entry<Integer, StorageBox> getStorageBox(Player p, Material material) {
        if (!plugin.isStorageBoxEnabled()) return null;
        return StorageBoxUtils.getStorageBoxForType(p.getInventory(), new ItemStack(material));
    }



    private void setCoolDown(Player p) {
        cooldownSet.add(p.getUniqueId());
        plugin.runAsyncDelayed(() -> cooldownSet.remove(p.getUniqueId()), Constants.PLACE_COOLDOWN_TICKS);
    }
}