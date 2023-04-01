package azuazu3939.buildtool;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPlayer;

public class BuildToolCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("BuildTool.command.BuildTool")) {
                    player.sendMessage(ChatColor.RED + "権限がありません。");
                } else {
                    get(player, args);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤー実行専用コマンドです。");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "エラーが発生しました。");
            return true;
        }
    }

    public void get(CommandSender sender, String[] strings) {

        String playerName = strings[0];
        Player p = getPlayer(playerName);
        if (p == null) {
            sender.sendMessage(ChatColor.RED + "そのプレイヤーはオフラインです。");
            return;
        }

        p.getInventory().addItem(new BuildItem().getItemStack());
        p.sendMessage(ChatColor.GREEN + "Builder Toolを手に入れました。");
    }
}
