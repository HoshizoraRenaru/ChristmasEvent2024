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

class SoulOverseerListener(private val plugin: Main) : Listener {
    private var skillActive = false
    private var skillTask: BukkitRunnable? = null

    // 우클릭 감지 및 스킬 활성화/비활성화
    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val player = event.player

        // 블록에서 우클릭 시 아예 작동하지 않게 하기
        if (event.action == Action.RIGHT_CLICK_BLOCK && player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())) {
            event.isCancelled = true // 블록에서 우클릭 시 이벤트 취소
            return // 블록에서 우클릭하면 더 이상 처리하지 않음
        }

        // 공기에서 우클릭 시만 작동하도록 처리
        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) &&
            player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())
        ) {

            // 블록에서 우클릭은 아예 무시하고, 공기 중에서만 스킬 발동
            if (!skillActive) {
                activateSkill(player)
            } else {
                deactivateSkill(player)
            }
        }

        // 블록 파괴 방지 (아이템이 소울 오버시어일 경우)
        if (player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer()) &&
            (event.action == Action.LEFT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_BLOCK)
        ) {
            event.isCancelled = true // 블록 상호작용 취소
        }

        // 근접 공격 방지 (아이템이 소울 오버시어일 경우)
        if (event.action == Action.LEFT_CLICK_AIR || event.action == Action.LEFT_CLICK_BLOCK) {
            if (player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())) {
                event.isCancelled = true // 공격 취소
            }
        }
    }

    // 아이템 드롭 시 스킬 비활성화
    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player

        // 드롭된 아이템이 소울 오버시어와 동일하면 스킬 중지
        if (skillActive && event.itemDrop.itemStack.isSimilar(ItemManager.createSoulOverseer())) {
            deactivateSkill(player)
        }
    }

    // 아이템 내구도 감소 방지 (소울 오버시어)
    @EventHandler
    fun onItemDamage(event: PlayerItemDamageEvent) {
        val item = event.item

        // 아이템이 "소울 오버시어"일 경우 내구도 감소 방지
        if (item.isSimilar(ItemManager.createSoulOverseer())) {
            event.isCancelled = true // 내구도 감소를 취소
        }
    }

    // 스킬 활성화
    private fun activateSkill(player: Player) {
        skillActive = true
        player.sendMessage("${ChatColor.GREEN}소울 바이트 발동됨")

        skillTask = object : BukkitRunnable() {
            override fun run() {
                if (player.totalExperience > 0) { // 경험치가 남아있으면
                    player.giveExp(-1) // 0.1초당 1 경험치 소모
                    applyEffects(player)

                    // 플레이어를 중심으로 구형 파티클 생성 (데미지 범위 표시)
                    val radius = 3.0 // 데미지 범위 반경
                    for (angle in 0..360 step 10) { // 각도를 조정하여 원형 파티클 생성
                        for (y in -1..1 step 1) { // Y축 방향으로도 파티클 생성
                            val radians = Math.toRadians(angle.toDouble())
                            val x = radius * Math.cos(radians)
                            val z = radius * Math.sin(radians)

                            // 랜덤 오프셋 추가 (-0.5 ~ 0.5)
                            val randomOffsetX = Random.nextDouble(-0.5, 0.5)
                            val randomOffsetY = Random.nextDouble(-0.5, 0.5)
                            val randomOffsetZ = Random.nextDouble(-0.5, 0.5)

                            // 기본 파란색 먼지 파티클 생성
                            player.world.spawnParticle(
                                org.bukkit.Particle.REDSTONE,
                                player.location.x + x + randomOffsetX,
                                player.location.y + y + 1.5 + randomOffsetY, // 플레이어 머리 위에 파티클 생성
                                player.location.z + z + randomOffsetZ,
                                0,
                                org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 1f)
                            ) // 기본 파란색 먼지 파티클 생성

                            // AQUA 색 먼지 파티클 생성 (틈 사이에 추가)
                            if (angle % 30 == 0) { // 예를 들어, 각도가 30도일 때만 AQUA 색 먼지 추가
                                player.world.spawnParticle(
                                    org.bukkit.Particle.REDSTONE,
                                    player.location.x + x + randomOffsetX,
                                    player.location.y + y + 1.5 + randomOffsetY,
                                    player.location.z + z + randomOffsetZ,
                                    0,
                                    org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(0, 255, 255), 1f)
                                ) // AQUA 색 먼지 파티클 생성
                            }

                            // 눈 파티클 생성 (틈 사이에 추가)
                            if (angle % 60 == 0) { // 예를 들어, 각도가 60도일 때만 눈 추가
                                player.world.spawnParticle(
                                    org.bukkit.Particle.SNOWBALL,
                                    player.location.x + x + randomOffsetX,
                                    player.location.y + y + 1.5 + randomOffsetY,
                                    player.location.z + z + randomOffsetZ,
                                    0
                                ) // 눈 파티클 생성
                            }
                        }
                    }
                } else {
                    player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
                    deactivateSkill(player)
                }
            }
        }.also {
            it.runTaskTimer(plugin, 0L, 2L) // 타이머 실행 (0초 후 시작, 2틱마다 실행)
        }
    }

    // 스킬 비활성화
    private fun deactivateSkill(player: Player) {
        skillTask?.cancel() // 반복 작업 중지
        skillTask = null // 스킬 작업 초기화
        skillActive = false // 스킬 비활성화 상태로 변경

        player.sendMessage("${ChatColor.RED}소울 바이트 중지됨")
    }

    // 주위의 적에게 피해와 구속 효과 적용
    private fun applyEffects(player: Player) {
        player.world.getNearbyEntities(player.location, 3.0, 3.0, 3.0).forEach { entity ->
            if (entity is LivingEntity && entity != player) { // 본인 제외하기 위해 조건 추가 가능
                entity.damage(1.0) // 초당 1 데미지
                // 구속 효과를 적용하되, 파티클을 숨기기 위해 'isShowParticles'를 false로 설정
                entity.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 0, true, false)) // 파티클 숨김
            }
        }
    }
}