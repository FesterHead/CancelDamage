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

## Default configuration:
```
# This plugin cancels non-player caused damage.
# Use damage causes from bukkit:  https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html
# Case is insensitive.
# If a damage cause is not configured the defaults will apply.
# For each damage type:
#         enabled: Is this damage type enabled?
#                  False means a damage type is processed normally and skipped from this plugin.
#                  If false, the other parameters are not needed.
#                  Default is 'true'
#  percent-chance: Percent chance the damage will be cancelled.
#                  Default is "100".  Meaning damage will be cancelled 100% of the time.
#                  Misconfiguration will use the default value.
#                  Use "rnd:min:max" for a random chance from min to max determined on each damage event. Use rnd:0:100 for 0 to 100
#  percent-cancel: Percent of the damage to cancel, provided the damage is not already cancelled.
#                  Default is "100".  Meaning 100% of the damage will be cancelled.
#                  Misconfiguration will use the default value.
#                  Use "rnd:min:max" for a random chance from min to max determined on each damage event. Use rnd:0:100 for 0 to 100
# log-level: 0 = only startup/reload messages, 1 = include enabled damage cause messages, 2 = include all damage cause messages and calculations
damage-cause:
  default:
    enabled: false
    percent-chance: 100
    percent-cancel: 100
  fall:
    enabled: false
    percent-chance: 50
    percent-cancel: rnd:80:100
log-level: 0
```

## What does cancel mean?
Let's say the damage to be applied to an entity is 100.  If the percent to cancel is 75%, then 25 is set for the damage.

`(100 - percent to cancel) / 100 * incoming damage = new damage`

Examples:
|incoming damage|percent to cancel|calculation|new damage|
|:---:|:---:|:---:|:---:|
|100|75|(100-75)/100*100|25|
|45|40|(100-40)/100*45|27|
|36|90|(100-90)/100*36|3.6|
|21|12|(100-12)/100*21|18.48|
