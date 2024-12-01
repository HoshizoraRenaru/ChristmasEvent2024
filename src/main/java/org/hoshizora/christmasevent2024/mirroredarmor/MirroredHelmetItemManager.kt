package org.hoshizora.christmasevent2024.mirroredarmor

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import net.md_5.bungee.api.ChatColor
import org.bukkit.enchantments.Enchantment
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlot
import java.util.UUID

object MirroredHelmetItemManager {
    fun create(): ItemStack {
        val helmet = ItemStack(Material.LEATHER_HELMET)
        val meta = helmet.itemMeta as LeatherArmorMeta

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l&kL &r&1｡&f✧&1:&f˚&1*:&f･｡ &9&l거울상 &f&l투구 &r&1｡･&f:&1*&f˚&1:✧&f｡ &d&l&kL&f"))
        meta.lore = listOf(
            "",
            "${ChatColor.GOLD}효과:",
            "${ChatColor.AQUA}적의 True Damage를 1 감소"
        )
        meta.setColor(Color.WHITE)

        val armorModifier = AttributeModifier(UUID.randomUUID(), "generic.armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier)

        val toughnessModifier = AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier)

        val knockbackResistanceModifier = AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD)
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceModifier)

        meta.isUnbreakable = true
        helmet.itemMeta = meta

        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10)
        helmet.addUnsafeEnchantment(Enchantment.MENDING, 1)
        helmet.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1)
        helmet.addUnsafeEnchantment(Enchantment.OXYGEN, 3)

        return helmet
    }
}