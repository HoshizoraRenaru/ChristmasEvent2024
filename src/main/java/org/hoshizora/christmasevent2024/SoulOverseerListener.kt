package org.hoshizora.christmasevent2024

import org.bukkit.ChatColor
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class SoulOverseerListener(private val plugin: Main) : Listener {
    private var skillActive = false
    private var skillTask: BukkitRunnable? = null

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val player = event.player

        // 우클릭을 감지하고 아이템이 "소울 오버시어"일 경우
        if ((event.action == org.bukkit.event.block.Action.RIGHT_CLICK_AIR || event.action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) &&
            player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())) {

            if (!skillActive) {
                activateSkill(player)
            } else {
                deactivateSkill(player)
            }
        }
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player

        // 스킬이 활성화된 상태에서 아이템을 드랍하면 스킬 중지
        if (skillActive && player.inventory.itemInMainHand.isSimilar(ItemManager.createSoulOverseer())) {
            deactivateSkill(player)
        }
    }

    private fun activateSkill(player: Player) {
        skillActive = true

        player.sendMessage("${ChatColor.GREEN}소울 바이트 발동됨")

        skillTask = object : BukkitRunnable() {
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
            it.runTaskTimer(plugin, 0L, 2L) // 타이머 실행
        }
    }

    private fun deactivateSkill(player: Player) {
        skillTask?.cancel() // 반복 작업 중지
        skillTask = null // 스킬 작업 초기화
        skillActive = false // 스킬 비활성화 상태로 변경

        player.sendMessage("${ChatColor.RED}소울 바이트 중지됨")
    }

    private fun applyEffects(player: Player) {
        // 플레이어의 펫을 제외한 LivingEntity에게만 효과 적용
        val pets = player.world.entities.filterIsInstance<LivingEntity>().filter { entity ->
            (entity is org.bukkit.entity.Wolf && entity.owner == player) || // 강아지 확인 (여기서 owner는 실제로 존재하지 않음)
                    (entity is org.bukkit.entity.Cat && entity.owner == player)   // 고양이 확인 (여기서 owner는 실제로 존재하지 않음)
        }

        player.world.getNearbyEntities(player.location, 5.0, 5.0, 5.0).forEach { entity ->
            if (entity is LivingEntity && entity != player && entity !in pets) {
                entity.damage(1.0) // 초당 1 데미지
                entity.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 0)) // 구속 효과
                player.world.spawnParticle(org.bukkit.Particle.REDSTONE, entity.location, 10,
                    org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 1f)) // 파란색 먼지 파티클 생성
            }
        }
    }
}