package org.hoshizora.christmasevent2024

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CheckExpCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player = sender
            val currentExperience = player.totalExperience
            player.sendMessage("${ChatColor.GREEN}현재 총 경험치: $currentExperience")
        } else {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.")
        }
        return true
    }
}
