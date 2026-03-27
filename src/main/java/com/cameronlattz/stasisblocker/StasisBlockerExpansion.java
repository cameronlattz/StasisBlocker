package com.cameronlattz.stasisblocker;

import com.github.sirblobman.api.configuration.ConfigurationManager;
import com.github.sirblobman.api.utility.VersionUtility;
import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.Expansion;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.logging.Logger;

public class StasisBlockerExpansion extends Expansion {
    private final StasisBlockerConfiguration configuration;
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
        new StasisBlockerListener(this).register();
    }

    @Override
    public void onDisable() {}

    @Override
    public void reloadConfig() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.reload("config.yml");
        YamlConfiguration config = configurationManager.get("config.yml");
        this.configuration.load(config);
    }
}