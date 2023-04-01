package azuazu3939.buildtool;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static azuazu3939.buildtool.BuildInventory.setBackFrame;
import static azuazu3939.buildtool.BuildInventory.setFrameInv;
import static azuazu3939.buildtool.BuildItem.*;

public class InventoryListener implements Listener {

    public static Map<Player, Inventory> inv = new HashMap<>();
    private static int get1 = 16;
    private static int get2 = 25;
    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {

        if (event.getInventory().getHolder() instanceof BuildInventory &&
                (event.getWhoClicked().getWorld().getName().equalsIgnoreCase("world") || event.getWhoClicked().getWorld().getName().equalsIgnoreCase("art"))) {

            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.OAK_SIGN)) {

                ItemStack item = inv.get((Player) event.getWhoClicked()).getItem(10);
                if (item == null) return;
                int i = new ItemUtils().getAdminItem(item);

                if (i == 1) {
                    new ItemUtils().setAdminItem(item, 2);
                    setLore(item, 2, event.getWhoClicked());
                } else if (i == 2) {
                    new ItemUtils().setAdminItem(item, 1);
                    setLore(item, 1, event.getWhoClicked());
                }
            } else if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.OAK_LOG)) {

                ItemStack item = inv.get((Player) event.getWhoClicked()).getItem(12);
                if (item == null) return;
                if (item.getAmount() == 16) item.setAmount(1);
                if (item.getAmount() < 16) item.setAmount(item.getAmount() + 1);
                get1 = item.getAmount();
                event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);

            } else if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.SPRUCE_LOG)) {

                ItemStack item = inv.get((Player) event.getWhoClicked()).getItem(14);
                if (item == null) return;
                if (item.getAmount() == 25) item.setAmount(1);
                if (item.getAmount() < 25) item.setAmount(item.getAmount() + 1);
                get2 = item.getAmount();
                event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
            }
        }
    }

    public void setLore(@NotNull ItemStack item, int i, HumanEntity player) {

        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) Objects.requireNonNull(meta.getLore()).clear();
        item.setItemMeta(meta);
        List<String> list = new ArrayList<>();
        if (i == 1) list.add(0, ChatColor.WHITE + "平面型設置にする");
        if (i == 2) list.add(0, ChatColor.WHITE + "ライン型設置にする");
        meta.setLore(list);
        item.setItemMeta(meta);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
    }

    @EventHandler
    public void onDrag(@NotNull InventoryDragEvent event) {

        if (event.getInventory().getHolder() instanceof BuildInventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreate(@NotNull PlayerJoinEvent event) {

        create(event.getPlayer());
    }

    public void create(Player player) {

        Inventory getInv = Bukkit.createInventory(new BuildInventory(), 54, ChatColor.translateAlternateColorCodes('&', "&f個別編集"));

        setBackFrame(getInv);
        setFrameInv(getInv);

        getInv.setItem(10, getItemGenre1());
        getInv.setItem(12, getLine());
        getInv.setItem(14, getSqu());

        inv.put(player, getInv);
    }


    public static int getLineAmount() {return get1;}
    public static int getSquAmount() {return get2;}
}
