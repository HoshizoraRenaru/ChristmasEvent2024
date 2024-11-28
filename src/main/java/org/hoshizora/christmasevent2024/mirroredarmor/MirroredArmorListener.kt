package org.hoshizora.christmasevent2024.mirroredarmor

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class MirroredArmorListener(private val plugin: JavaPlugin) : Listener {

    // 트루 데미지 감소 로직
    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity is Player) {
            val player = entity
            val helmet = player.inventory.helmet
            val chestplate = player.inventory.chestplate
            val leggings = player.inventory.leggings
            val boots = player.inventory.boots

            // 착용한 갑옷 부위 수를 카운트
            val wornArmorParts = listOf(helmet, chestplate, leggings, boots).count { it != null && isMirroredArmorSet(it) }

            // 2부분 이상 착용했을 경우
            if (wornArmorParts >= 2) {
                // 트루 데미지 (마법 데미지, 불/용암 데미지 등) 감소 적용
                if (event.cause == EntityDamageEvent.DamageCause.MAGIC || event.cause == EntityDamageEvent.DamageCause.FIRE || event.cause == EntityDamageEvent.DamageCause.LAVA || event.cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    val damage = event.damage
                    val reducedDamage = applyTrueDamageReduction(damage)
                    event.damage = reducedDamage
                }
            }
        }
    }

    // MirroredArmor 세트 확인
    private fun isMirroredArmorSet(item: ItemStack): Boolean {
        return item.hasItemMeta() && item.itemMeta?.displayName == "Mirrored Armor"
    }

    // 트루 데미지 감소 로직 (예시로 20% 감소)
    private fun applyTrueDamageReduction(damage: Double): Double {
        val reductionFactor = 0.8 // 20% 감소
        return damage * reductionFactor
    }
}
