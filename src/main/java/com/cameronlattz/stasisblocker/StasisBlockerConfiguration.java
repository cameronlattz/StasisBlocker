package com.cameronlattz.stasisblocker;

import com.github.sirblobman.api.configuration.IConfigurable;
import org.bukkit.configuration.ConfigurationSection;

public final class StasisBlockerConfiguration implements IConfigurable {
    private final boolean DEFAULT_STASIS_BLOCKING_ENABLED = true;
    private final int DEFAULT_STASIS_PEARL_AGE = 300;
    private final String DEFAULT_BLOCKED_MESSAGE = "§cYou cannot teleport with an enderpearl older than {age} ticks while in combat.";
    private boolean stasisBlockingEnabled;
    private int stasisEnderpearlAge;
    private String blockedMessage;

    public StasisBlockerConfiguration() {
        this.stasisBlockingEnabled = DEFAULT_STASIS_BLOCKING_ENABLED;
        this.stasisEnderpearlAge = DEFAULT_STASIS_PEARL_AGE;
        this.blockedMessage = DEFAULT_BLOCKED_MESSAGE;
    }

    @Override
    public void load(ConfigurationSection config) {
        setStasisBlockingEnabled(config.getBoolean("prevent-stasis", DEFAULT_STASIS_BLOCKING_ENABLED));
        setStasisEnderpearlAge(config.getInt("stasis-enderpearl-age", DEFAULT_STASIS_PEARL_AGE));
        ConfigurationSection messagesSection = config.getConfigurationSection("messages");
        if (messagesSection != null) {
            setBlockedMessage(messagesSection.getString("blocked", DEFAULT_BLOCKED_MESSAGE));
        }
    }

    public boolean isStasisBlockingEnabled() {
        return this.stasisBlockingEnabled;
    }

    public void setStasisBlockingEnabled(boolean isStasisBlockingEnabled) {
        this.stasisBlockingEnabled = isStasisBlockingEnabled;
    }

    public int getStasisEnderpearlAge() {
        return this.stasisEnderpearlAge;
    }

    public void setStasisEnderpearlAge(int stasisEnderpearlAge) {
        this.stasisEnderpearlAge = stasisEnderpearlAge;
    }

    public String getBlockedMessage() {
        return this.blockedMessage;
    }

    public void setBlockedMessage(String blockedMessage) {
        this.blockedMessage = blockedMessage;
    }
}