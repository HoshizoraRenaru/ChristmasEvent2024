package org.hoshizora.christmasevent2024

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.plugin.java.JavaPlugin

class PurpleIceListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val item = event.itemInHand
        if (item.isSimilar(PurpleIceItemManager.createPurpleIce())) {
            event.isCancelled = true
        }
    }
}