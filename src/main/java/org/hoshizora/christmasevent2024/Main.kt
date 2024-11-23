package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), CommandExecutor {
    override fun onEnable() {
        logger.info("星空れなる｜HoshizoraRenaru EternaL Plugin")
        logger.info("Welcome to 2024 Christmas!")

        // 이벤트 리스너 등록
        server.pluginManager.registerEvents(SoulOverseerListener(this), this)

        // 커맨드 등록 및 Tab Completer 설정
        this.getCommand("dev")?.setExecutor(this)
        this.getCommand("dev")?.tabCompleter = CommandTabCompleter()
    }

    override fun onDisable() {
        logger.info("Welcome to 2024 Christmas has gone!")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("dev", ignoreCase = true)) {
            if (sender is Player) {
                // 플레이어에게 소울 오버시어 아이템 지급
                sender.inventory.addItem(ItemManager.createSoulOverseer())
                sender.sendMessage("dev/ SoulOverseer[Christmas 2024] has been given.")
            } else {
                sender.sendMessage("Only players can use this command.")
            }
            return true
        }
        return false
    }
}