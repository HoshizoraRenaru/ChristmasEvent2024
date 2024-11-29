package org.hoshizora.christmasevent2024.mirroredarmor

import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class MirroredArmorListener(private val plugin: JavaPlugin) : Listener {

    // 트루 데미지 감소 로직
    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity is Player) {
            val player = entity

            // 풀 세트 여부 확인
            val isFullSet = isFullSetEquipped(player)

            // 풀 세트 착용 시 최대 체력 증가
            if (isFullSet) {
                increaseMaxHealth(player, 8.0) // 하트 4칸 (8 체력) 증가
            } else {
                resetMaxHealth(player) // 원래 체력으로 복구
            }

            // 트루 데미지 감소 적용 (풀 세트와 상관없이 2부위 이상 착용 시)
            val wornArmorParts = countWornMirroredArmorParts(player)
            if (wornArmorParts >= 2) {
                if (event.cause in listOf(
                        EntityDamageEvent.DamageCause.MAGIC,
                        EntityDamageEvent.DamageCause.FIRE,
                        EntityDamageEvent.DamageCause.LAVA,
                        EntityDamageEvent.DamageCause.SUFFOCATION
                    )
                ) {
                    val damage = event.damage
                    val reducedDamage = applyTrueDamageReduction(damage)
                    event.damage = reducedDamage
                }
            }
        }
    }

    // MirroredArmor 풀 세트 확인
    private fun isFullSetEquipped(player: Player): Boolean {
        val helmet = player.inventory.helmet
        val chestplate = player.inventory.chestplate
        val leggings = player.inventory.leggings
        val boots = player.inventory.boots

        // 각 아이템이 MirroredArmor로 생성된 가죽 갑옷과 일치하는지 확인
        return helmet != null && isMirroredLeatherArmor(helmet) &&
                chestplate != null && isMirroredLeatherArmor(chestplate) &&
                leggings != null && isMirroredLeatherArmor(leggings) &&
                boots != null && isMirroredLeatherArmor(boots)
    }

    // MirroredArmor 가죽 갑옷 확인
    private fun isMirroredLeatherArmor(item: ItemStack): Boolean {
        if (!item.hasItemMeta()) return false
        val meta = item.itemMeta ?: return false

        // 각 가죽 갑옷 아이템에 대해 이름과 lore를 비교
        return when (item.type) {
            Material.LEATHER_HELMET -> {
                val mirroredHelmet = MirroredHelmetItemManager.create()
                meta.displayName == mirroredHelmet.itemMeta?.displayName &&
                        meta.lore == mirroredHelmet.itemMeta?.lore
            }
            Material.LEATHER_CHESTPLATE -> {
                val mirroredChestplate = MirroredChestplateItemManager.create()
                meta.displayName == mirroredChestplate.itemMeta?.displayName &&
                        meta.lore == mirroredChestplate.itemMeta?.lore
            }
            Material.LEATHER_LEGGINGS -> {
                val mirroredLeggings = MirroredLeggingsItemManager.create()
                meta.displayName == mirroredLeggings.itemMeta?.displayName &&
                        meta.lore == mirroredLeggings.itemMeta?.lore
            }
            Material.LEATHER_BOOTS -> {
                val mirroredBoots = MirroredBootsItemManager.create()
                meta.displayName == mirroredBoots.itemMeta?.displayName &&
                        meta.lore == mirroredBoots.itemMeta?.lore
            }
            else -> false
        }
    }

    // MirroredArmor 착용 부위 카운트
    private fun countWornMirroredArmorParts(player: Player): Int {
        val armorPieces = listOf(
            player.inventory.helmet,
            player.inventory.chestplate,
            player.inventory.leggings,
            player.inventory.boots
        )
        return armorPieces.count { it != null && isMirroredLeatherArmor(it) }
    }

    // 트루 데미지 감소 로직 (예시로 20% 감소)
    private fun applyTrueDamageReduction(damage: Double): Double {
        val reductionFactor = 0.8 // 20% 감소
        return damage * reductionFactor
    }

    private fun increaseMaxHealth(player: Player, amount: Double) {
        val attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
        if (attribute != null) {
            val baseHealth = 20.0
            val newMaxHealth = baseHealth + amount
            attribute.baseValue = newMaxHealth
            player.health = newMaxHealth
        }
    }

    private fun resetMaxHealth(player: Player) {
        val attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
        if (attribute != null) {
            attribute.baseValue = 20.0 // 기본 체력으로 복구
            player.health = player.health.coerceAtMost(20.0)
        }
    }

    @EventHandler
    fun onArmorChange(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player is Player) {
            // 인벤토리 변경 후 다음 틱에 체력 업데이트를 수행
            plugin.server.scheduler.runTask(plugin, Runnable {
                val isFullSet = isFullSetEquipped(player)
                if (isFullSet) {
                    increaseMaxHealth(player, 8.0)
                } else {
                    resetMaxHealth(player)
                }
            })
        }
    }
}

