package com.qubert.modelViewer2;

import com.mojang.brigadier.Message;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModelViewer2 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new Listener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
