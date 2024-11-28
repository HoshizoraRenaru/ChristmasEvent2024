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

object MirroredBootsItemManager {
    fun create(): ItemStack {
        val boots = ItemStack(Material.LEATHER_BOOTS)
        val meta = boots.itemMeta as LeatherArmorMeta

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l&kL &r&1｡&f✧&1:&f˚&1*:&f･｡ &9&l거울상 &f&l부츠 &r&1｡･&f:&1*&f˚&1:✧&f｡ &d&l&kL&f"))
        meta.lore = listOf(
            "",
            "${ChatColor.GOLD}효과:",
            "${ChatColor.AQUA}적의 True Damage를 1 감소"
        )
        meta.setColor(Color.fromRGB(174, 0, 255)) // 색상 설정

        // 방어력 설정 (네더라이트 부츠와 동일)
        val armorModifier = AttributeModifier(UUID.randomUUID(), "generic.armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier)

        // 방어력 강도 설정 (네더라이트 부츠와 동일)
        val toughnessModifier = AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier)

        // 넉백 저항 설정 (네더라이트 갑옷의 특성)
        val knockbackResistanceModifier = AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET)
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceModifier)

        boots.itemMeta = meta

        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10)
        boots.addUnsafeEnchantment(Enchantment.MENDING, 1)
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5)
        boots.addUnsafeEnchantment(Enchantment.SOUL_SPEED, 3)

        return boots
    }
}
