package org.hoshizora.christmasevent2024

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ExperienceManager(private val plugin: JavaPlugin) {

    private val savedExperience = mutableMapOf<Player, Int>()
    private val dataFile: File = File(plugin.dataFolder, "experience.yml")
    private lateinit var config: FileConfiguration

    init {
        if (!dataFile.exists()) {
            dataFile.createNewFile()
        }
        config = YamlConfiguration.loadConfiguration(dataFile)
        loadAllPlayersExperience()
    }

    // 모든 플레이어의 경험치 불러오기
    private fun loadAllPlayersExperience() {
        config.getKeys(false).forEach { playerName ->
            val experience = config.getInt(playerName)
            // 플레이어 객체를 찾기 위해 이름으로 검색
            val player = plugin.server.getPlayer(playerName)
            if (player != null) {
                savedExperience[player] = experience
            }
        }
    }

    // 경험치 저장
    fun saveExperience(player: Player) {
        // 현재 경험치를 저장
        savedExperience[player] = player.totalExperience
        // YAML 파일에 저장
        config.set(player.name, player.totalExperience)
        saveConfig()
    }

    // YAML 파일 저장
    private fun saveConfig() {
        try {
            config.save(dataFile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 경험치 불러오기
    fun loadExperience(player: Player): Int {
        return savedExperience.getOrDefault(player, 0) // 기본값 0
    }

    // 모든 플레이어 경험치 저장
    fun saveAllPlayers() {
        plugin.server.onlinePlayers.forEach { player ->
            saveExperience(player)
        }
    }

    // 특정 플레이어 경험치 복구
    fun restoreExperience(player: Player) {
        val experience = loadExperience(player)
        player.giveExp(experience)
    }
}
