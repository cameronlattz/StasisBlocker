# StasisBlocker

A CombatLogX Expansion that prevents players from exploiting enderpearl stasis chambers to escape combat by blocking enderpearl teleports if the enderpearl is above a ticks_lived threshold.

---

## Features

- While combat-tagged, blocks teleports from enderpearls that have lived longer than a configurable amount of ticks.
- Configurable via `config.yml`.
- Compatible with Minecraft 1.8 and above (requires CombatLogX with expansion support).

---

## Installation

1. Download `Stasis Blocker.jar` from the [Releases](https://github.com/cameronlattz/StasisBlocker/releases) page.
2. Place the JAR file into your server’s `plugins/CombatLogX/expansions/` folder.
3. Start or reload your server.
4. Configure the plugin by editing `plugins/CombatLogX/expansions/StasisBlocker/config.yml` as needed.

---

## Configuration

The default `config.yml` includes:

```yaml
# Block enderpearls older than a certain age while in combat.
prevent-stasis: true

# Age of enderpearls that will be blocked while in combat, in ticks. 20 ticks = 1 second.
# Default: 300 (ticks)
stasis-enderpearl-age: 300

# Messages that will be sent to players after an enderpearl teleport is blocked.
messages:  # Use {ticks} to include the enderpearl age in ticks, or {seconds} to include the age in seconds.
  blocked: "§cYou cannot teleport with an enderpearl older than {seconds} seconds while in combat."
