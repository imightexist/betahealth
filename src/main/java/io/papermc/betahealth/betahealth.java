package io.papermc.betahealth;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.command.*;
import org.bukkit.boss.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;
import java.lang.*;
import org.bukkit.inventory.meta.*;
import net.kyori.adventure.text.*;
import org.bukkit.attribute.*;
import org.bukkit.event.block.*;
import org.bukkit.potion.*;

public class betahealth extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {
    // private ItemStack rawPork = new ItemStack(Material.PORKCHOP);
    // private ItemStack cookedPork = new ItemStack(Material.COOKED_PORKCHOP);
    private int constantHunger = 6;
    private boolean animateEating = false;
    private boolean healthySprinting = true;
    private Map<String, Integer> nutrition = new HashMap<String, Integer>();

    private static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {}
        }).start();
    }

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        nutrition.put("APPLE", 4);
        nutrition.put("BAKED_POTATO", 5);
        nutrition.put("BEETROOT", 1);
        nutrition.put("BEETROOT_SOUP", 6);
        nutrition.put("BREAD", 5);
        nutrition.put("CAKE", 2);
        nutrition.put("CARROT", 3);
        nutrition.put("CHORUS_FRUIT", 4);
        nutrition.put("COOKED_BEEF", 8);
        nutrition.put("COOKED_CHICKEN", 6);
        nutrition.put("COOKED_COD", 5);
        nutrition.put("COOKED_MUTTON", 6);
        nutrition.put("COOKED_PORKCHOP", 8);
        nutrition.put("COOKED_RABBIT", 5);
        nutrition.put("COOKED_SALMON", 6);
        nutrition.put("COOKIE", 2);
        nutrition.put("DRIED_KELP", 1);
        nutrition.put("ENCHANTED_GOLDEN_APPLE", 20);
        nutrition.put("GOLDEN_APPLE", 20);
        nutrition.put("GLOW_BERRIES", 2);
        nutrition.put("GOLDEN_CARROT", 6);
        nutrition.put("HONEY_BOTTLE", 6);
        nutrition.put("MELON_SLICE", 2);
        nutrition.put("MUSHROOM_STEW", 6);
        nutrition.put("POISONOUS_POTATO", 2);
        nutrition.put("POTATO", 1);
        nutrition.put("PUFFERFISH", 1);
        nutrition.put("PUMPKIN_PIE", 8);
        nutrition.put("RABBIT_STEW", 10);
        nutrition.put("BEEF", 3);
        nutrition.put("CHICKEN", 2);
        nutrition.put("COD", 2);
        nutrition.put("MUTTON", 2);
        nutrition.put("PORKCHOP", 3);
        nutrition.put("RABBIT", 3);
        nutrition.put("SALMON", 2);
        nutrition.put("ROTTEN_FLESH", 4);
        nutrition.put("SPIDER_EYE", 2);
        nutrition.put("SUSPICIOUS_STEW", 6);
        nutrition.put("SWEET_BERRIES", 2);
        nutrition.put("TROPICAL_FISH", 1);
    }

    /*
     * private ItemStack rawPork(){
     * ItemStack food = new ItemStack(Material.PORKCHOP);
     * ArrayList<Component> lore = new ArrayList<Component>();
     * lore.add(Component.text(ChatColor.COLOR_CHAR + Math.random()/* +
     * Math.random()));
     * food.lore(lore);
     * return food;
     * }
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent p) {
        Player player = p.getPlayer();
        player.setFoodLevel(constantHunger);
        player.sendMessage("The resource pack removes the hunger icon.");
        player.setResourcePack("https://www.dropbox.com/s/v9y2dzccexlu0tl/no%20hunger%201.16.5.zip?dl=1");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        setTimeout(() -> p.setFoodLevel(constantHunger),1500);
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        // detect if interacted cake
        if (event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().toString().equalsIgnoreCase("cake_block")) {
                p.setHealth(p.getHealth() + nutrition.get("CAKE"));
            }
        } else {
            if (event.getMaterial().isEdible() && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
                if (p.getInventory().getItemInMainHand().getType() == event.getMaterial() && p.getHealth() < 20) {
                    p.getInventory().setItemInMainHand(new ItemStack(event.getMaterial(), p.getInventory().getItemInMainHand().getAmount() - 1));
                }else if (p.getInventory().getItemInOffHand().getType() == event.getMaterial() && p.getHealth() < 20) {
                    p.getInventory().setItemInOffHand(new ItemStack(event.getMaterial(), p.getInventory().getItemInMainHand().getAmount() - 1));
                }
                p.setHealth(Math.min(20, p.getHealth() + nutrition.get(event.getMaterial().toString().toUpperCase())));
                if (event.getMaterial().toString().toUpperCase() == "CHORUS_FRUIT"){
                    p.teleport(new Location(p.getLocation().getWorld(),p.getLocation().getX()+Math.floor(Math.random() * 30)-15,p.getLocation().getY(),p.getLocation().getZ()+Math.floor(Math.random() * 30)-15));
                }else if (event.getMaterial().toString().toUpperCase() == "ENCHANTED_GOLDEN_APPLE"){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,120,3));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,300,3));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,120,3));
                }else if (event.getMaterial().toString().toUpperCase() == "HONEY_BOTTLE"){
                    p.removePotionEffect(PotionEffectType.POISON);
                }else if (event.getMaterial().toString().toUpperCase() == "POISONOUS_POTATO"){
                    int chance = (int)Math.floor(Math.random() * 100);
                    if (chance >= 60){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,5,0));
                    }
                }else if (event.getMaterial().toString().toUpperCase() == "POISONOUS_POTATO"){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,15,0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,60,1));
                }else if (event.getMaterial().toString().toUpperCase() == "CHICKEN"){
                    int chance = (int)Math.floor(Math.random() * 100);
                    if (chance >= 30){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,30,0));
                    }
                }else if (event.getMaterial().toString().toUpperCase() == "ROTTEN_FLESH"){
                    int chance = (int)Math.floor(Math.random() * 100);
                    if (chance >= 80){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,5,0));
                    }
                }else if (event.getMaterial().toString().toUpperCase() == "SPIDER_EYE"){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,5,0));
                }else if (event.getMaterial().toString().toUpperCase() == "SUSPICIOUS_STEW"){
                    int chance = (int)Math.floor(Math.random() * 8);
                    int chance2 = (int)Math.floor(Math.random() * 8)+3;
                    if (chance == 0){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,chance2,0));
                    }else if (chance == 1){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,chance2,0));
                    }else if (chance == 2){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,chance2,0));
                    }else if (chance == 3){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,chance2,0));
                    }else if (chance == 4){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,chance2,0));
                    }else if (chance == 5){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,chance2,0));
                    }else if (chance == 6){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,chance2,0));
                    }else if (chance == 7){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,chance2,0));
                    }
                }
                event.setCancelled(true);
            }
        }
        // p.sendMessage(event.getMaterial().toString());
        /*
         * if (p.getInventory().getItemInMainHand().getType() == Material.PORKCHOP) {
         * p.setHealth(p.getHealth() + 1.5);
         * p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
         * } else if (p.getInventory().getItemInOffHand().getType() ==
         * Material.PORKCHOP) {
         * p.setHealth(p.getHealth() + 1.5);
         * p.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
         * } else if (p.getInventory().getItemInMainHand().getType() ==
         * Material.COOKED_BEEF) {
         * p.setHealth(p.getHealth() + 4);
         * p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
         * } else if (p.getInventory().getItemInOffHand().getType() ==
         * Material.COOKED_BEEF) {
         * p.setHealth(p.getHealth() + 4);
         * p.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
         * }
         */
    }

    /*
     * @EventHandler
     * public void onEntityDeath(EntityDeathEvent e) {
     * if (e.getEntityType() == EntityType.PIG) {
     * e.getDrops().clear();
     * e.getDrops().add(rawPork());
     * }
     * }
     */

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        // not copied code!!!
        // e.setCancelled(true);
        Player p = (Player) e.getEntity();
        HumanEntity h = e.getEntity();
        // Entity h = e.getEntity();
        // Entity p = e.getEntity();
        // p.sendMessage(String.valueOf(e.getFoodLevel()));
        // p.setHealthScale(20);
        if (e.getFoodLevel() > constantHunger && animateEating) {
            /*
             * if (h.getHealth() + (e.getFoodLevel() - 6)/2 > 10){
             * h.setHealth(10);
             * //p.setHealthScale(10);
             * }else{
             * 
             * //p.setHealthScale(10);
             * }
             */
            h.setHealth(Math.min(20, h.getHealth() + (e.getFoodLevel() - constantHunger)));
            // p.sendMessage(String.valueOf(h.getHealth()));
        }
        if (h.getHealth() > 6) {
            e.setFoodLevel(constantHunger);
        } else if (healthySprinting) {
            e.setFoodLevel(6);
        } else {
            e.setFoodLevel(constantHunger);
        }
        // e.setCancelled(true);
    }

    // @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            /*
             * if (cmd.getName().equalsIgnoreCase("sprinting")) {
             * if (args[0] == "healthy") {
             * constantHunger = 7;
             * healthySprinting = true;
             * return true;
             * } else if (args[0] == "always") {
             * constantHunger = 7;
             * healthySprinting = false;
             * return true;
             * }else if (args[0] == "off") {
             * constantHunger = 6;
             * healthySprinting = false;
             * return true;
             * } else {
             * return false;
             * }
             * } else
             */if (cmd.getName().equalsIgnoreCase("chewing")) {
                if (args[0] == "true") {
                    animateEating = true;
                    return true;
                } else if (args[0] == "false") {
                    animateEating = false;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();

        /*
         * if (cmd.getName().equalsIgnoreCase("sprinting")) {
         * list.add("always");
         * list.add("healthy");
         * list.add("off");
         * return list;
         * } else
         */if (cmd.getName().equalsIgnoreCase("chewing")) {
            list.add("true");
            list.add("false");
            return list;
        } else {
            return null;
        }

    }
}
