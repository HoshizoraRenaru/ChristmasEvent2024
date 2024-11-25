package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import net.md_5.bungee.api.ChatColor

object YoinoYoYoiItemManager {
    fun createYoinoYoYoi(): ItemStack {
        val sword = ItemStack(Material.NETHERITE_SWORD)
        val meta: ItemMeta? = sword.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&k&lL&r&d🌸🍀 &c&l宵の&4&l余、&e&l良い！ &r&d🍀🌸&d&k&lL"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.RED}${ChatColor.BOLD}宵の${ChatColor.DARK_RED}${ChatColor.BOLD}余、${ChatColor.YELLOW}${ChatColor.BOLD}良い！${ChatColor.RESET} ${ChatColor.YELLOW}${ChatColor.BOLD}RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}10${ChatColor.GRAY}경험치를 소모하여 ${ChatColor.GREEN}10초${ChatColor.GRAY}간 자신에게 ${ChatColor.RED}힘 2",
                "${ChatColor.GRAY}효과를 부여한다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}15초"
            )
            sword.itemMeta = it
        }

        sword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3)
        sword.addUnsafeEnchantment(Enchantment.DURABILITY, 5)
        sword.addUnsafeEnchantment(Enchantment.MENDING, 1)
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8)
        sword.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3)

        return sword
    }
}