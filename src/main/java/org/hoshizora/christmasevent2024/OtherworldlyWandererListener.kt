package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.ChatColor
import org.bukkit.*
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.event.block.Action
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class OtherworldlyWandererListener(private val plugin: JavaPlugin) : Listener {

    private val cooldowns = mutableMapOf<UUID, Long>()
    private val hitCombos = mutableMapOf<UUID, MutableMap<UUID, ComboInfo>>()

    private fun isOtherworldlyWanderer(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false

        val meta = item.itemMeta ?: return false
        return meta.displayName == OtherworldlyWandererItemManager.createOtherworldlyWanderer().itemMeta?.displayName &&
                meta.lore == OtherworldlyWandererItemManager.createOtherworldlyWanderer().itemMeta?.lore &&
                item.type == OtherworldlyWandererItemManager.createOtherworldlyWanderer().type
    }


    init {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach { player ->
                    if (isOtherworldlyWanderer(player.inventory.itemInMainHand)) {
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

    // 이계의 워프
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (event.hand != EquipmentSlot.HAND) {
            return
        }

        if (isOtherworldlyWanderer(item) &&
            (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {

            event.isCancelled = true

            useWarpSkill(player)
        }

        if (isOtherworldlyWanderer(item) &&
            (event.action == Action.LEFT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_BLOCK)) {

            event.isCancelled = true
        }

        if ((event.action == Action.LEFT_CLICK_AIR || event.action == Action.LEFT_CLICK_BLOCK) &&
            isOtherworldlyWanderer(item)) {

            event.isCancelled = true
        }
    }

    private fun useWarpSkill(player: Player) {
        val playerId = player.uniqueId
        val currentTime = System.currentTimeMillis()
        val cooldownTime = 15000

        if (cooldowns.containsKey(playerId)) {
            val timeLeft = ((cooldowns[playerId]!! + cooldownTime) - currentTime) / 1000
            if (timeLeft > 0) {
                player.sendMessage("${ChatColor.RED}쿨타임이 ${timeLeft}초 남았습니다.")
                return
            }
        }

        if (player.totalExperience < 20) {
            player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
            return
        }

        val nearbyEntities = player.world.getEntitiesByClass(LivingEntity::class.java)
        val target = nearbyEntities.filter { it != player }
            .minByOrNull { it.location.distance(player.location) }

        if (target != null) {
            val targetLocation = target.location
            val direction = targetLocation.direction.multiply(-1).normalize()

            val teleportLocation = targetLocation.add(direction.multiply(1))

            val maxDistance = 10.0

            val xDistance = teleportLocation.x - player.location.x
            val yDistance = teleportLocation.y - player.location.y
            val zDistance = teleportLocation.z - player.location.z

            if (Math.abs(xDistance) > maxDistance || Math.abs(yDistance) > maxDistance || Math.abs(zDistance) > maxDistance) {
                player.sendMessage("${ChatColor.DARK_PURPLE}범위 내의 목표를 찾을 수 없습니다.")
                return
            }

            player.giveExp(-20)
            cooldowns[playerId] = currentTime

            player.teleport(teleportLocation)
            player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_HURT, 1.0f, 0.5f)
            player.playSound(player.location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1.0f, 1.0f)
        } else {
            player.sendMessage("${ChatColor.DARK_PURPLE}범위 내의 목표를 찾을 수 없습니다.")
        }
    }

    // Hit Combo
    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val attacker = event.damager as? Player ?: return
        val target = event.entity as? LivingEntity ?: return

        if (!isOtherworldlyWanderer(attacker.inventory.itemInMainHand)) {
            return
        }

        val currentTime = System.currentTimeMillis()
        val playerCombo = hitCombos.getOrPut(attacker.uniqueId) { mutableMapOf() }
        val comboInfo = playerCombo.getOrPut(target.uniqueId) { ComboInfo() }

        if (currentTime - comboInfo.lastHitTime < 5000) {
            comboInfo.hitCount++

            if (comboInfo.hitCount >= 3) {
                activateCombo(attacker, comboInfo.hitCount)
            }
        } else {
            comboInfo.hitCount = 1
        }

        comboInfo.lastHitTime = currentTime

        comboInfo.taskId?.let { Bukkit.getScheduler().cancelTask(it) }

        comboInfo.taskId = Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            if (System.currentTimeMillis() - comboInfo.lastHitTime >= 10000) {
                if (playerCombo.remove(target.uniqueId) != null) {
                    deactivateCombo(attacker)
                }
            }
        }, 200L).taskId // ComboBreak time
    }

    private fun activateCombo(player: Player, hitCount: Int) {
        applyComboEffects(player)

        val message = if (hitCount == 3) {
            "${ChatColor.DARK_PURPLE}${ChatColor.BOLD}Hit Combo 발동됨"
        } else {
            "${ChatColor.DARK_PURPLE}${ChatColor.BOLD}Hit Combo 발동됨"
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(message))
    }

    private fun deactivateCombo(player: Player) {
        player.removePotionEffect(PotionEffectType.SPEED)
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE)
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("${ChatColor.RED}${ChatColor.BOLD}Hit Combo 초기화됨"))
    }

    private fun applyComboEffects(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 200, 0, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0, false, false))
    }
}
