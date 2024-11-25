package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DevCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("dev", ignoreCase = true)) {
            if (sender is Player) {
                if (args.isNotEmpty() && args[0].equals("YoinoYoYoi", ignoreCase = true)) {
                    sender.inventory.addItem(YoinoYoYoiItemManager.createYoinoYoYoi())
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &d&k&lL&r&d🌸🍀 &c&l宵の&4&l余、&e&l良い！ &r&d🍀🌸&d&k&lL [Christmas 2024] has been given."))
                } else {
                    sender.inventory.addItem(ItemManager.createSoulOverseer())
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &f&k&lL &f&l얼어붙은 &b&l영혼 &9&l지휘봉 &f&k&lL&r&f [Christmas 2024] has been given."))
                }
            } else {
                sender.sendMessage("Only players can use this command.")
            }
            return true
        }
        return false
    }
}