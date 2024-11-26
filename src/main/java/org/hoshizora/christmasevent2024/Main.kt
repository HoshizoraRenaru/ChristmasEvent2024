package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), CommandExecutor {

    private lateinit var experienceManager: ExperienceManager

    override fun onEnable() {
        logger.info("星空れなる｜HoshizoraRenaru EternaL Plugin")
        logger.info("Welcome to 2024 Christmas!")

        server.pluginManager.registerEvents(SoulOverseerListener(this), this)
        server.pluginManager.registerEvents(YoinoYoYoiListener(this), this)
        server.pluginManager.registerEvents(OtherworldlyWandererListener(this), this)
        experienceManager = ExperienceManager(this)
        server.pluginManager.registerEvents(ExperienceEventListener(experienceManager), this)
        server.pluginManager.registerEvents(OtherworldlyWandererListener(this), this)

        server.scheduler.runTask(this, Runnable {
            experienceManager.saveAllPlayers()
        })

        getCommand("dev")?.apply {
            setExecutor(DevCommand())
            tabCompleter = DevCommandTabCompleter()
        }
        getCommand("exprefresh")?.apply {
            setExecutor(ExpRefreshCommand())
            tabCompleter = ExpRefreshTabCompleter()
        }
    }

    override fun onDisable() {
        logger.info("Welcome to 2024 Christmas has gone!")
        experienceManager.saveAllPlayers()
    }
}
