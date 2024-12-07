package org.hoshizora.christmasevent2024

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerToggleFlightEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class PlayerInCombatListener(private val plugin: JavaPlugin) : Listener {

    private val disabledPlayers = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        if (event.entity is Player && event.damager is Player) {
            val player = event.entity as Player
            disabledPlayers[player.uniqueId] = System.currentTimeMillis() + 30000
            player.sendMessage("§c전투 상태에 진입했습니다.")

            object : BukkitRunnable() {
                override fun run() {
                    if (System.currentTimeMillis() >= disabledPlayers[player.uniqueId] ?: 0) {
                        disabledPlayers.remove(player.uniqueId)
                        player.sendMessage("§a전투 상태가 해제되었습니다.")
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 20L, 20L)
        }
    }

    @EventHandler
    fun onPlayerToggleFlight(event: PlayerToggleFlightEvent) {
        checkAndDisableFlight(event.player, event)
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        if (event.player.isGliding) {
            checkAndDisableFlight(event.player, event)
        }
    }

    private fun checkAndDisableFlight(player: Player, event: org.bukkit.event.Cancellable) {
        if (disabledPlayers.containsKey(player.uniqueId)) {
            if (System.currentTimeMillis() < disabledPlayers[player.uniqueId]!!) {
                event.isCancelled = true
                player.isGliding = false
                player.allowFlight = false
                player.sendMessage("§c전투 중에는 겉날개를 사용할 수 없습니다!")
            } else {
                disabledPlayers.remove(player.uniqueId)
            }
        }
    }
}