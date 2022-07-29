package me.hybridplague.staffmentors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

public class MentorListHandler implements Listener {

	private StaffMentors main;
	public MentorListHandler(StaffMentors main) {
		this.main = main;
	}
	
	private static Inventory list;
	
	public void mentorList(Player player) {
		list = Bukkit.createInventory(null, 27, MentorUtils.format("&6Mentor List"));
		MentorUtils.inventory.add(list);
		player.openInventory(list);
		
		
		list.setItem(22, MentorUtils.createItem(Material.BARRIER, null, "&4Close"));
		
		main.getConfig().getConfigurationSection("list").getKeys(false).forEach(modID -> {
			
			OfflinePlayer mentor = Bukkit.getOfflinePlayer(UUID.fromString(modID));
			String name = mentor.getName();
			List<String> lore = new ArrayList<String>();
			String displayName = MentorUtils.format("&c" + name);
			
			lore.add("");
			for (String l : main.getConfig().getStringList("list." + modID)) {
				String loreName = Bukkit.getOfflinePlayer(UUID.fromString(l)).getName();
				lore.add(MentorUtils.format("&7" + loreName));
			}
			
			
			list.addItem(MentorUtils.createHead(name, lore, displayName));
			
		});
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (MentorUtils.inventory.contains(e.getInventory())) {
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem() == null) return;
			e.setCancelled(true);
			
			Material material = e.getCurrentItem().getType();
			ItemMeta meta = e.getCurrentItem().getItemMeta();
			String displayName = meta.getDisplayName();
			
			if (material == Material.BARRIER
					&& displayName.equals(MentorUtils.format("&4Close"))) {
				p.closeInventory();
				return;
			}
		}
	}
}
