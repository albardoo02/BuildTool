package net.azisaba.buildtool.listener;

import net.azisaba.buildtool.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class OpenListener implements Listener {

    private final InventoryOptionsListener inventoryOptionsListener;

    public OpenListener(InventoryOptionsListener inventoryOptionsListener) {
        this.inventoryOptionsListener = inventoryOptionsListener;
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player p = event.getPlayer();
        if (p.isSneaking() && Util.isBuildItem(event.getItem())) {
            event.setCancelled(true);
            p.closeInventory();
            p.openInventory(inventoryOptionsListener.getPlayerOptionsInventory(p.getUniqueId()));
        }
    }
}
