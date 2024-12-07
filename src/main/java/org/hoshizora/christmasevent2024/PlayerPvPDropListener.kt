package org.hoshizora.christmasevent2024

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

class PlayerPvPDropListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val killer = event.entity.killer
        if (killer != null) {
            // 플레이어가 다른 플레이어에 의해 죽었을 때만 아이템 드롭
            dropChristmasItems(event)
        }
    }

    private fun dropChristmasItems(event: PlayerDeathEvent) {
        // 캐러멜 드롭 (1-4개 랜덤)
        val caramelCount = Random.nextInt(1, 5)
        repeat(caramelCount) {
            event.drops.add(CaramelItemManager.createCaramel())
        }

        // 보랏빛 얼음 드롭 (1-16개 랜덤)
        val purpleIceCount = Random.nextInt(1, 17)
        repeat(purpleIceCount) {
            event.drops.add(PurpleIceItemManager.createPurpleIce())
        }

        // 반사체 드롭 (5% 확률)
        if (Random.nextDouble() < 0.05) {
            event.drops.add(ReflectiveCoreItemManager.createReflectiveCore())
        }
    }
}