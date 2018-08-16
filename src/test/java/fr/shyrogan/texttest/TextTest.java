package fr.shyrogan.texttest;

import fr.shyrogan.utilities.text.Text;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Sébastien (Shyrogan)
 */
public final class TextTest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().spigot().sendMessage(Text.of("Woooohooo").color(ChatColor.RED).create());
    }

}
