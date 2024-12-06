package org.hoshizora.christmasevent2024

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin

class SophisticatedAmethystListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val item = event.itemInHand
        if (item.isSimilar(SophisticatedAmethystItemManager.createSophisticatedAmethyst())) {
            event.isCancelled = true
        }
    }
}