package org.hoshizora.christmasevent2024

import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Wither
import org.bukkit.plugin.java.JavaPlugin

class SuiseiNightmareMobManager(private val plugin: JavaPlugin) {
    fun summonBoss(location: Location) {
        val boss = location.world?.spawnEntity(location, EntityType.WITHER) as? Wither ?: return
        boss.apply {
            customName = "Nightmare"
            isCustomNameVisible = true
            maxHealth = 2000.0
            health = 2000.0
        }
        updateBossBar(boss)
    }

    fun updateBossBar(boss: Wither) {
        val displayHealth = boss.health / 10 // 2000 -> 200M
        val healthDisplay = String.format("%.1fM", displayHealth)
        boss.customName = "Nightmare - $healthDisplay"
    }
}
