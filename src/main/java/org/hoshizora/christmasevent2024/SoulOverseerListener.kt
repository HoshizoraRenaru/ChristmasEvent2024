package org.hoshizora.christmasevent2024

import org.bukkit.ChatColor
import org.bukkit.Sound
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
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.util.Vector
import kotlin.random.Random

class SoulOverseerListener(private val plugin: Main) : Listener {
    private val skillActive = mutableMapOf<Player, Boolean>() // 플레이어별 기술 활성화 상태 저장
    private val skillTasks = mutableMapOf<Player, MutableList<BukkitRunnable>>() // 플레이어별 작업 저장

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val player = event.player

        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) &&
            player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())
        ) {
            event.isCancelled = true

            if (skillActive[player] == true) {
                deactivateSkill(player)
            } else {
                activateSkill(player)
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
        if (skillActive[player] == true && event.itemDrop.itemStack.isSimilar(ItemManager.createSoulOverseer())) {
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
            if (damager.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer()) && skillActive[damager] == true) {
                entity.velocity = Vector(0, 0, 0)
            }
        }
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        val killer = entity.killer

        if (killer is Player && skillActive[killer] == true &&
            killer.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())
        ) {
            entity.world.playSound(entity.location, Sound.ENTITY_PLAYER_HURT_FREEZE, 1f, 0.7f)
        }
    }

    private fun activateSkill(player: Player) {
        skillActive[player] = true
        player.sendMessage("${ChatColor.GREEN}소울 바이트 발동됨")

        val tasks = mutableListOf<BukkitRunnable>()

        // 지속적인 소리 재생
        val soundTask = object : BukkitRunnable() {
            override fun run() {
                player.world.playSound(player.location, Sound.ITEM_ELYTRA_FLYING, 1f, 0.5f)
            }
        }.also {
            it.runTaskTimer(plugin, 0L, 20L) // 1초마다 소리 재생
            tasks.add(it)
        }

        val particleTask = object : BukkitRunnable() {
            override fun run() {
                spawnParticles(player)
            }
        }.also {
            it.runTaskTimer(plugin, 0L, 2L) // 2틱마다 실행
            tasks.add(it)
        }

        // 주변 적들에게 데미지와 효과 (1초마다)
        val damageTask = object : BukkitRunnable() {
            override fun run() {
                applyEffects(player)
            }
        }.also {
            it.runTaskTimer(plugin, 0L, 20L) // 20틱(1초)마다 실행
            tasks.add(it)
        }

        // 경험치 소모 (0.1초마다)
        val expTask = object : BukkitRunnable() {
            override fun run() {
                if (player.totalExperience > 0) {
                    player.giveExp(-1)
                } else {
                    player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
                    deactivateSkill(player)
                }
            }
        }.also {
            it.runTaskTimer(plugin, 0L, 2L) // 0.1초마다 실행
            tasks.add(it)
        }

        skillTasks[player] = tasks
    }

    private fun deactivateSkill(player: Player) {
        skillTasks[player]?.forEach { it.cancel() }
        skillTasks.remove(player)

        skillActive[player] = false
        player.sendMessage("${ChatColor.RED}소울 바이트 중지됨")
    }

    private fun applyEffects(player: Player) {
        player.world.getNearbyEntities(player.location, 3.0, 3.0, 3.0).forEach { entity ->
            if (entity is LivingEntity && entity != player) {
                val damage = 2.0
                entity.damage(damage, player)
                entity.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 1, true, false))
                entity.velocity = Vector(0, 0, 0) // 넉백 방지
            }
        }
    }

    private fun spawnParticles(player: Player) {
        val radius = 3.0
        for (angle in 0..360 step 10) { // 각도별로 파티클 생성
            for (y in -1..1 step 1) { // 높이별로 파티클 생성
                val radians = Math.toRadians(angle.toDouble())
                val x = radius * Math.cos(radians)
                val z = radius * Math.sin(radians)

                val randomOffsetX = Random.nextDouble(-0.5, 0.5)
                val randomOffsetY = Random.nextDouble(-0.5, 0.5)
                val randomOffsetZ = Random.nextDouble(-0.5, 0.5)

                // 기본 파란색 REDSTONE 파티클
                player.world.spawnParticle(
                    org.bukkit.Particle.REDSTONE,
                    player.location.x + x + randomOffsetX,
                    player.location.y + y + 1.5 + randomOffsetY,
                    player.location.z + z + randomOffsetZ,
                    0,
                    org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 1f)
                )

                // SOUL 파티클 - 10% 확률로 생성
                if (Random.nextDouble() < 0.03) {
                    player.world.spawnParticle(
                        org.bukkit.Particle.SOUL,
                        player.location.x + x + randomOffsetX,
                        player.location.y + y + 1.5 + randomOffsetY,
                        player.location.z + z + randomOffsetZ,
                        0,
                    )
                }

                // 추가: 밝은 청록색 REDSTONE 파티클
                player.world.spawnParticle(
                    org.bukkit.Particle.REDSTONE,
                    player.location.x + x + randomOffsetX,
                    player.location.y + y + 1.5 + randomOffsetY,
                    player.location.z + z + randomOffsetZ,
                    0,
                    org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(0, 255, 255), 1f)
                )

                // SNOWBALL 파티클 (각도와 무관하게 생성)
                player.world.spawnParticle(
                    org.bukkit.Particle.SNOWBALL,
                    player.location.x + x + randomOffsetX,
                    player.location.y + y + 1.5 + randomOffsetY,
                    player.location.z + z + randomOffsetZ,
                    1
                )
            }
        }
    }
}
