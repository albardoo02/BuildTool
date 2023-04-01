package azuazu3939.buildtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BuildItem {

    public ItemStack getItemStack() {

        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lBuild&f&l-&b&lTool"));

        List<String> list = new ArrayList<>();
        int i = 0;
        for (String s: BuildTool.get().getConfig().getStringList("BuildItemLore")) {
            list.add(i, ChatColor.translateAlternateColorCodes('&', s));
            i++;
        }
        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getFrame() {

        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f"));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getBack() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f"));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);

        return item;

    }

    public static @NotNull ItemStack getItemGenre1() {

        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&l建築モード"));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        List<String> list = new ArrayList<>();
        list.add(0, ChatColor.WHITE + "ライン状設置にする");

        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getLine() {

        ItemStack item = new ItemStack(Material.OAK_LOG, 16);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "ライン型設置の設置数");
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item_line"), PersistentDataType.STRING, "true");
        List<String> list = new ArrayList<>();
        list.add(0, ChatColor.WHITE + "クリックで編集");

        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getSqu() {

        ItemStack item = new ItemStack(Material.SPRUCE_LOG, 25);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "平面型設置の設置数");
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item_line"), PersistentDataType.STRING, "true");
        List<String> list = new ArrayList<>();
        list.add(0, ChatColor.WHITE + "クリックで編集");

        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }
}
