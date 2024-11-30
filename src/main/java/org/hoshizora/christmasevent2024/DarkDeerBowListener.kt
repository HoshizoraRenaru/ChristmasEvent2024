package org.hoshizora.christmasevent2024

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class DarkDeerBowListener(private val plugin: JavaPlugin) : Listener {

    private val playerLastFireTime = mutableMapOf<Player, Long>()
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.type == Material.BOW && event.action.name.contains("LEFT_CLICK")) {
            val currentTime = System.currentTimeMillis()

            if (playerLastFireTime[player] == null || currentTime - playerLastFireTime[player]!! >= 500) {
                playerLastFireTime[player] = currentTime

                fireArrow(player)
            }
        }
    }

    private fun fireArrow(player: Player) {
        val arrows = player.inventory.contents.filter { it != null && it.type == Material.ARROW }

        if (arrows.isNotEmpty()) {
            val arrow = player.launchProjectile(AbstractArrow::class.java)
            arrow.velocity = player.location.direction.multiply(3.0)
            startParticleEffect(arrow)

            player.inventory.removeItem(org.bukkit.inventory.ItemStack(Material.ARROW, 1))
        }
    }

    private fun startParticleEffect(arrow: AbstractArrow) {
        object : BukkitRunnable() {
            override fun run() {
                if (arrow.isDead || arrow.location == null) {
                    cancel()
                    return
                }
                arrow.world.spawnParticle(
                    Particle.WAX_OFF,
                    arrow.location,
                    2,
                    0.05, 0.05, 0.05, 0.1
                )
                val color = Color.fromRGB(25, 1, 88)
                val dustOptions = Particle.DustOptions(color, 1.0f)
                arrow.world.spawnParticle(
                    Particle.REDSTONE,
                    arrow.location,
                    1,
                    0.05, 0.05, 0.05, 0.1,
                    dustOptions
                )
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }
}
