package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Damageable
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.text.DecimalFormat
import java.util.*

class NewtonListener(private val plugin: JavaPlugin) : Listener {

    private val absorptionCooldowns = mutableMapOf<UUID, Long>()
    private val skillCooldowns = mutableMapOf<UUID, Long>() // 스킬 쿨타임을 위한 맵

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (event.hand != EquipmentSlot.HAND) return

        if (isNewtonItem(item) &&
            (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {
            // 스킬 쿨타임 체크
            if (!canUseSkill(player)) return // 쿨타임 중이면 리턴

            event.isCancelled = true
            useNewtonSkill(player)
        }
    }

    private fun isNewtonItem(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false
        val meta = item.itemMeta ?: return false
        return meta.displayName == NewtonItemManager.createNewton().itemMeta?.displayName &&
                meta.lore == NewtonItemManager.createNewton().itemMeta?.lore
    }

    private fun useNewtonSkill(player: Player) {
        // 스킬 실행: 텔레포트 -> Absorption (가능한 경우) -> 폭발 효과
        teleportPlayer(player)
        if (canUseShield(player)) {
            applyAbsorptionShield(player)
        }
        createExplosion(player)

        // 스킬 사용 후 쿨타임 설정
        skillCooldowns[player.uniqueId] = System.currentTimeMillis()
    }

    private fun canUseSkill(player: Player): Boolean {
        val lastUsedTime = skillCooldowns[player.uniqueId] ?: 0L
        val currentTime = System.currentTimeMillis()
        val cooldown = (0.15 * 1000).toLong() // 0.2초를 밀리초로 변환

        return currentTime - lastUsedTime >= cooldown // 쿨타임이 지났는지 확인
    }

    private fun teleportPlayer(player: Player) {
        val loc = player.location
        val direction = loc.direction.normalize()
        val targetLoc = loc.clone()
        var blocked = false

        for (i in 1..10) {
            targetLoc.add(direction)

            if (targetLoc.block.type.isAir || !targetLoc.block.type.isSolid) {
                continue
            }

            targetLoc.subtract(direction)
            blocked = true
            break
        }

        player.teleport(targetLoc)

        if (blocked) {
            player.sendMessage("${ChatColor.RED}There are blocks in the way!")
        }

        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.0f)
        player.world.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1.0f)

        // 기존 입자 로직 유지
        player.world.spawnParticle(Particle.EXPLOSION_LARGE, loc, 3, 0.5, 0.5, 0.5, 0.0)
        player.world.spawnParticle(Particle.EXPLOSION_LARGE, targetLoc, 3, 0.5, 0.5, 0.5, 0.0)
    }

    private fun applyAbsorptionShield(player: Player) {
        val absorptionLevel = 3
        val durationTicks = 5 * 20 // 5초

        val absorptionEffect = PotionEffect(PotionEffectType.ABSORPTION, durationTicks, absorptionLevel, false, false)
        player.addPotionEffect(absorptionEffect)

        player.world.playSound(player.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 0.8f)

        // 파티클로 글씨 생성
        spawnCircleParticles(player)

        absorptionCooldowns[player.uniqueId] = System.currentTimeMillis()

        object : BukkitRunnable() {
            override fun run() {
                if (player.isOnline && !player.isDead) {
                    val currentAbsorption = player.absorptionAmount
                    player.removePotionEffect(PotionEffectType.ABSORPTION)
                    val healingAmount = currentAbsorption * 0.5
                    val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
                    val newHealth = (player.health + healingAmount).coerceAtMost(maxHealth)
                    player.health = newHealth

                    player.world.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.7f)
                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        player.world.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f)
                    }, 1L)
                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        player.world.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f)
                    }, 2L)

                    player.world.spawnParticle(Particle.HEART, player.location.add(0.0, 2.0, 0.0), 2, 0.0, 0.0, 0.0)
                }
            }
        }.runTaskLater(plugin, durationTicks.toLong())
    }

    private fun spawnCircleParticles(player: Player) {
        val startColor = Color.fromRGB(247, 0, 127) // #f7007f
        val endColor = Color.fromRGB(28, 162, 255) // #1ca2ff

        val totalSteps = 50 // 그라데이션 단계
        val radius = 0.3 // 반지름
        val particleCount = 30 // 파티클 개수

        // 방향 벡터를 얻고 회전 행렬을 적용하기 위한 준비
        val direction = player.location.direction.normalize()
        val right = direction.clone().crossProduct(Vector(0.0, 1.0, 0.0)).normalize()
        val up = right.clone().crossProduct(direction).normalize()

        // 플레이어 머리 앞 위치 계산
        val headLocation = player.location.add(0.0, 1.6, 0.0)
        val offset = direction.clone().multiply(0.5) // 머리 앞쪽 약간 떨어진 위치
        val baseLocation = headLocation.add(offset)

        for (i in 0 until particleCount) {
            // 각도를 계산하여 원 좌표 생성
            val angle = 2.0 * Math.PI * i / particleCount
            val x = radius * Math.cos(angle)
            val z = radius * Math.sin(angle)

            // 플레이어가 바라보는 방향에 맞게 회전 변환
            val rotated = right.clone().multiply(x).add(up.clone().multiply(z))
            val particleLocation = baseLocation.clone().add(rotated)

            // 그라데이션 색상 계산
            val t = i.toDouble() / totalSteps.coerceAtLeast(1)
            val r = interpolate(startColor.red, endColor.red, t)
            val g = interpolate(startColor.green, endColor.green, t)
            val b = interpolate(startColor.blue, endColor.blue, t)

            val color = Color.fromRGB(r, g, b)

            // 파티클 소환
            player.world.spawnParticle(
                Particle.REDSTONE,
                particleLocation,
                0,
                Particle.DustOptions(color, 0.5f) // 크기는 1.0으로 설정
            )
        }
    }

    private fun interpolate(start: Int, end: Int, t: Double): Int {
        return (start + (end - start) * t).toInt()
    }

    private fun createExplosion(player: Player) {
        val loc = player.location
        player.world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f)

        // 폭발 파티클 효과
        player.world.spawnParticle(
            Particle.EXPLOSION_LARGE,
            loc.x,
            loc.y + 1,
            loc.z,
            10, // 파티클 개수
            0.0, // X축 퍼짐 범위
            0.0, // Y축 퍼짐 범위
            0.0, // Z축 퍼짐 범위
            5.0  // 파티클 크기
        )

        val entities = player.getNearbyEntities(6.0, 6.0, 6.0)
            .filterIsInstance<Damageable>()
            .filter {
                it is Player && it != player ||
                        it !is Player && !listOf(
                    EntityType.ARMOR_STAND,
                    EntityType.ITEM_FRAME,
                    EntityType.MINECART,
                    EntityType.EXPERIENCE_ORB,
                    EntityType.DROPPED_ITEM,
                    EntityType.BOAT
                ).contains(it.type)
            }

        var totalDamage = 0.0

        for (entity in entities) {
            val initialHealth = entity.health

            val distance = loc.distance(entity.location)
            val damage = ((6 - distance) / 6 * (30 - 15) + 15).coerceIn(15.0, 30.0) // 최대 데미지를 30으로 설정

            entity.damage(damage, player)

            totalDamage += damage

            if (entity.health <= 0 && initialHealth > 0) {
                player.world.playSound(entity.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.5f)
            }
        }

        val df = DecimalFormat("#,###,###,###.#").apply { isGroupingUsed = true }
        val formattedDamage = df.format(totalDamage)

        if (totalDamage > 0.0) {
            player.sendMessage(
                "${ChatColor.GRAY}Your Implosion hit ${ChatColor.RED}${entities.size} " +
                        "${ChatColor.GRAY}enemies for ${ChatColor.RED}$formattedDamage ${ChatColor.GRAY}damage."
            )
        }
    }

    private fun canUseShield(player: Player): Boolean {
        val lastUsedTime = absorptionCooldowns[player.uniqueId] ?: 0L
        val currentTime = System.currentTimeMillis()
        val cooldown = 5000L // 5초 쿨다운

        return currentTime - lastUsedTime >= cooldown
    }
}
