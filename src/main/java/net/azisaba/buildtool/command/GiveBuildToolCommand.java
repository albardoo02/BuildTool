package net.azisaba.buildtool.command;

import net.azisaba.buildtool.BuildTool;
import net.azisaba.buildtool.item.Build;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveBuildToolCommand implements CommandExecutor {

    private final BuildTool plugin;

    public GiveBuildToolCommand(BuildTool plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("コンソールからは実行できません");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("BuildTool.command.giveBuildTool")) {
            player.sendMessage(ChatColor.RED + "権限がありません");
            return true;
        }

        int modelData = plugin.getBuildToolModelData();
        player.getInventory().addItem((new Build()).build(Material.BLAZE_ROD, modelData));
        player.sendMessage(ChatColor.GREEN + "BuildToolを入手しました");

        return true;
    }
}
