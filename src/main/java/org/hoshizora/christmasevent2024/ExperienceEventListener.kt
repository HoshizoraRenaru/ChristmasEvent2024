package org.hoshizora.christmasevent2024

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.entity.Player
import org.bukkit.GameRule
import org.bukkit.ChatColor
import org.bukkit.event.player.PlayerRespawnEvent

class ExperienceEventListener(private val experienceManager: ExperienceManager) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val experience = experienceManager.loadExperience(player)
        player.giveExp(experience)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        experienceManager.saveExperience(player)
        player.sendMessage("${ChatColor.RED}퇴장 시 경험치가 저장되었습니다.")

        println("${player.name}의 경험치가 저장되었습니다: ${player.totalExperience}")
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity as? Player ?: return
        val keepInventoryEnabled = player.world.getGameRuleValue(GameRule.KEEP_INVENTORY) ?: false

        if (keepInventoryEnabled) {
            experienceManager.saveExperience(player)
            player.giveExp(-player.totalExperience)
        }
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val experience = experienceManager.loadExperience(player)
        player.giveExp(experience)
        player.sendMessage("${ChatColor.GREEN}현재 총 경험치: $experience")
    }
}
