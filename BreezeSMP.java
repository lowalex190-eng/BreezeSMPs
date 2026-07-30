package com.breezesmp.plugin;

import com.breezesmp.plugin.commands.BreezeSMPCommand;
import com.breezesmp.plugin.gui.WeaponsGui;
import com.breezesmp.plugin.items.CooldownManager;
import com.breezesmp.plugin.items.CustomItems;
import com.breezesmp.plugin.items.RecipeRegistrar;
import com.breezesmp.plugin.listeners.WeaponAbilityListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BreezeSMP extends JavaPlugin {

    private CustomItems customItems;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        customItems = new CustomItems(this);
        cooldownManager = new CooldownManager();

        RecipeRegistrar recipeRegistrar = new RecipeRegistrar(this, customItems);
        recipeRegistrar.registerRecipes();

        WeaponsGui weaponsGui = new WeaponsGui(this, customItems);
        getServer().getPluginManager().registerEvents(weaponsGui, this);

        WeaponAbilityListener abilityListener = new WeaponAbilityListener(this, customItems, cooldownManager);
        getServer().getPluginManager().registerEvents(abilityListener, this);

        BreezeSMPCommand command = new BreezeSMPCommand(this, customItems, weaponsGui);
        var pluginCommand = getCommand("breezesmp");
        if (pluginCommand != null) {
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        } else {
            getLogger().warning("Command 'breezesmp' not found in plugin.yml!");
        }

        getLogger().info("BreezeSMP has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BreezeSMP has been disabled.");
    }

    public CustomItems getCustomItems() {
        return customItems;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
