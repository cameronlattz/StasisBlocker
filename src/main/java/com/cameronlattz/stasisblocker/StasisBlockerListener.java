package com.cameronlattz.stasisblocker;

import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.ExpansionListener;
import com.github.sirblobman.combatlogx.api.manager.ICombatManager;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StasisBlockerListener extends ExpansionListener {
    private final StasisBlockerConfiguration configuration;
    private final Map<UUID, Long> stasisCandidates = new ConcurrentHashMap<>();
    private final static int EXPIRY_TIME = 500;
    private static final String PERM_BYPASS = "stasisblocker.bypass";

    public StasisBlockerListener(StasisBlockerExpansion expansion) {
        super(expansion);
        this.configuration = expansion.getConfiguration();
    }

    @EventHandler
    public void onEnderpearlLand(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof EnderPearl)) return;

        EnderPearl enderpearl = (EnderPearl) event.getEntity();
        if (!(enderpearl.getShooter() instanceof Player)) return;

        boolean isStasisBlockingEnabled = configuration.isStasisBlockingEnabled();
        if (!isStasisBlockingEnabled) return;

        int age = enderpearl.getTicksLived();
        int stasisEnderpearlAge = configuration.getStasisEnderpearlAge();
        if (age < stasisEnderpearlAge) return;

        Player shooter = (Player)enderpearl.getShooter();
        ICombatLogX combatLogX = getCombatLogX();
        ICombatManager combatManager = combatLogX.getCombatManager();
        if (!combatManager.isInCombat(shooter)) return;

        if (shooter.hasPermission(PERM_BYPASS)) return;

        long now = System.currentTimeMillis();
        stasisCandidates.entrySet().removeIf(e -> e.getValue() + EXPIRY_TIME < now);
        UUID uuid = shooter.getUniqueId();
        stasisCandidates.put(uuid, System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;

        Player shooter = event.getPlayer();
        UUID uuid = shooter.getUniqueId();
        if (stasisCandidates.remove(uuid) != null) {
            event.setCancelled(true);
            double stasisEnderpearlAge = configuration.getStasisEnderpearlAge();
            String message = configuration.getBlockedMessage().replace("{age}", String.valueOf((int) Math.floor(stasisEnderpearlAge)));
            shooter.sendMessage(message);
        }
    }
}