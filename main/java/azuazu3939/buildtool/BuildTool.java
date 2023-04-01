package azuazu3939.buildtool;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BuildTool extends JavaPlugin {

    private static BuildTool tool;
    public BuildTool() {tool = this;}
    public static BuildTool get() {return tool;}
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveConfig();
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("BuildTool")).setExecutor(new BuildToolCommand());
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new AcitonListener(), this);
        pm.registerEvents(new InventoryListener(), this);

        for (Player player: Bukkit.getOnlinePlayers()) {
            new InventoryListener().create(player);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
