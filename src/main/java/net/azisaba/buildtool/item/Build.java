package net.azisaba.buildtool.item;

import net.azisaba.buildtool.util.Constants;
import net.azisaba.buildtool.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class Build {

    private static final List<String> LORE = Arrays.asList(
            Util.getColor("&fプレイヤー用建築補助ツール"),
            Util.getColor("&f手持ちのブロックを消費して、ブロック設置します。"),
            Util.getColor("&fシフト左で設定を開きます。")
    );

    public ItemStack build(Material material, int modelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(Constants.BUILD_TOOL_KEY, PersistentDataType.INTEGER, 1);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setCustomModelData(modelData);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lBuild&f&l-&b&lTool"));
        meta.setLore(LORE);
        item.setItemMeta(meta);

        return item;
    }
}
