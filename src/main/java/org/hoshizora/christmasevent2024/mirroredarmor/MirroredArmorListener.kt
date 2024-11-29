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
            val isFullSet = isFullSetEquipped(player)

            if (isFullSet) {
                increaseMaxHealth(player, 8.0)
            } else {
                resetMaxHealth(player)
            }

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

    private fun isFullSetEquipped(player: Player): Boolean {
        val helmet = player.inventory.helmet
        val chestplate = player.inventory.chestplate
        val leggings = player.inventory.leggings
        val boots = player.inventory.boots

        return helmet != null && isMirroredLeatherArmor(helmet) &&
                chestplate != null && isMirroredLeatherArmor(chestplate) &&
                leggings != null && isMirroredLeatherArmor(leggings) &&
                boots != null && isMirroredLeatherArmor(boots)
    }

    private fun isMirroredLeatherArmor(item: ItemStack): Boolean {
        if (!item.hasItemMeta()) return false
        val meta = item.itemMeta ?: return false

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

    private fun countWornMirroredArmorParts(player: Player): Int {
        val armorPieces = listOf(
            player.inventory.helmet,
            player.inventory.chestplate,
            player.inventory.leggings,
            player.inventory.boots
        )
        return armorPieces.count { it != null && isMirroredLeatherArmor(it) }
    }

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
            attribute.baseValue = 20.0
            player.health = player.health.coerceAtMost(20.0)
        }
    }

    @EventHandler
    fun onArmorChange(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player is Player) {
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

