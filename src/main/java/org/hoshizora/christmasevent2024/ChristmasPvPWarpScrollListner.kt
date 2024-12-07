package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class ChristmasPvPWarpScrollListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlayerUseScroll(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) && item != null) {
            val scrollItem = ChristmasPvPWarpScrollItemManager.createScroll()

            if (item.isSimilar(scrollItem)) {
                event.isCancelled = true

                if (item.amount > 1) {
                    item.amount = item.amount - 1
                } else {
                    player.inventory.removeItem(item)
                }

                Bukkit.getServer().dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "mvtp ${player.name} christmas_pvp"
                )

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l워프완료."))
                player.world.playSound(player.location, Sound.ENTITY_WITHER_DEATH, 2.0f, 0.5f)
            }
        }
    }
}