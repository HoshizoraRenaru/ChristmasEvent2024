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
                if (args.isNotEmpty()) {
                    when {
                        args[0].equals("YoinoYoYoi", ignoreCase = true) -> {  // 대소문자 구분 없이 비교
                            sender.inventory.addItem(YoinoYoYoiItemManager.createYoinoYoYoi())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &d&k&lL&r&d🌸🍀 &c&l宵の&4&l余、&e&l良い！ &r&d🍀🌸&d&k&lL &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("souloverseer", ignoreCase = true) -> {
                            sender.inventory.addItem(ItemManager.createSoulOverseer())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &f&k&lL &f&l얼어붙은 &b&l영혼 &9&l지휘봉 &f&k&lL&r&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("otherworldlywanderer", ignoreCase = true) -> {
                            sender.inventory.addItem(OtherworldlyWandererItemManager.createOtherworldlyWanderer())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5&l&kL &r&f➶&9-͙&f˚&9 ༘&f✶ &1&l이계의 &f&l방랑자 &r&9｡･:*&f˚&9:✧&f｡ &5&l&kL &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("mirroredchristmasdestroyer", ignoreCase = true) -> { // 추가
                            sender.inventory.addItem(MirroredChristmasDestroyerItemManager.createMirroredChristmasDestroyer())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5&l&kL &r&f➶&9-͙&f˚&9 ༘&f✶ &1&l반사된 &d&l크&9&l리&d&l스&9&l마&d&l스 &d&l파괴자 &r&9｡･:*&f˚&9:✧&f｡ &5&l&kL &r&f[Christmas 2024] has been given."))
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
