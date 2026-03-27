# StasisBlocker

A CombatLogX Expansion that prevents players from exploiting enderpearl stasis chambers to escape combat by blocking enderpearl teleports over a configurable distance and across dimensions while in combat.

---

## Features

- Blocks enderpearl teleports longer than a specified horizontal distance while combat-tagged.
- Takes nether dimension scaling into account for distance calculations.
- Configurable distance and messages via `config.yml`.
- Compatible with Minecraft 1.8 and above (requires CombatLogX with expansion support).

---

## Installation

1. Download `Stasis Blocker.jar` from the [target](https://github.com/cameronlattz/StasisBlocker/target) page.
2. Place the JAR file into your server’s `plugins/CombatLogX/expansions/` folder.
3. Start or reload your server.
4. Configure the plugin by editing `plugins/CombatLogX/expansions/StasisBlocker/config.yml` as needed.

---

## Configuration

The default `config.yml` includes:

```yaml
# Block enderpearl teleports longer than a specified distance while in combat. Default: true
prevent-distant-enderpearl: true

# How far a player can travel with an enderpearl while in combat before being blocked. Has no effect if prevent-distant-enderpearl is false.
# Nether coordinates will be converted to overworld coordinates for distance calculation.
# Default: 300 (blocks)
max-enderpearl-distance: 300

# Messages that will be sent to players after an enderpearl teleport is blocked by the stasis expansion.
messages:  # Use {distance} to include the teleport distance in the message.
  distance-blocked: "§cYou cannot teleport with an enderpearl more than {distance} blocks while in combat."