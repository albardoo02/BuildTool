package net.azisaba.buildtool;

import net.azisaba.buildtool.command.GiveBuildToolCommand;
import net.azisaba.buildtool.data.PlayerManager;
import net.azisaba.buildtool.listener.BuildToolPlaceListener;
import net.azisaba.buildtool.listener.InventoryOptionsListener;
import net.azisaba.buildtool.listener.OpenListener;
import net.azisaba.buildtool.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class BuildTool extends JavaPlugin {

    private PlayerManager playerManager;
    private Set<String> enabledWorlds;
    private boolean storageBoxEnabled = false;

    private int buildToolModelData;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.loadConfigData();

        this.playerManager = new PlayerManager();

        this.checkDependencies();
        this.registerListeners();

        this.getCommand("giveBuildTool").setExecutor(new GiveBuildToolCommand(this));
    }

    public void loadConfigData() {
        this.enabledWorlds = new HashSet<>(this.getConfig().getStringList("enableWorlds"));
        this.buildToolModelData = this.getConfig().getInt("model", 15);
    }

    private void checkDependencies() {
        Plugin storageBoxPlugin = this.getServer().getPluginManager().getPlugin("StorageBox");
        if (storageBoxPlugin != null && storageBoxPlugin.isEnabled()) {
            storageBoxEnabled = true;
        }
    }

    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();

        InventoryOptionsListener optionsListener = new InventoryOptionsListener(this, playerManager);

        manager.registerEvents(optionsListener, this);
        manager.registerEvents(new BuildToolPlaceListener(this, playerManager), this);
        manager.registerEvents(new OpenListener(optionsListener), this);
        manager.registerEvents(new PlayerQuitListener(playerManager), this);
    }

    public boolean isStorageBoxEnabled() {
        return storageBoxEnabled;
    }

    public boolean isWorldEnabled(String worldName) {
        return enabledWorlds.contains(worldName);
    }

    public void runAsyncDelayed(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, runnable, delay);
    }

    public int getBuildToolModelData() {
        return buildToolModelData;
    }
}
