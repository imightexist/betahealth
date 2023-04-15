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

    @EventHandler (priority=EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        // not copied code!!!
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getFoodLevel() > 6){
                p.setHealth(p.getHealth() + p.getFoodLevel() - 6);
            }
            p.setFoodLevel(6);
        }
    }

    // @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return true;
    }
}
