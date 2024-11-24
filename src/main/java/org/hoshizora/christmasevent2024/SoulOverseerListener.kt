package org.hoshizora.christmasevent2024

import org.bukkit.ChatColor
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.util.Vector

class SoulOverseerListener(private val plugin: Main) : Listener {
    private var skillActive = false
    private var skillTask: BukkitRunnable? = null
    private var damageTask: BukkitRunnable? = null

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val player = event.player

        if (event.action == Action.RIGHT_CLICK_BLOCK && player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())) {
            event.isCancelled = true
            return
        }

        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) &&
            player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())
        ) {
            if (!skillActive) {
                activateSkill(player)
            } else {
                deactivateSkill(player)
            }
        }

        if (player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer()) &&
            (event.action == Action.LEFT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_BLOCK)
        ) {
            event.isCancelled = true
        }

        if (event.action == Action.LEFT_CLICK_AIR || event.action == Action.LEFT_CLICK_BLOCK) {
            if (player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        if (skillActive && event.itemDrop.itemStack.isSimilar(ItemManager.createSoulOverseer())) {
            deactivateSkill(player)
        }
    }

    @EventHandler
    fun onItemDamage(event: PlayerItemDamageEvent) {
        val item = event.item
        if (item.isSimilar(ItemManager.createSoulOverseer())) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        val entity = event.entity

        if (damager is Player && entity is LivingEntity) {
            if (damager.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer()) && skillActive) {
                entity.velocity = Vector(0, 0, 0)
            }
        }
    }

    private fun activateSkill(player: Player) {
        skillActive = true
        player.sendMessage("${ChatColor.GREEN}소울 바이트 발동됨")

        // 파티클 생성 (2틱 간격)
        skillTask = object : BukkitRunnable() {
            override fun run() {
                spawnParticles(player)
            }
        }.also {
            it.runTaskTimer(plugin, 0L, 2L)  // 2틱마다 실행
        }

        // 데미지 및 효과 적용 (0.5초마다)
        damageTask = object : BukkitRunnable() {
            override fun run() {
                if (player.totalExperience > 0) {
                    player.giveExp(-1)
                    applyEffects(player)
                } else {
                    player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
                    deactivateSkill(player)
                }
            }
        }.also {
            it.runTaskTimer(plugin, 0L, 10L)  // 10틱마다 실행 (0.5초마다)
        }
    }

    private fun deactivateSkill(player: Player) {
        skillTask?.cancel()
        damageTask?.cancel()
        skillTask = null
        damageTask = null
        skillActive = false
        player.sendMessage("${ChatColor.RED}소울 바이트 중지됨")
    }

    private fun applyEffects(player: Player) {
        player.world.getNearbyEntities(player.location, 3.0, 3.0, 3.0).forEach { entity ->
            if (entity is LivingEntity && entity != player) {
                val damage = 2.0
                entity.damage(damage, player)
                entity.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 1, true, false))

                // 넉백 방지
                entity.velocity = Vector(0, 0, 0)  // 넉백을 없애기 위해 속도를 0으로 설정
            }
        }
    }

    private fun spawnParticles(player: Player) {
        val radius = 3.0
        for (angle in 0..360 step 10) {
            for (y in -1..1 step 1) {
                val radians = Math.toRadians(angle.toDouble())
                val x = radius * Math.cos(radians)
                val z = radius * Math.sin(radians)

                val randomOffsetX = Random.nextDouble(-0.5, 0.5)
                val randomOffsetY = Random.nextDouble(-0.5, 0.5)
                val randomOffsetZ = Random.nextDouble(-0.5, 0.5)

                player.world.spawnParticle(
                    org.bukkit.Particle.REDSTONE,
                    player.location.x + x + randomOffsetX,
                    player.location.y + y + 1.5 + randomOffsetY,
                    player.location.z + z + randomOffsetZ,
                    0,
                    org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 1f)
                )

                if (angle % 30 == 0) {
                    player.world.spawnParticle(
                        org.bukkit.Particle.REDSTONE,
                        player.location.x + x + randomOffsetX,
                        player.location.y + y + 1.5 + randomOffsetY,
                        player.location.z + z + randomOffsetZ,
                        0,
                        org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(0, 255, 255), 1f)
                    )
                }

                if (angle % 60 == 0) {
                    player.world.spawnParticle(
                        org.bukkit.Particle.SNOWBALL,
                        player.location.x + x + randomOffsetX,
                        player.location.y + y + 1.5 + randomOffsetY,
                        player.location.z + z + randomOffsetZ,
                        0
                    )
                }
            }
        }
    }
}
