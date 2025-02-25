package org.hoshizora.christmasevent2024

import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import net.md_5.bungee.api.ChatColor
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class SantasTradeSecretListener(private val plugin: JavaPlugin) : Listener {
    private val cooldowns = mutableMapOf<UUID, Long>()

    private fun isSantasTradeSecrets(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false
        val meta = item.itemMeta ?: return false
        return meta.displayName == SantasTradeSecretItemManager.createSantasTradeSecrets().itemMeta?.displayName &&
                meta.lore == SantasTradeSecretItemManager.createSantasTradeSecrets().itemMeta?.lore &&
                item.type == SantasTradeSecretItemManager.createSantasTradeSecrets().type
    }

    init {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach { player ->
                    if (isSantasTradeSecrets(player.inventory.itemInMainHand)) {
                        spawnParticles(player)
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 5L)
    }

    private fun spawnParticles(player: Player) {
        val location = player.location.add(0.0, 1.0, 0.0)

        player.world.spawnParticle(Particle.WAX_OFF, location, 1, 0.5, 0.5, 0.5, 0.0)

        val color = Color.fromRGB(25, 1, 88)
        val dustOptions = Particle.DustOptions(color, 1.0f)
        player.world.spawnParticle(Particle.REDSTONE, location, 7, 0.5, 0.5, 0.5, dustOptions)
    }

    private fun dash(player: Player) {
        player.giveExp(-20)
        player.velocity = player.location.direction.multiply(2).setY(0.0)
        player.world.playSound(player.location, Sound.BLOCK_AMETHYST_BLOCK_BREAK, 2.0f, 0.1f)
        player.world.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_FLAP, 2.0f, 0.1f)

        val location = player.location.add(0.0, 1.0, 0.0)
        player.world.spawnParticle(Particle.WAX_OFF, location, 60, 1.0, 1.0, 1.0, 0.5)
        val color = Color.fromRGB(25, 1, 88)
        val dustOptions = Particle.DustOptions(color, 1.0f)
        player.world.spawnParticle(Particle.REDSTONE, location, 100, 1.0, 1.0, 1.0, 0.5, dustOptions)

        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 60, 1))
    }


    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (event.hand != EquipmentSlot.HAND) {
            return
        }

        if (isSantasTradeSecrets(item)) {
            if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
                event.isCancelled = true
            }

            val playerId = player.uniqueId
            val currentTime = System.currentTimeMillis()

            if (cooldowns.containsKey(playerId)) {
                val timeLeft = ((cooldowns[playerId]!! + 10000) - currentTime) / 1000
                if (timeLeft > 0) {
                    player.sendMessage("${ChatColor.RED}쿨타임이 ${timeLeft}초 남았습니다.")
                    return
                }
            }

            if (player.totalExperience < 20) {
                player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
                return
            }

            dash(player)
            cooldowns[playerId] = currentTime
        }
    }
}
