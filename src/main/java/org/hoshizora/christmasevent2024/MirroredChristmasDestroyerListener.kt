package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.*
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class MirroredChristmasDestroyerListener(private val plugin: JavaPlugin) : Listener {

    private val activeShields = mutableMapOf<UUID, Long>()
    private val cooldowns = mutableMapOf<UUID, Long>()

    private fun isMirroredChristmasDestroyer(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false

        val meta = item.itemMeta ?: return false
        return meta.displayName == MirroredChristmasDestroyerItemManager.createMirroredChristmasDestroyer().itemMeta?.displayName &&
                meta.lore == MirroredChristmasDestroyerItemManager.createMirroredChristmasDestroyer().itemMeta?.lore &&
                item.type == MirroredChristmasDestroyerItemManager.createMirroredChristmasDestroyer().type
    }

    init {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach { player ->
                    if (isMirroredChristmasDestroyer(player.inventory.itemInMainHand)) {
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

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (isMirroredChristmasDestroyer(item) &&
            (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {

            event.isCancelled = true

            val playerId = player.uniqueId
            val currentTime = System.currentTimeMillis()
            val cooldownTime = 30000

            if (cooldowns.containsKey(playerId)) {
                val timeLeft = ((cooldowns[playerId]!! + cooldownTime) - currentTime) / 1000
                if (timeLeft > 0) {
                    player.sendMessage("${ChatColor.RED}쿨타임이 ${timeLeft}초 남았습니다.")
                    return
                }
            }

            if (player.totalExperience < 30) {
                player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
                return
            }

            activateReflectionShield(player)
        }
    }

    private fun activateReflectionShield(player: Player) {
        val playerId = player.uniqueId

        player.giveExp(-30)
        activeShields[playerId] = System.currentTimeMillis()
        cooldowns[playerId] = System.currentTimeMillis()

        player.world.playSound(player.location, Sound.BLOCK_BELL_RESONATE, 1.0f, 2.0f)
        player.world.spawnParticle(Particle.ELECTRIC_SPARK, player.location.add(0.0, 1.0, 0.0), 50, 0.5, 1.0, 0.5, 0.0)
        player.sendMessage("${ChatColor.of("#2a00ad")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}R${ChatColor.of("#2c07b6")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#2e0ebf")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}f${ChatColor.of("#3115c8")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}l${ChatColor.of("#331cd1")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#3523da")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}c${ChatColor.of("#382ae3")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}t${ChatColor.of("#3a31ec")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}i${ChatColor.of("#3c38f5")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("#0000ff")}${ChatColor.BLUE}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}n${ChatColor.WHITE}${ChatColor.BOLD} ⚝ ${ChatColor.GREEN}${ChatColor.BOLD}쉴드 적용됨")

        val particleTask = object : BukkitRunnable() {
            override fun run() {
                if (!activeShields.containsKey(playerId)) {
                    cancel()
                    return
                }
                player.world.spawnParticle(Particle.ELECTRIC_SPARK, player.location.add(0.0, 1.0, 0.0), 20, 0.3, 0.3, 0.3, 0.01)
            }
        }
        particleTask.runTaskTimer(plugin, 0L, 1L) // 0 틱 딜레이로 시작, 이후 10틱마다 실행 (0.5초 간격)

        object : BukkitRunnable() {
            override fun run() {
                if (activeShields.remove(playerId) != null) {
                    // 파티클 폭발 효과
                    player.world.spawnParticle(Particle.ELECTRIC_SPARK, player.location.add(0.0, 1.0, 0.0), 110, 1.0, 1.0, 1.0, 0.5)
                    player.world.playSound(player.location, Sound.BLOCK_GLASS_BREAK, 1.0f, 0.5f)
                    player.sendMessage("${ChatColor.of("#2a00ad")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}R${ChatColor.of("#2c07b6")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#2e0ebf")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}f${ChatColor.of("#3115c8")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}l${ChatColor.of("#331cd1")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#3523da")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}c${ChatColor.of("#382ae3")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}t${ChatColor.of("#3a31ec")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}i${ChatColor.of("#3c38f5")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("#0000ff")}${ChatColor.BLUE}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}n${ChatColor.WHITE}${ChatColor.BOLD} ⚝ ${ChatColor.RED}${ChatColor.BOLD}쉴드 파괴됨")
                }
            }
        }.runTaskLater(plugin, 200L)
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val target = event.entity as? Player ?: return
        val attacker = event.damager as? LivingEntity ?: return

        if (!activeShields.containsKey(target.uniqueId)) return

        val damage = event.damage

        attacker.damage(damage)
        event.isCancelled = true // 피해 무효화

        if (activeShields.remove(target.uniqueId) != null) {
            target.world.spawnParticle(Particle.ELECTRIC_SPARK, target.location.add(0.0, 1.0, 0.0), 110, 1.0, 1.0, 1.0, 0.5)
            target.world.playSound(target.location, Sound.BLOCK_GLASS_BREAK, 1.0f, 0.5f)
            target.sendMessage("${ChatColor.of("#2a00ad")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}R${ChatColor.of("#2c07b6")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#2e0ebf")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}f${ChatColor.of("#3115c8")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}l${ChatColor.of("#331cd1")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#3523da")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}c${ChatColor.of("#382ae3")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}t${ChatColor.of("#3a31ec")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}i${ChatColor.of("#3c38f5")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("#0000ff")}${ChatColor.BLUE}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}n${ChatColor.WHITE}${ChatColor.BOLD} ⚝ ${ChatColor.RED}${ChatColor.BOLD}쉴드 파괴됨")
        }
    }
}
