package com.festerhead.listener;

import java.util.Objects;
import java.util.Random;
import com.festerhead.CancelDamage;
import com.festerhead.object.ConfigCause;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
  private CancelDamage plugin;

  public EntityDamage(CancelDamage plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onEntityDamage(EntityDamageEvent event) {
    Entity entity = event.getEntity();

    if (Objects.isNull(entity) || (entity instanceof Player)) {
      return;
    }

    ConfigCause cause = null;

    // Check the damage cause is configured or use the default
    if (plugin.getCauses().containsKey(event.getCause())) {
      cause = plugin.getCauses().get(event.getCause());
    } else {
      cause = plugin.getDefaultCause();
    }

    // Check if the damage cause is enabled for cancelling
    if (Objects.nonNull(cause) && cause.isEnabled()) {
      // Is this a complete cancel?
      if (cause.getPercentChance() >= new Random().nextInt(100)) {
        event.setCancelled(true);
      } else {
        // Compute and apply damage reduction
        double newDamage = event.getDamage() - (event.getDamage() * (cause.getPercentDamage() / 100.0));
        event.setDamage(((newDamage < 0.0 || newDamage > event.getDamage() ? event.getDamage() : newDamage)));
      }
    }
  }
}
