# Cancel Damage

Mostly made this to nerf fall damage mob farms on my SkyBlock server.

## Minecraft version
The following Minecraft versions are available in the release downloads:
- 1.15.2 (default, this is the version I use)
- 1.16.3

## Support
- None

## Issue Tracker
- https://github.com/FesterHead/CancelDamage/issues

## Discord
- None

## Configuration Assistance
- None

## Contributing To CancelDamage
- Fork the repository and issue a pull request.

Default configuration:
```
#
# This plugin cancels non-player damage.
# Use damage causes from bukkit:  https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html
# Case is insensitive.
#
# If a damage cause is not configured the defaults will apply.
#
# For each damage type:
#
#         enabled: Is this damage type enabled?
#                  False means a damage type is processed normally and skipped from this plugin.
#                  If false, the other parameters are not needed.
#                  Default is 'true'
#  percent-chance: Percent chance the damage will be cancelled.
#                  Default is "100".  Meaning damage will be cancelled 100% of the time.
#                  Misconfiguration will use the default value.
#                  Use "rnd" for a random chance from 0 to 100 determined on each damage event.
#  percent-damage: Percent of the damage to be cancelled, provided the damage is to be cancelled.
#                  Default is "100".  Meaning 100% of the dame will be cancelled.
#                  Misconfiguration will use the default value.
#                  Use "rnd" for a random percentage from 0 to 100 determined on each damage event.

damage-cause:
  default:
    enabled: false          # All damage causes not listed will process normally.
    percent-chance: "100"
    percent-damage: "100"
#  fall:
#    enabled: true           # Cancel all non-player fall damage
#    percent-chance: "100"
#    percent-damage: "100"
```

Example:
```
  fall:
    enabled: true           # 50% of the time, reduce fall damage by 25%
    percent-chance: "50"
    percent-damage: "25"
```

Example:
```
  fall:
    enabled: true           # Randomly reduce fall damage by 90%
    percent-chance: "rnd"
    percent-damage: "90"
```

Example:
```
  fall:
    enabled: true           # Randomly reduce fall damage by a random percent
    percent-chance: "rnd"
    percent-damage: "rnd"
```