package com.cameronlattz.stasisblocker;

import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.ExpansionListener;
import com.github.sirblobman.combatlogx.api.manager.ICombatManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class StasisBlockerListener extends ExpansionListener {
    private final StasisBlockerConfiguration configuration;

    public StasisBlockerListener(StasisBlockerExpansion expansion) {
        super(expansion);
        this.configuration = expansion.getConfiguration();
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }

        Player player = event.getPlayer();
        ICombatLogX combatLogX = getCombatLogX();
        ICombatManager combatManager = combatLogX.getCombatManager();
        if (!combatManager.isInCombat(player)) {
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();
        if (to == null) {
            return;
        }

        boolean isDistanceBlockingEnabled = configuration.isDistanceBlockingEnabled();
        if (!isDistanceBlockingEnabled) {
            return;
        }

        double toX = to.getX();
        double toZ = to.getZ();
        if (to.getWorld().getEnvironment() == World.Environment.NETHER && from.getWorld().getEnvironment() != World.Environment.NETHER) {
            toX = toX * 8;
            toZ = toZ * 8;
        }
        else if (from.getWorld().getEnvironment() == World.Environment.NETHER && to.getWorld().getEnvironment() != World.Environment.NETHER) {
            toX = toX / 8;
            toZ = toZ / 8;
        }
        double deltaX = toX - from.getX();
        double deltaZ = toZ - from.getZ();
        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double maxDistance = configuration.getMaxTeleportDistance();
        if (horizontalDistance > maxDistance) {
            event.setCancelled(true);
            String message = configuration.getDistanceBlockedMessage().replace("{distance}", String.valueOf((int) Math.floor(maxDistance)));
            player.sendMessage(message);
        }
    }
}