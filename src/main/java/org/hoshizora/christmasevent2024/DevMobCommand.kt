package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class DevMobCommand(private val plugin: JavaPlugin) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("devmob", ignoreCase = true)) {
            if (sender is Player) {
                if (args.isNotEmpty()) {
                    when {
                        args[0].equals("SuiseiNightmare", ignoreCase = true) -> {
                            val mobManager = SuiseiNightmareMobManager(plugin)
                            mobManager.summonBoss(sender.location)
                            sender.sendMessage("${ChatColor.GREEN}SuiseiNightmare has been spawned at your location.")
                        }
                        else -> {
                            sender.sendMessage("${ChatColor.RED}Invalid argument!")
                        }
                    }
                } else {
                    sender.sendMessage("Please provide a mob name.")
                }
            } else {
                sender.sendMessage("Only players can use this command.")
            }
            return true
        }
        return false
    }
}
