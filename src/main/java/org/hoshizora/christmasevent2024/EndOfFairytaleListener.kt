package org.hoshizora.christmasevent2024

import org.bukkit.*
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
import java.util.*

class EndOfFairytaleListener(private val plugin: JavaPlugin) : Listener {

    private val cooldowns = mutableMapOf<UUID, Long>()

    private fun isFairyTaleEnding(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false

        val meta = item.itemMeta ?: return false
        return meta.displayName == EndOfFairytaleItemManager.createEndOfFairytale().itemMeta?.displayName &&
                meta.lore == EndOfFairytaleItemManager.createEndOfFairytale().itemMeta?.lore &&
                item.type == EndOfFairytaleItemManager.createEndOfFairytale().type
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (event.hand != EquipmentSlot.HAND) {
            return
        }

        if (isFairyTaleEnding(item) &&
            (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {

            event.isCancelled = true
            useFairySkill(player)
        }
    }

    private fun useFairySkill(player: Player) {
        val playerId = player.uniqueId
        val currentTime = System.currentTimeMillis()
        val cooldownTime = 30000 // 30초

        if (cooldowns.containsKey(playerId)) {
            val timeLeft = ((cooldowns[playerId]!! + cooldownTime) - currentTime) / 1000
            if (timeLeft > 0) {
                player.sendMessage("${ChatColor.RED}쿨타임이 ${timeLeft}초 남았습니다.")
                return
            }
        }

        if (player.totalExperience < 30) {
            player.sendMessage("${ChatColor.RED}경험치가 부족합니다!")
            return
        }

        player.giveExp(-30)
        cooldowns[playerId] = currentTime

        val isBuff = Random().nextBoolean()

        if (isBuff) {
            applyBuffEffects(player)
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.1f)
        } else {
            applyDebuffEffects(player)
            player.playSound(player.location, Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2.0f, 0.5f)
        }

        player.playSound(player.location, Sound.ENTITY_EVOKER_CAST_SPELL, 1.0f, 1.0f)
    }

    private fun applyBuffEffects(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 0, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 400, 1, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 400, 1, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 0, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 400, 1, false, true))
    }

    private fun applyDebuffEffects(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 400, 0, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.WEAKNESS, 400, 1, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 400, 0, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.POISON, 400, 0, false, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 0, false, true))
    }
}
