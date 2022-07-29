package me.hybridplague.staffmentors;

import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

public class StaffMentors extends JavaPlugin {

	MentorFollowHandler mfh;
	MentorListHandler mlh;
	
	public Permission perm;
	
	@Override
	public void onEnable() {
		
		if (!setupPermission()) {
			System.out.println(ChatColor.RED + "You must have Vault installed.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		this.mfh = new MentorFollowHandler(this);
		this.mlh = new MentorListHandler(this);
		this.getCommand("mentor").setExecutor(new MentorCommandHandler(this));
		this.getServer().getPluginManager().registerEvents(new MentorListHandler(this), this);
		this.saveDefaultConfig();
	}
	
	@Override
	public void onDisable() {
	}
	
	public boolean setupPermission() {
		RegisteredServiceProvider<Permission> permissions = getServer().
				getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissions != null)
			perm = permissions.getProvider();
		return (perm != null);
	}
	
}
