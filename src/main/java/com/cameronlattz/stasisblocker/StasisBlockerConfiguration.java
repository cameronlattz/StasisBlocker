package com.cameronlattz.stasisblocker;

import com.github.sirblobman.api.configuration.IConfigurable;
import org.bukkit.configuration.ConfigurationSection;

public final class StasisBlockerConfiguration implements IConfigurable {
    private final boolean DEFAULT_DISTANCE_BLOCKING_ENABLED = true;
    private final int DEFAULT_MAX_TELEPORT_DISTANCE = 300;
    private final String DEFAULT_DISTANCE_BLOCKED_MESSAGE = "§cYou cannot teleport more than {distance} blocks away while in combat.";
    private boolean distanceBlockingEnabled;
    private int maxTeleportDistance;
    private String distanceBlockedMessage;

    public StasisBlockerConfiguration() {
        this.distanceBlockingEnabled = DEFAULT_DISTANCE_BLOCKING_ENABLED;
        this.maxTeleportDistance = DEFAULT_MAX_TELEPORT_DISTANCE;
        this.distanceBlockedMessage = DEFAULT_DISTANCE_BLOCKED_MESSAGE;
    }

    @Override
    public void load(ConfigurationSection config) {
        setDistanceBlockingEnabled(config.getBoolean("prevent-distant-enderpearl", DEFAULT_DISTANCE_BLOCKING_ENABLED));
        setMaxTeleportDistance(config.getInt("max-enderpearl-distance", DEFAULT_MAX_TELEPORT_DISTANCE));
        ConfigurationSection messagesSection = config.getConfigurationSection("messages");
        if (messagesSection != null) {
            setDistanceBlockedMessage(messagesSection.getString("distance-blocked", DEFAULT_DISTANCE_BLOCKED_MESSAGE));
        }
    }

    public boolean isDistanceBlockingEnabled() {
        return this.distanceBlockingEnabled;
    }

    public void setDistanceBlockingEnabled(boolean isStasisBlockingEnabled) {
        this.distanceBlockingEnabled = isStasisBlockingEnabled;
    }

    public int getMaxTeleportDistance() {
        return this.maxTeleportDistance;
    }

    public void setMaxTeleportDistance(int maxTeleportDistance) {
        this.maxTeleportDistance = maxTeleportDistance;
    }

    public String getDistanceBlockedMessage() {
        return this.distanceBlockedMessage;
    }

    public void setDistanceBlockedMessage(String distanceBlockedMessage) {
        this.distanceBlockedMessage = distanceBlockedMessage;
    }
}