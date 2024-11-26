package org.hoshizora.christmasevent2024

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class DevCommandTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String>? {
        if (command.name.equals("dev", ignoreCase = true) && args.size == 1) {
            return listOf("SoulOverseer", "YoinoYoYoi", "OtherworldlyWanderer") // 자동 완성할 옵션 추가
        }
        return null // 기본값으로 null 반환
    }
}
