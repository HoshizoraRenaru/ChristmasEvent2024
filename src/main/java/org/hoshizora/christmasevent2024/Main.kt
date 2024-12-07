package org.hoshizora.christmasevent2024

import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin
import org.hoshizora.christmasevent2024.mirroredarmor.MirroredArmorListener

class Main : JavaPlugin(), CommandExecutor {

    private lateinit var experienceManager: ExperienceManager

    override fun onEnable() {
        logger.info("星空れなる｜HoshizoraRenaru EternaL Plugin")
        logger.info("Welcome to 2024 Christmas!")

        server.pluginManager.registerEvents(SpiritsOverseerListener(this), this)
        server.pluginManager.registerEvents(YoinoYoYoiListener(this), this)
        server.pluginManager.registerEvents(OtherworldlyWandererListener(this), this)
        experienceManager = ExperienceManager(this)
        server.pluginManager.registerEvents(ExperienceEventListener(experienceManager), this)
        server.pluginManager.registerEvents(ReflectedChristmasDestroyerListener(this), this)
        server.pluginManager.registerEvents(MirroredArmorListener(this), this)
        server.pluginManager.registerEvents(DarkDeerBowListener(this), this)
        server.pluginManager.registerEvents(PurpleIceListener(this), this)
        server.pluginManager.registerEvents(SugarBlockListener(this), this)
        server.pluginManager.registerEvents(CaramelListener(this), this)
        server.pluginManager.registerEvents(SantasTradeSecretListener(this), this)
        server.pluginManager.registerEvents(EndOfFairytaleListener(this), this)
        server.pluginManager.registerEvents(SophisticatedAmethystListener(this), this)
        server.pluginManager.registerEvents(CaramelBoxListener(this), this)
        server.pluginManager.registerEvents(ReflectiveStoneListener(this), this)
        server.pluginManager.registerEvents(ChristmasPvPWarpScrollListener(this), this)
        server.pluginManager.registerEvents(PlayerPvPDropListener(this), this)
        server.pluginManager.registerEvents(PlayerInCombatListener(this), this)

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
        getCommand("checkexp")?.apply {
            setExecutor(CheckExpCommand())
        }

        PurpleIceRecipe(this).register()
        SugarBlockRecipe(this).register()
        CaramelRecipe(this).register()
        SophisticatedAmethystRecipe(this).register()
        FastGlassRecipe(this).register()
    }

    override fun onDisable() {
        logger.info("Welcome to 2024 Christmas has gone!")
        experienceManager.saveAllPlayers()
    }
}
