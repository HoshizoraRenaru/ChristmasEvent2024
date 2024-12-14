package org.hoshizora.christmasevent2024

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

class ChristmasCoinDropListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity

        if (entity.killer == null) return

        if (Random.nextDouble() < 0.05) {
            val christmasCoin: ItemStack = ChristmasCoinItemManager.createCoin()
            entity.world.dropItemNaturally(entity.location, christmasCoin)
        }
    }
}
