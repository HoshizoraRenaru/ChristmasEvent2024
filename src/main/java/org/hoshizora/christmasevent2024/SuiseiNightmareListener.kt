package org.hoshizora.christmasevent2024

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.boss.BossBar
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.*
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector
import java.util.*
import kotlin.random.Random

class SuiseiNightmareListener(private val plugin: JavaPlugin, private val mobManager: SuiseiNightmareMobManager) : Listener {
    private val bossFlightLimitTask = mutableMapOf<UUID, BukkitTask>()
    private val bossBars = mutableMapOf<UUID, BossBar>()

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity.type == EntityType.WITHER && entity.customName?.startsWith("Nightmare") == true) {
            handleDamage(entity as Wither, event.damage)
        }
    }

    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity
        if (entity is Wither && entity.customName?.startsWith("Nightmare") == true) {
            startBossFlightLimitTask(entity)
            createBossBar(entity) // 보스바 생성

            // Wither의 기본 AI를 비활성화
            entity.setAI(false)

            // 기본 소리 제거
            object : BukkitRunnable() {
                override fun run() {
                    if (entity.isDead) {
                        cancel()
                    } else {
                        // Wither 소리를 억제하기 위해 직접 제어
                        entity.location.world?.playSound(
                            entity.location,
                            Sound.ENTITY_PLAYER_LEVELUP, // 플레이어 레벨업 소리 등으로 대체 가능
                            0f, // 볼륨 0
                            1f
                        )
                    }
                }
            }.runTaskTimer(plugin, 0L, 5L) // 반복적으로 실행
        }
    }

    private fun startBossFlightLimitTask(boss: Wither) {
        val task = object : BukkitRunnable() {
            val initialLocation = boss.location.clone()

            override fun run() {
                if (boss.isDead) {
                    cancel()
                    bossFlightLimitTask.remove(boss.uniqueId)
                    return
                }

                // XY 및 높이 고정
                boss.teleport(initialLocation.apply { y = initialLocation.y })
            }
        }.runTaskTimer(plugin, 0L, 5L) // 0.25초마다 고정 위치로 이동

        bossFlightLimitTask[boss.uniqueId] = task
    }


    @EventHandler
    fun onEntityChangeBlock(event: EntityChangeBlockEvent) {
        if (event.entity is Wither && event.entity.customName?.startsWith("Nightmare") == true) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        if (event.entity is Wither && event.entity.customName?.startsWith("Nightmare") == true) {
            event.blockList().clear()
        } else if (event.entity.type == EntityType.WITHER_SKULL) {
            val shooter = (event.entity as Projectile).shooter
            if (shooter is Wither && shooter.customName?.startsWith("Nightmare") == true) {
                event.blockList().clear()
            }
        }
    }

    private fun createBossBar(boss: Wither) {
        val bossBar = Bukkit.createBossBar(
            "${boss.customName} (${boss.health.toInt()})",
            BarColor.RED,
            BarStyle.SOLID
        )

        // 모든 플레이어에게 BossBar를 추가합니다.
        Bukkit.getOnlinePlayers().forEach { player ->
            bossBar.addPlayer(player)
        }

        bossBars[boss.uniqueId] = bossBar

        // 주기적으로 보스바 업데이트
        object : BukkitRunnable() {
            override fun run() {
                if (boss.isDead) {
                    bossBar.removeAll()
                    cancel()
                } else {
                    bossBar.setTitle("${boss.customName ?: "Unknown"} (${boss.health.coerceAtLeast(0.0).toInt()})")
                    bossBar.progress = boss.health / boss.maxHealth // 체력 비율 업데이트
                }
            }
        }.runTaskTimer(plugin, 0L, 20L) // 1초마다 업데이트
    }

    private fun spawnFakeTNT(location: Location) {
        object : BukkitRunnable() {
            override fun run() {
                location.world?.spawnParticle(Particle.EXPLOSION_LARGE, location, 1)
                location.world?.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f)
                location.world?.getNearbyEntities(location, 5.0, 5.0, 5.0)?.forEach { entity ->
                    if (entity is LivingEntity && entity !is Wither) {
                        entity.damage(5.0)
                    }
                }
            }
        }.runTaskLater(plugin, 40L)
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        if (entity is Wither && entity.customName?.startsWith("Nightmare") == true) {
            bossFlightLimitTask[entity.uniqueId]?.cancel()
            bossFlightLimitTask.remove(entity.uniqueId)
             handleDeath(entity)

            // 보스바 제거
            bossBars[entity.uniqueId]?.removeAll()
            bossBars.remove(entity.uniqueId)
        }
    }

    private fun handleDamage(boss: Wither, damage: Double) {
        val currentHealth = boss.health
        val newHealth = (currentHealth - damage).coerceAtLeast(1.0) // 최소 체력을 1로 설정

        when {
            newHealth <= 1.0 && currentHealth > 1.0 -> {
                boss.health = 1.0
                activateDeathSequence(boss)
            }
            else -> {
                boss.health = newHealth
                playDamageSound(boss.location)
                mobManager.updateBossBar(boss)

                when {
                    newHealth <= 1333 && currentHealth > 1333 -> activateTNTPattern(boss)
                    newHealth <= 666 && currentHealth > 666 -> activateLightningPattern(boss)
                }
            }
        }
    }

    private fun playDamageSound(location: Location) {
        val sound = if (Random.nextBoolean()) Sound.ENTITY_WITHER_DEATH else Sound.ENTITY_WITHER_HURT
        location.world?.playSound(location, sound, 0.5f, 1f)
    }

    private fun activateTNTPattern(boss: Wither) {
        object : BukkitRunnable() {
            override fun run() {
                if (!boss.isDead) {
                    spawnFakeTNT(boss.location)
                    playRandomWitherSound(boss.location)
                } else {
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 0L, 20L)
    }

    private fun playRandomWitherSound(location: Location) {
        val sound = Sound.ENTITY_WITHER_AMBIENT
        val pitch = if (Random.nextBoolean()) 0.9f else 0.7f
        location.world?.playSound(location, sound, 0.5f, pitch)
    }

    private fun activateLightningPattern(boss: Wither) {
        val location = boss.location
        repeat(10) {
            val strikeLocation = location.clone().add(
                Random.nextInt(-10, 11).toDouble(),
                0.0,
                Random.nextInt(-10, 11).toDouble()
            )
            location.world?.strikeLightning(strikeLocation)
        }
    }

    private fun activateDeathSequence(boss: Wither) {
        boss.isInvulnerable = true // 무적 상태로 설정
        boss.setAI(false) // AI 비활성화
        boss.velocity = Vector(0, 0, 0) // 움직임 정지

        object : BukkitRunnable() {
            var count = 0

            override fun run() {
                if (count < 12) {
                    boss.world.playSound(boss.location, Sound.ENTITY_WITHER_HURT, 1.0f, 1f)
                    count++
                } else {
                    repeat(20) {
                        spawnSpreadingTNT(boss.location)
                    }
                    boss.remove() // 보스 제거
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 0L, 5L) // 매 초마다 실행
    }

    private fun spawnSpreadingTNT(location: Location) {
        val world = location.world ?: return

        // TNT 위치 랜덤화
        val tntLocation = location.clone().add(
            Random.nextDouble(-10.0, 10.0),
            Random.nextDouble(0.0, 5.0),
            Random.nextDouble(-10.0, 10.0)
        )

        object : BukkitRunnable() {
            override fun run() {
                world.spawnParticle(Particle.EXPLOSION_HUGE, tntLocation, 1)
                world.playSound(tntLocation, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f)

                // 근처 엔티티에게 데미지 주기
                world.getNearbyEntities(tntLocation, 8.0, 8.0, 8.0).forEach { entity ->
                    if (entity is LivingEntity && entity !is Wither) {
                        entity.damage(10.0)
                    }
                }
            }
        }.runTaskLater(plugin, (20 + Random.nextLong(40)).coerceAtMost(60)) // 폭발 시간 랜덤화
    }

    private fun handleDeath(boss: Wither) {
        // 추가적인 사망 처리 로직
    }
}
