package org.hoshizora.christmasevent2024

import org.bukkit.*
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FireworkExplodeEvent
import org.bukkit.persistence.PersistentDataType
import java.util.*

class YoinoYoYoiListener(private val plugin: JavaPlugin) : Listener {

    private val cooldowns = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.isSimilar(YoinoYoYoiItemManager.createYoinoYoYoi()) && (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {
            event.isCancelled = true
            useSkill(player)
        }
    }

    private fun useSkill(player: Player) {
        val playerId = player.uniqueId
        val currentTime = System.currentTimeMillis()
        val cooldownTime = 15000 // 15 seconds in milliseconds

        if (cooldowns.containsKey(playerId)) {
            val timeLeft = ((cooldowns[playerId]!! + cooldownTime) - currentTime) / 1000
            if (timeLeft > 0) {
                player.sendMessage("${ChatColor.RED}스킬 쿨타임이 ${timeLeft}초 남았습니다.")
                return
            }
        }

        // Check if the player has enough experience points
        if (player.totalExperience < 20) {
            player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
            return
        }

        // Deduct 10 experience points from the player
        player.giveExp(-20)

        cooldowns[playerId] = currentTime

        // Launch fireworks immediately
        launchFirework(player.location, Color.RED)
        launchFirework(player.location, Color.RED)
        launchFirework(player.location, Color.ORANGE)
        launchFirework(player.location, Color.YELLOW)

        // Spawn FLAME particles around the player
        spawnFlameParticles(player)

        // Apply potion effect with hidden particles
        player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION, 60, 0, false, false))
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&l天真爛漫 &c&l宵の&4&l余、&e&l良い！"))
    }

    private fun launchFirework(location: Location, color: Color) {
        val fw: Firework = location.world!!.spawn(location, Firework::class.java)
        val fwm: FireworkMeta = fw.fireworkMeta
        fwm.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(color).build())
        fwm.power = 0
        fw.fireworkMeta = fwm
        fw.persistentDataContainer.set(NamespacedKey(plugin, "skillFirework"), PersistentDataType.BYTE, 1)
    }

    private fun spawnFlameParticles(player: Player) {
        val location = player.location.add(0.0, 1.0, 0.0)
        val world = location.world ?: return

        val particleCount = 152
        val spreadRadius = 0.1
        val speed = 0.8

        world.spawnParticle(
            Particle.FLAME,
            location,
            particleCount,
            spreadRadius, // x
            spreadRadius, // y
            spreadRadius, // z
            speed
        )
    }
}