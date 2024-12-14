package org.hoshizora.christmasevent2024

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class DevCommandTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String>? {
        if (command.name.equals("dev", ignoreCase = true) && args.size == 1) {
            return listOf("SpiritsOverseer", "YoinoYoYoi", "OtherworldlyWanderer", "MirroredChristmasDestroyer", "MirroredArmor", "DarkDeerBow", "ReflectiveCore", "SantasTradeSecret", "EndOfFairytale", "CaramelBox", "ReflectiveStone", "ChristmasPvPWarpScroll", "ChristmasRandomBoxFragment", "ChristmasCoin", "Newton")
        }
        return null
    }
}
