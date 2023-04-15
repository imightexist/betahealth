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

public class betahealth extends JavaPlugin implements Listener, CommandExecutor {
    //private ItemStack rawPork = new ItemStack(Material.PORKCHOP);
    //private ItemStack cookedPork = new ItemStack(Material.COOKED_PORKCHOP);
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    /*private ItemStack rawPork(){
        ItemStack food = new ItemStack(Material.PORKCHOP);
        ArrayList<Component> lore = new ArrayList<Component>();
        lore.add(Component.text(ChatColor.COLOR_CHAR + Math.random()/* + Math.random()));
        food.lore(lore);
        return food;
    }*/
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent p) {
        Player player = p.getPlayer();
        player.setFoodLevel(6);
        player.sendMessage("The resource pack removes the hunger icon.");
        player.setResourcePack("https://www.dropbox.com/s/v9y2dzccexlu0tl/no%20hunger%201.16.5.zip?dl=1");
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        p.setFoodLevel(6);
    }

    /*@EventHandler @Nullable
    public void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (p.getInventory().getItemInMainHand().getType() == Material.PORKCHOP) {
            p.setHealth(p.getHealth() + 1.5);
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }else if (p.getInventory().getItemInOffHand().getType() == Material.PORKCHOP){
            p.setHealth(p.getHealth() + 1.5);
            p.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
        }else if (p.getInventory().getItemInMainHand().getType() == Material.COOKED_BEEF){
            p.setHealth(p.getHealth() + 4);
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }else if (p.getInventory().getItemInOffHand().getType() == Material.COOKED_BEEF){
            p.setHealth(p.getHealth() + 4);
            p.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
        }
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if (e.getEntityType() == EntityType.PIG){
            e.getDrops().clear();
            e.getDrops().add(rawPork());
        }
    }*/

    @EventHandler (priority=EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        // not copied code!!!
        //e.setCancelled(true);
        Player p = (Player) e.getEntity();
        HumanEntity h = e.getEntity();
        //Entity h = e.getEntity();
        //Entity p = e.getEntity();
        //p.sendMessage(String.valueOf(e.getFoodLevel()));
        //p.setHealthScale(20);
        if (e.getFoodLevel() > 6){
            /*if (h.getHealth() + (e.getFoodLevel() - 6)/2 > 10){
                h.setHealth(10);
                //p.setHealthScale(10);
            }else{
                
                //p.setHealthScale(10);
            }*/
            h.setHealth(Math.min(20,h.getHealth() + (e.getFoodLevel() - 6)/2));
            //p.sendMessage(String.valueOf(h.getHealth()));
        }
        e.setFoodLevel(6);
        //e.setCancelled(true);
    }
    // @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return true;
    }
}
