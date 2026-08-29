package com.qubert.modelViewer2;

import com.mojang.brigadier.Message;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;

public final class ModelViewer2 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            getServer().getPluginManager().registerEvents(new Listener(this), this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
