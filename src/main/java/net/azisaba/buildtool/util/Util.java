package net.azisaba.buildtool.util;

import net.azisaba.buildtool.operation.Operation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class Util {

    public static Inventory createOptionsInventory(InventoryHolder holder) {
        Inventory inv = org.bukkit.Bukkit.createInventory(holder, 9, "Build Tool Options");
        inv.setItem(0, createOption(getColor("&aライン型設置"), Material.OAK_LOG));
        inv.setItem(2, createOption(getColor("&a正方形設置"), Material.STONE));
        return inv;
    }

    public static ItemStack createOption(String name, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static List<BlockFace> getOrthogonalFaces(BlockFace face) {
        List<BlockFace> axes = new ArrayList<>();

        switch (face) {
            case UP:
            case DOWN:
                axes.add(BlockFace.NORTH);
                axes.add(BlockFace.EAST);
                break;
            case NORTH:
            case SOUTH:
                axes.add(BlockFace.UP);
                axes.add(BlockFace.EAST);
                break;
            case EAST:
            case WEST:
                axes.add(BlockFace.UP);
                axes.add(BlockFace.NORTH);
                break;
            default:
                break;
        }
        return axes;
    }


    public static String getColor(String name) {
        return ChatColor.translateAlternateColorCodes('&', name);
    }

    public static Operation.OperationType getOperationTypeFromSlot(int slot) {
        switch (slot) {
            case 0: return Operation.OperationType.LONG_LENGTH_PLACE;
            case 2: return Operation.OperationType.SQUARE_BLOCK_PLACE;
            default: return null;
        }
    }

    public static boolean isBuildItem(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Constants.BUILD_TOOL_KEY, PersistentDataType.INTEGER);
    }
}