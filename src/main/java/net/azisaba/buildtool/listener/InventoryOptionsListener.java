package net.azisaba.buildtool.listener;

import java.util.UUID;

import net.azisaba.buildtool.BuildTool;
import net.azisaba.buildtool.data.PlayerData;
import net.azisaba.buildtool.data.PlayerManager;
import net.azisaba.buildtool.operation.Operation;
import net.azisaba.buildtool.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class InventoryOptionsListener implements Listener, InventoryHolder {

    private final BuildTool plugin;
    private final PlayerManager playerManager;

    public InventoryOptionsListener(BuildTool plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof InventoryOptionsListener)) {
            return;
        }
        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || event.getClickedInventory() == null || !(event.getClickedInventory().getHolder() instanceof InventoryOptionsListener)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        int slot = event.getSlot();

        Operation.OperationType type = Util.getOperationTypeFromSlot(slot);
        if (type != null) {
            setAmount(clicked, event, type);
            data.setOperationType(type);
            data.setOperationAmount(clicked.getAmount());
            enchantItem(event.getInventory(), clicked);
        }

        data.setOperationAmount(clicked.getAmount());

        enchantItem(event.getInventory(), clicked);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof InventoryOptionsListener) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof InventoryOptionsListener) {
            playerManager.getPlayerData(event.getPlayer().getUniqueId()).setOptionsInventory(event.getInventory());
        }
    }

    public Inventory getNewOptionsInventory() {
        return Util.createOptionsInventory(this);
    }

    public Inventory getPlayerOptionsInventory(UUID uuid) {
        PlayerData data = playerManager.getPlayerData(uuid);
        if (data.getOptionsInventory() == null) {
            data.setOptionsInventory(getNewOptionsInventory());
        }
        return data.getOptionsInventory();
    }

    private void setAmount(ItemStack clicked, InventoryClickEvent e, Operation.OperationType type) {
        int edit = e.isRightClick() ? -1 : 1;
        if (e.isShiftClick()) {
            edit *= 5;
        }

        int currentAmount = clicked.getAmount();
        int newAmount = currentAmount + edit;

        if (type == Operation.OperationType.SQUARE_BLOCK_PLACE) {
            if (newAmount > 4) newAmount = 1;
            if (newAmount < 1) newAmount = 4;
        } else {
            if (newAmount > 64) newAmount = 1;
            if (newAmount < 1) newAmount = 64;
        }
        clicked.setAmount(newAmount);
    }

    private void enchantItem(Inventory inv, ItemStack item) {
        for (ItemStack stack : inv.getContents()) {
            if (stack != null) {
                stack.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
        }
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, 9, "Build Tool Options");
    }
}