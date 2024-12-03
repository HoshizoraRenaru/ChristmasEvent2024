package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class DarkDeerBowListener(private val plugin: JavaPlugin) : Listener {

    private val playerLastFireTime = mutableMapOf<Player, Long>()

    private fun isDarkDeerBow(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false
        val meta = item.itemMeta ?: return false
        return meta.displayName == DarkDeerBowItemManager.createDarkDeerBow().itemMeta?.displayName &&
                meta.lore == DarkDeerBowItemManager.createDarkDeerBow().itemMeta?.lore &&
                item.type == Material.BOW
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (isDarkDeerBow(item) && event.action.name.contains("LEFT_CLICK")) {
            val currentTime = System.currentTimeMillis()

            if (playerLastFireTime[player] == null || currentTime - playerLastFireTime[player]!! >= 500) {
                playerLastFireTime[player] = currentTime

                if (player.totalExperience >= 5) {
                    player.giveExp(-5)
                    object : BukkitRunnable() {
                        override fun run() {
                            fireArrow(player)
                        }
                    }.runTaskLater(plugin, 2L)
                } else {
                    player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
                }
            }
        }
    }

    private fun fireArrow(player: Player) {
        val arrows = player.inventory.contents.filter { it != null && it.type == Material.ARROW }

        if (arrows.isNotEmpty()) {
            val arrow = player.launchProjectile(AbstractArrow::class.java)
            arrow.velocity = player.location.direction.multiply(3.0)
            arrow.damage = 8.0

            arrow.setMetadata("DarkDeerBowArrow", FixedMetadataValue(plugin, true))

            startParticleEffect(arrow)

            player.inventory.removeItem(org.bukkit.inventory.ItemStack(Material.ARROW, 1))
            player.playSound(player.location, Sound.BLOCK_AMETHYST_BLOCK_STEP, 2F, 0.1F)
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

    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        if (projectile is AbstractArrow && projectile.shooter is Player && projectile.hasMetadata("DarkDeerBowArrow")) {
            val hitEntity = event.hitEntity
            if (hitEntity is LivingEntity) {
                val shooter = projectile.shooter as Player
                playHitSound(shooter)
            }
        }
    }

    private fun playHitSound(player: Player) {
        player.playSound(player.location, Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 0.8f)
    }
}
