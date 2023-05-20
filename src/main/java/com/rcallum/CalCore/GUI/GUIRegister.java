package com.rcallum.CalCore.GUI;

import org.bukkit.plugin.java.JavaPlugin;

public class GUIRegister {

    private JavaPlugin plugin;

    public GUIRegister(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(
                new CGUIListener(this), plugin
        );
    }

    public CGUI createGUI(String name, int rowsPerPage) {
        return new CGUI(plugin, name, rowsPerPage);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
