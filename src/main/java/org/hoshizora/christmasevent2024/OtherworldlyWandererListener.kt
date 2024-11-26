package org.hoshizora.christmasevent2024

import org.bukkit.*
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.event.block.Action
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class OtherworldlyWandererListener(private val plugin: JavaPlugin) : Listener {

    private val cooldowns = mutableMapOf<UUID, Long>()
    private val hitCombos = mutableMapOf<UUID, MutableMap<UUID, Long>>() // Player -> Target -> Last Hit Time

    init {
        // 파티클 효과를 위한 반복 작업 시작
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach { player ->
                    if (player.inventory.itemInMainHand.isSimilar(OtherworldlyWandererItemManager.createOtherworldlyWanderer())) {
                        spawnParticles(player)
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 5L) // 0.25초마다 실행 (5 ticks)
    }

    // 파티클 생성 메소드
    private fun spawnParticles(player: Player) {
        val location = player.location.add(0.0, 1.0, 0.0)

        // Wax Off 파티클 생성
        player.world.spawnParticle(Particle.WAX_OFF, location, 10, 0.5, 0.5, 0.5, 0.0)

        // Redstone 파티클 생성 (색상: #190158)
        val color = Color.fromRGB(25, 1, 88)
        val dustOptions = Particle.DustOptions(color, 1.0f)
        player.world.spawnParticle(Particle.REDSTONE, location, 10, 0.5, 0.5, 0.5, dustOptions)
    }

    // 패시브 스킬: "이계의 워프"
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.isSimilar(OtherworldlyWandererItemManager.createOtherworldlyWanderer()) && (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {
            event.isCancelled = true
            useWarpSkill(player)
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

            // 경험치 차감 및 쿨다운 설정은 텔레포트가 성공적으로 이루어진 후에만 적용
            player.giveExp(-20)
            cooldowns[playerId] = currentTime

            player.teleport(teleportLocation)
            player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_HURT, 1.0f, 0.5f)
            player.playSound(player.location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1.0f, 1.0f)
        } else {
            player.sendMessage("${ChatColor.DARK_PURPLE}범위 내의 목표를 찾을 수 없습니다.")
        }
    }

    // 액티브 스킬: "Hit Combo"
    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val attacker = event.damager as? Player ?: return
        val target = event.entity as? LivingEntity ?: return

        val currentTime = System.currentTimeMillis()

        // 타겟에 대한 마지막 히트 시간 기록
        val playerCombo = hitCombos.getOrPut(attacker.uniqueId) { mutableMapOf() }
        val lastHitTime = playerCombo.getOrPut(target.uniqueId) { 0L }

        // 5초 이내 동일 타겟을 3번 이상 때리면 콤보 발동
        if (currentTime - lastHitTime < 5000) {
            val comboHits = (playerCombo[target.uniqueId] ?: 0) + 1
            playerCombo[target.uniqueId] = comboHits

            if (comboHits >= 3) {
                // 신속 1 효과와 공격력 증가 1 효과 부여
                attacker.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 200, 0, false, false))
                attacker.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0, false, false))
                attacker.sendMessage("${ChatColor.YELLOW}Hit Combo 발동됨")

                // 효과가 10초간 지속되도록 설정
                hitCombos[attacker.uniqueId]?.put(target.uniqueId, currentTime)
            }
        } else {
            // 5초 이상 타격이 없으면 콤보 상태 해제
            playerCombo[target.uniqueId] = 0
        }
    }
}