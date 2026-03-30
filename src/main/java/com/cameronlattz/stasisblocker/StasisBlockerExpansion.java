package com.cameronlattz.stasisblocker;

import com.github.sirblobman.api.configuration.ConfigurationManager;
import com.github.sirblobman.api.utility.VersionUtility;
import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.Expansion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.logging.Logger;

public class StasisBlockerExpansion extends Expansion {
    private final StasisBlockerConfiguration configuration;
    private final Permission PERM_BYPASS = new Permission("stasisblocker.bypass", "Bypass stasis blocking", PermissionDefault.FALSE);
    public StasisBlockerExpansion(ICombatLogX plugin) {
        super(plugin);
        this.configuration = new StasisBlockerConfiguration();
    }

    public StasisBlockerConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void onLoad() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.saveDefault("config.yml");
    }

    @Override
    public void onEnable() {
        int minorVersion = VersionUtility.getMinorVersion();
        if (minorVersion < 8) {
            Logger logger = getLogger();
            logger.info("The stasis blocker expansion requires Spigot 1.8 or higher.");
            selfDisable();
            return;
        }

        reloadConfig();

        if (Bukkit.getPluginManager().getPermission(PERM_BYPASS.getName()) == null) {
            Bukkit.getPluginManager().addPermission(PERM_BYPASS);
        }
        new StasisBlockerListener(this).register();
    }

    @Override
    public void onDisable() {
        Permission p = Bukkit.getPluginManager().getPermission(PERM_BYPASS.getName());
        if (p != null) Bukkit.getPluginManager().removePermission(p);
    }

    @Override
    public void reloadConfig() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.reload("config.yml");
        YamlConfiguration config = configurationManager.get("config.yml");
        this.configuration.load(config);
    }
}