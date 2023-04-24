package com.rcallum.calcore.Files;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigHandler {

    public String filename;
    public File configFile;
    private FileConfiguration config;
    private final JavaPlugin plugin;

    public ConfigHandler(String filename, JavaPlugin plugin){
        this.plugin = plugin;
        this.filename = filename;
        makeConfig();
    }

    public void makeConfig(){
        configFile = new File(this.plugin.getDataFolder(),filename);

        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
            plugin.saveResource(filename,false);
        }

        config = new YamlConfiguration();
        try{
            config.load(configFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        configFile = new File(this.plugin.getDataFolder(),filename);
        config = new YamlConfiguration();
        try{
            config.load(configFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(){
        return this.config;
    }

    public void saveConfig(){
        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defIMessagesStream = this.plugin.getResource(filename);
        if (defIMessagesStream != null) {
            config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defIMessagesStream, Charsets.UTF_8)));
        }
    }
    public void saveData(){
        try{
            config.save(configFile);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
