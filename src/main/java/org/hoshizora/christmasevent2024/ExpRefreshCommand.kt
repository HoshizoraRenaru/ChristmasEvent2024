package org.hoshizora.christmasevent2024

import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ExpRefreshCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player: Player = sender

            // 현재 레벨, 진행도 기반 totalExperience 계산
            val totalExperience = calculateTotalExperience(player.level, player.exp)

            player.totalExperience = 0
            player.exp = 0f
            player.level = 0

            player.giveExp(totalExperience)

            player.sendMessage("§a경험치가 새로고침 되었습니다.")
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
        } else {
            sender.sendMessage("§c이 명령어는 플레이어만 사용할 수 있습니다.")
        }
        return true
    }

    // 누적 경험치 계산 함수
    private fun calculateTotalExperience(level: Int, progress: Float): Int {
        var totalExperience = 0

        // 레벨별 경험치 계산
        for (i in 0 until level) {
            totalExperience += when {
                i <= 15 -> 2 * i + 7
                i <= 30 -> 5 * i - 38
                else -> 9 * i - 158
            }
        }

        // 현재 레벨에서 진행 중인 경험치 추가
        val currentLevelExp = when {
            level <= 15 -> 2 * level + 7
            level <= 30 -> 5 * level - 38
            else -> 9 * level - 158
        }

        totalExperience += (currentLevelExp * progress).toInt()
        return totalExperience
    }
}
