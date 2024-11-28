package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.hoshizora.christmasevent2024.mirroredarmor.*

class DevCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("dev", ignoreCase = true)) {
            if (sender is Player) {
                if (args.isNotEmpty()) {
                    when {
                        args[0].equals("YoinoYoYoi", ignoreCase = true) -> {
                            sender.inventory.addItem(YoinoYoYoiItemManager.createYoinoYoYoi())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &d&k&lL&r&d🌸🍀 &c&l宵の&4&l余、&e&l良い！ &r&d🍀🌸&d&k&lL &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("SpiritsOverseer", ignoreCase = true) -> {
                            sender.inventory.addItem(SpiritsOverseerItemManager.createSoulOverseer())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &f&k&lL &f&l얼어붙은 &b&l정령 &9&l감독관 &f&k&lL&r&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("OtherworldlyWanderer", ignoreCase = true) -> {
                            sender.inventory.addItem(OtherworldlyWandererItemManager.createOtherworldlyWanderer())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5&l&kL &r&f➶&9-͙&f˚&9 ༘&f✶ &1&l이계의 &f&l방랑자 &r&9｡･:*&f˚&9:✧&f｡ &5&l&kL &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("MirroredChristmasDestroyer", ignoreCase = true) -> {
                            sender.inventory.addItem(MirroredChristmasDestroyerItemManager.createMirroredChristmasDestroyer())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5&l&kL &r&f➶&9-͙&f˚&9 ༘&f✶ &1&l반사된 &d&l크&9&l리&d&l스&9&l마&d&l스 &d&l파괴자 &r&9｡･:*&f˚&9:✧&f｡ &5&l&kL &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("MirroredArmor", ignoreCase = true) -> {
                            sender.inventory.addItem(MirroredHelmetItemManager.create())
                            sender.inventory.addItem(MirroredChestplateItemManager.create())
                            sender.inventory.addItem(MirroredLeggingsItemManager.create())
                            sender.inventory.addItem(MirroredBootsItemManager.create())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &d&l&kL &r&f➶&1-͙&f˚&1 ༘&f✶ &9&l거울상 &f&l갑옷 &r&1｡･:*&f˚&1:✧&f｡ &d&l&kL&f &r&f[Christmas 2024] has been given."))
                        }
                        else -> {
                            sender.sendMessage("${ChatColor.RED}Invalid argument!")
                        }
                    }
                } else {
                    sender.sendMessage("Please provide an item name.")
                }
            } else {
                sender.sendMessage("Only players can use this command.")
            }
            return true
        }
        return false
    }
}
