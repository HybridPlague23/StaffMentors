package me.hybridplague.staffmentors;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MentorFollowHandler {

	private StaffMentors main;
	public MentorFollowHandler(StaffMentors main) {
		this.main = main;
	}
	
	
	public void mentorFollow(Player player) {
		main.getConfig().getConfigurationSection("list").getKeys(false).forEach(modID -> {
			if (main.getConfig().getStringList("list." + modID).contains(player.getUniqueId().toString())) {
				if (!Bukkit.getOfflinePlayer(UUID.fromString(modID)).isOnline()) {
					player.sendMessage(MentorUtils.getmentorIsOffline());
					return;
				}
				
				Player mentor = Bukkit.getPlayer(UUID.fromString(modID));
				player.teleport(mentor);
				MentorUtils.sendFollowing(mentor, player);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), MentorUtils.format("a &o" + player.getName() + " is following their mentor, " + mentor.getName() + "."));
				return;
			}
		});
		
	}
	
}
