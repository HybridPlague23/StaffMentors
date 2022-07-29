package me.hybridplague.staffmentors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MentorCommandHandler implements CommandExecutor {

	private StaffMentors main;
	public MentorCommandHandler(StaffMentors main) {
		this.main = main;
	}
	
	/*
	 * mentor follow
	 * mentor add <player>
	 * mentor remove <player>
	 * mentor [list]
	 */
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;

		if (!p.hasPermission("staffmentors.mentor.use")) {
			p.sendMessage(MentorUtils.getNoPerms());
			return true;
		}
		
		switch (args.length) {
		case 0:
			if (main.getConfig().getConfigurationSection("list").getKeys(false).isEmpty()) {
				p.sendMessage(MentorUtils.getnoMentors());
				return true;
			}
			main.mlh.mentorList(p);
			break;
		case 1:
			switch(args[0].toUpperCase()) {
			case "LIST":
				if (main.getConfig().getConfigurationSection("list").getKeys(false).isEmpty()) {
					p.sendMessage(MentorUtils.getnoMentors());
					return true;
				}
				main.mlh.mentorList(p);
				break;
			case "FOLLOW":
				if (main.getConfig().getConfigurationSection("list").getKeys(false).isEmpty()) {
					p.sendMessage(MentorUtils.getnoMentors());
					return true;
				}
				if (!MentorUtils.getHasMentor()) { // Run the loops
					main.getConfig().getConfigurationSection("list").getKeys(false).forEach(modID -> {
						if (!main.getConfig().getStringList("list." + modID).contains(p.getUniqueId().toString())) {
							MentorUtils.setHasMentor(false);
						} else {
							MentorUtils.setHasMentor(true);
						}
					});
				}
				
				// Check if value turned true
				if (MentorUtils.getHasMentor()) { // follow if true
					main.mfh.mentorFollow(p);
					return true;
				} else { // Send message if false
					p.sendMessage(MentorUtils.getnoFollow());
				}
				break;
			case "ADD", "REMOVE":
				if (!p.hasPermission("staffmentors.mentor.admin")) {
					p.sendMessage(MentorUtils.getNoPerms());
					return true;
				}
				p.sendMessage(MentorUtils.getMissingModArgument());
				break;
			default:
				p.sendMessage(MentorUtils.getinvalidArgument());
				break;
			}
			return true;
		case 2:
			switch(args[0].toUpperCase()) {
			case "LIST":
				if (main.getConfig().getConfigurationSection("list").getKeys(false).isEmpty()) {
					p.sendMessage(MentorUtils.getnoMentors());
					return true;
				}
				main.mlh.mentorList(p);
				break;
			case "FOLLOW":
				if (main.getConfig().getConfigurationSection("list").getKeys(false).isEmpty()) {
					p.sendMessage(MentorUtils.getnoMentors());
					return true;
				}
				if (!MentorUtils.getHasMentor()) { // Run the loops
					main.getConfig().getConfigurationSection("list").getKeys(false).forEach(modID -> {
						if (!main.getConfig().getStringList("list." + modID).contains(p.getUniqueId().toString())) {
							MentorUtils.setHasMentor(false);
						} else {
							MentorUtils.setHasMentor(true);
						}
					});
				}
				
				// Check if value turned true
				if (MentorUtils.getHasMentor()) { // follow if true
					main.mfh.mentorFollow(p);
					return true;
				} else { // Send message if false
					p.sendMessage(MentorUtils.getnoFollow());
				}
				break;
			case "ADD", "REMOVE":
				if (!p.hasPermission("staffmentors.mentor.admin")) {
					p.sendMessage(MentorUtils.getNoPerms());
					return true;
				}
				p.sendMessage(MentorUtils.getMissingHelperArgument());
				break;
			default:
				p.sendMessage(MentorUtils.getinvalidArgument());
				break;
			}
			return true;
		case 3:
			switch(args[0].toUpperCase()) {
			case "LIST":
				if (main.getConfig().getConfigurationSection("list").getKeys(false).isEmpty()) {
					p.sendMessage(MentorUtils.getnoMentors());
					return true;
				}
				main.mlh.mentorList(p);
				break;
			case "FOLLOW":
				if (main.getConfig().getConfigurationSection("list").getKeys(false).isEmpty()) {
					p.sendMessage(MentorUtils.getnoMentors());
					return true;
				}
				if (!MentorUtils.getHasMentor()) { // Run the loops
					main.getConfig().getConfigurationSection("list").getKeys(false).forEach(modID -> {
						if (!main.getConfig().getStringList("list." + modID).contains(p.getUniqueId().toString())) {
							MentorUtils.setHasMentor(false);
						} else {
							MentorUtils.setHasMentor(true);
						}
					});
				}
				
				// Check if value turned true
				if (MentorUtils.getHasMentor()) { // follow if true
					main.mfh.mentorFollow(p);
					return true;
				} else { // Send message if false
					p.sendMessage(MentorUtils.getnoFollow());
				}
				break;
			case "ADD":
				if (!p.hasPermission("staffmentors.mentor.admin")) {
					p.sendMessage(MentorUtils.getNoPerms());
					return true;
				}
				OfflinePlayer addMod = Bukkit.getOfflinePlayer(args[1]);
				OfflinePlayer addHelper = Bukkit.getOfflinePlayer(args[2]);
				
				List<String> modGroups = new ArrayList<String>();
				
				for (String g : main.perm.getPlayerGroups(null, addMod)) {
					modGroups.add(g);
				}
				
				List<String> helperGroups = new ArrayList<String>();
				
				for (String g : main.perm.getPlayerGroups(null, addHelper)) {
					helperGroups.add(g);
				}
				
				if (!modGroups.contains("SeniorAdministrator")
						&& !modGroups.contains("Administrator")
						&& !modGroups.contains("Moderator")
						&& !modGroups.contains("TrialModerator")) {
					p.sendMessage(MentorUtils.getNotMod(addMod.getName()));
					return true;
				}
				
				if (!helperGroups.contains("Helper")) {
					p.sendMessage(MentorUtils.getNotHelper(addHelper.getName()));
					return true;
				}
				
				if (!addMod.hasPlayedBefore()) {
					p.sendMessage(MentorUtils.getnotAMod());
					return true;
				}
				if (!addHelper.hasPlayedBefore()) {
					p.sendMessage(MentorUtils.getnotAHelper());
					return true;
				}
				
				if (addMod.getName().equalsIgnoreCase(addHelper.getName())) {
					p.sendMessage(MentorUtils.getCannotMentorSelf());
					return true;
				}
				
				UUID addModID = addMod.getUniqueId();
				UUID addHelperID = addHelper.getUniqueId();
				
				/*
				 * loop through lists to see if helper is already being mentored
				 */
				main.getConfig().getConfigurationSection("list").getKeys(false).forEach(modID -> {
					if (MentorUtils.getHasMentor() == false) {
						if (main.getConfig().getStringList("list." + modID).contains(addHelperID.toString())) {
							MentorUtils.setHasMentor(true);
							p.sendMessage(MentorUtils.getAlreadyHasMentor(addHelper.getName()));
							return;
						}
					}
				});
				if (MentorUtils.getHasMentor()) {
					MentorUtils.setHasMentor(false);
					return true;
				}
				List<String> addList = main.getConfig().getStringList("list." + addModID.toString());
				addList.add(addHelperID.toString());
				main.getConfig().set("list." + addModID.toString(), addList);
				main.saveConfig();
				
				p.sendMessage(MentorUtils.getMentorSet(addMod.getName(), addHelper.getName()));
				break;
			case "REMOVE":
				if (!p.hasPermission("staffmentors.mentor.admin")) {
					p.sendMessage(MentorUtils.getNoPerms());
					return true;
				}
				OfflinePlayer removeMod = Bukkit.getOfflinePlayer(args[1]);
				OfflinePlayer removeHelper = Bukkit.getOfflinePlayer(args[2]);
				
				if (!removeMod.hasPlayedBefore()) {
					p.sendMessage(MentorUtils.getnotAMod());
					return true;
				}
				if (!removeHelper.hasPlayedBefore()) {
					p.sendMessage(MentorUtils.getnotAHelper());
					return true;
				}

				UUID removeModID = removeMod.getUniqueId();
				UUID removeHelperID = removeHelper.getUniqueId();
				
				if (!main.getConfig().getStringList("list." + removeModID.toString()).contains(removeHelperID.toString())) {
					p.sendMessage(MentorUtils.getIsNotMentoring(removeMod.getName(), removeHelper.getName()));
					return true;
				}
				
				List<String> removeList = main.getConfig().getStringList("list." + removeModID.toString());
				
				removeList.remove(removeHelperID.toString());
				main.getConfig().set("list." + removeModID.toString(), removeList);
				if (main.getConfig().getStringList("list." + removeModID.toString()).isEmpty())
					main.getConfig().set("list." + removeModID.toString(), null);
				main.saveConfig();
				
				p.sendMessage(MentorUtils.getMentorRemove(removeMod.getName(), removeHelper.getName()));
				break;
			default:
				p.sendMessage(MentorUtils.getinvalidArgument());
				break;
			}
			return true;
		default:
			p.sendMessage(MentorUtils.getinvalidArgument());
			break;
		}
		
		MentorUtils.setHasMentor(false);
		
		return true;
	}

	
	
	
}
