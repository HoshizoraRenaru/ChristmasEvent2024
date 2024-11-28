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

object MirroredChestplateItemManager {
    fun create(): ItemStack {
        val chestplate = ItemStack(Material.LEATHER_CHESTPLATE)
        val meta = chestplate.itemMeta as LeatherArmorMeta

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l&kL &r&1｡&f✧&1:&f˚&1*:&f･｡ &9&l거울상 &f&l흉갑 &r&1｡･&f:&1*&f˚&1:✧&f｡ &d&l&kL&f"))
        meta.lore = listOf(
            "",
            "${ChatColor.GOLD}효과:",
            "${ChatColor.AQUA}적의 True Damage를 1 감소"
        )
        meta.setColor(Color.fromRGB(79, 42, 201))

        // 방어력 설정 (네더라이트 흉갑과 동일)
        val armorModifier = AttributeModifier(UUID.randomUUID(), "generic.armor", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier)

        // 방어력 강도 설정
        val toughnessModifier = AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier)

        // 넉백 저항 설정
        val knockbackResistanceModifier = AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST)
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceModifier)

        chestplate.itemMeta = meta

        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 4)
        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10)
        chestplate.addUnsafeEnchantment(Enchantment.MENDING, 1)

        return chestplate
    }
}