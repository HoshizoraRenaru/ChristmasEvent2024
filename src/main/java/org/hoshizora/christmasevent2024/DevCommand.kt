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
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &1&l이계의 &f&l방랑자 &r&9｡･&f:&9*&f˚&9:✧&f｡ &5&l&kL&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("MirroredChristmasDestroyer", ignoreCase = true) -> {
                            sender.inventory.addItem(ReflectedChristmasDestroyerItemManager.createMirroredChristmasDestroyer())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &1&l반사된 &d&l크&9&l리&d&l스&9&l마&d&l스 &d&l파괴자 &r&9｡･&f:&9*&f˚&9:✧&f｡ &5&l&kL&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("MirroredArmor", ignoreCase = true) -> {
                            sender.inventory.addItem(MirroredHelmetItemManager.create())
                            sender.inventory.addItem(MirroredChestplateItemManager.create())
                            sender.inventory.addItem(MirroredLeggingsItemManager.create())
                            sender.inventory.addItem(MirroredBootsItemManager.create())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &d&l&kL &r&f➶&1-͙&f˚&1 ༘&f✶ &9&l거울상 &f&l갑옷 &r&1｡･:*&f˚&1:✧&f｡ &d&l&kL&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("DarkDeerBow", ignoreCase = true) -> {
                            sender.inventory.addItem(DarkDeerBowItemManager.createDarkDeerBow())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &1&l검은 순록 &f&l활 &r&9｡･&f:&9*&f˚&9:✧&f｡ &5&l&kL&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("ReflectiveCore", ignoreCase = true) -> {
                            sender.inventory.addItem(ReflectiveCoreItemManager.createReflectiveCore())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &f&l&kL &r&1&l반${ChatColor.of("#1f1fde")}&l사&9&l체 &f&l&kL &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("SantasTradeSecret", ignoreCase = true) -> {
                            sender.inventory.addItem(SantasTradeSecretItemManager.createSantasTradeSecrets())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &d&l&kL &r&1｡&f✧&1:&f˚&1*:&f･｡ &9&l산타의 &5&l영업 &d&l기밀 &r&1｡･&f:&1*&f˚&1:✧&f｡ &d&l&kL&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("EndOfFairytale", ignoreCase = true) -> {
                            sender.inventory.addItem(EndOfFairytaleItemManager.createEndOfFairytale())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &f&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &1&l동화의 &5&l결말 &r&9｡･&f:&9*&f˚&9:✧&f｡ &f&l&kL&f &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("CaramelBox", ignoreCase = true) -> {
                            sender.inventory.addItem(CaramelBoxItemManager.createCaramelBox())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &5캐러멜 &6박스 &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("ReflectiveStone", ignoreCase = true) -> {
                            sender.inventory.addItem(ReflectiveStoneItemManager.createReflectiveStone())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &f&l&kL &d크&9리&d스&9마&d스&f의 빛나는 &r&1&l반${ChatColor.of("#1f1fde")}&l사&9&석 &f&l&kL &r&f[Christmas 2024] has been given."))
                        }
                        args[0].equals("ChristmasPvPWarpScroll", ignoreCase = true) -> {
                            sender.inventory.addItem(ChristmasPvPWarpScrollItemManager.createScroll())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "dev/ &d크&9리&d스&9마&d스 &r&fPvP &c&l워프 스크롤 &r&f[Christmas 2024] has been given."))
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
