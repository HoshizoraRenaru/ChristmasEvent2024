package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Damageable
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.text.DecimalFormat
import java.util.*

class NewtonListener(private val plugin: JavaPlugin) : Listener {

    private val absorptionCooldowns = mutableMapOf<UUID, Long>()
    private val skillCooldowns = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (event.hand != EquipmentSlot.HAND) return

        if (isNewtonItem(item) &&
            (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {
            if (!canUseSkill(player)) return

            event.isCancelled = true
            useNewtonSkill(player)
        }
    }

    private fun isNewtonItem(item: ItemStack?): Boolean {
        if (item == null || !item.hasItemMeta()) return false
        val meta = item.itemMeta ?: return false
        return meta.displayName == NewtonItemManager.createNewton().itemMeta?.displayName &&
                meta.lore == NewtonItemManager.createNewton().itemMeta?.lore
    }

    private fun useNewtonSkill(player: Player) {
        teleportPlayer(player)
        if (canUseShield(player)) {
            applyAbsorptionShield(player)
        }
        createExplosion(player)

        skillCooldowns[player.uniqueId] = System.currentTimeMillis()
    }

    private fun canUseSkill(player: Player): Boolean {
        val lastUsedTime = skillCooldowns[player.uniqueId] ?: 0L
        val currentTime = System.currentTimeMillis()
        val cooldown = (0.15 * 1000).toLong()

        return currentTime - lastUsedTime >= cooldown
    }

    private fun teleportPlayer(player: Player) {
        val loc = player.location
        val direction = loc.direction.normalize()
        val targetLoc = loc.clone()
        var blocked = false

        for (i in 1..10) {
            targetLoc.add(direction)

            if (targetLoc.block.type.isAir || !targetLoc.block.type.isSolid) {
                continue
            }

            targetLoc.subtract(direction)
            blocked = true
            break
        }

        player.teleport(targetLoc)

        if (blocked) {
            player.sendMessage("${ChatColor.RED}There are blocks in the way!")
        }

        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.0f)
        player.world.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1.0f)

        player.world.spawnParticle(Particle.EXPLOSION_LARGE, loc, 3, 0.5, 0.5, 0.5, 0.0)
        player.world.spawnParticle(Particle.EXPLOSION_LARGE, targetLoc, 3, 0.5, 0.5, 0.5, 0.0)
    }

    private fun applyAbsorptionShield(player: Player) {
        val absorptionLevel = 3
        val durationTicks = 5 * 20 // 5s

        val absorptionEffect = PotionEffect(PotionEffectType.ABSORPTION, durationTicks, absorptionLevel, false, false)
        player.addPotionEffect(absorptionEffect)

        player.world.playSound(player.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 0.8f)

        spawnCircleParticles(player)

        absorptionCooldowns[player.uniqueId] = System.currentTimeMillis()

        object : BukkitRunnable() {
            override fun run() {
                if (player.isOnline && !player.isDead) {
                    val currentAbsorption = player.absorptionAmount
                    player.removePotionEffect(PotionEffectType.ABSORPTION)
                    val healingAmount = currentAbsorption * 0.5
                    val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
                    val newHealth = (player.health + healingAmount).coerceAtMost(maxHealth)
                    player.health = newHealth

                    player.world.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.7f)
                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        player.world.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f)
                    }, 1L)
                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        player.world.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f)
                    }, 2L)

                    player.world.spawnParticle(Particle.HEART, player.location.add(0.0, 2.0, 0.0), 2, 0.0, 0.0, 0.0)
                }
            }
        }.runTaskLater(plugin, durationTicks.toLong())
    }

    private fun spawnCircleParticles(player: Player) {
        val startColor = Color.fromRGB(247, 0, 127) // #f7007f
        val endColor = Color.fromRGB(28, 162, 255) // #1ca2ff

        val totalSteps = 50
        val radius = 0.3
        val particleCount = 30

        val direction = player.location.direction.normalize()
        val right = direction.clone().crossProduct(Vector(0.0, 1.0, 0.0)).normalize()
        val up = right.clone().crossProduct(direction).normalize()

        val headLocation = player.location.add(0.0, 1.6, 0.0)
        val offset = direction.clone().multiply(0.5)
        val baseLocation = headLocation.add(offset)

        for (i in 0 until particleCount) {
            val angle = 2.0 * Math.PI * i / particleCount
            val x = radius * Math.cos(angle)
            val z = radius * Math.sin(angle)

            val rotated = right.clone().multiply(x).add(up.clone().multiply(z))
            val particleLocation = baseLocation.clone().add(rotated)

            val t = i.toDouble() / totalSteps.coerceAtLeast(1)
            val r = interpolate(startColor.red, endColor.red, t)
            val g = interpolate(startColor.green, endColor.green, t)
            val b = interpolate(startColor.blue, endColor.blue, t)

            val color = Color.fromRGB(r, g, b)

            player.world.spawnParticle(
                Particle.REDSTONE,
                particleLocation,
                0,
                Particle.DustOptions(color, 0.5f)
            )
        }
    }

    private fun interpolate(start: Int, end: Int, t: Double): Int {
        return (start + (end - start) * t).toInt()
    }

    private fun createExplosion(player: Player) {
        val loc = player.location
        player.world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f)

        player.world.spawnParticle(
            Particle.EXPLOSION_LARGE,
            loc.x,
            loc.y + 1,
            loc.z,
            10, // particle count
            0.0, // X spread
            0.0, // Y spread
            0.0, // Z spread
            5.0  // particle size
        )

        val entities = player.getNearbyEntities(6.0, 6.0, 6.0)
            .filterIsInstance<Damageable>()
            .filter {
                it is Player && it != player ||
                        it !is Player && !listOf(
                    EntityType.ARMOR_STAND,
                    EntityType.ITEM_FRAME,
                    EntityType.MINECART,
                    EntityType.EXPERIENCE_ORB,
                    EntityType.DROPPED_ITEM,
                    EntityType.BOAT
                ).contains(it.type)
            }

        var totalDamage = 0.0

        for (entity in entities) {
            val initialHealth = entity.health

            val distance = loc.distance(entity.location)
            val damage = ((6 - distance) / 6 * (30 - 15) + 15).coerceIn(15.0, 30.0) // max damage 30

            entity.damage(damage, player)

            totalDamage += damage

            if (entity.health <= 0 && initialHealth > 0) {
                player.world.playSound(entity.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.5f)
            }
        }

        val df = DecimalFormat("#,###,###,###.#").apply { isGroupingUsed = true }
        val formattedDamage = df.format(totalDamage)

        if (totalDamage > 0.0) {
            player.sendMessage(
                "${ChatColor.GRAY}Your Implosion hit ${ChatColor.RED}${entities.size} " +
                        "${ChatColor.GRAY}enemies for ${ChatColor.RED}$formattedDamage ${ChatColor.GRAY}damage."
            )
        }
    }

    private fun canUseShield(player: Player): Boolean {
        val lastUsedTime = absorptionCooldowns[player.uniqueId] ?: 0L
        val currentTime = System.currentTimeMillis()
        val cooldown = 5000L // 5s cooldown

        return currentTime - lastUsedTime >= cooldown
    }
}
