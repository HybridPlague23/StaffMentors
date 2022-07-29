package me.hybridplague.staffmentors;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MentorUtils {

	
	public static List<Inventory> inventory = new ArrayList<Inventory>();
	
	public static String format(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	private static boolean hasMentor = false;
	
	private final static String noPERM = format("&cInsufficient Permissions.");
	private final static String missingModArg = format("&cMissing Argument: &7/mentor <ADD | REMOVE> &c<MODERATOR> <HELPER>");
	private final static String missingHelperArg = format("&cMissing Argument: &7/mentor <ADD | REMOVE> <MODERATOR> &c<HELPER>");
	private final static String invalidArg = format("&cInvalid Argument: &7/mentor <ADD | REMOVE | LIST | FOLLOW> [MODERATOR] [HELPER]");
	private final static String noMentors = format("&cThere are currently no mentors!");
	private final static String noFollow = format("&cYou have no mentor to follow!");
	private final static String invalidMod = format("&cModerator not found.");
	private final static String invalidHelper = format("&cHelper not found.");
	private final static String mentorIsOffline = format("&cYour mentor is currently offline.");
	private final static String cannotMentorSelf = format("&cPlayers cannot mentor theirself!");
	private static String notMod = "";
	private static String notHelper = "";
	private static String alreadyHasMentor = "";
	private static String isNotMentoring = "";
	private static String mentorSet = "";
	private static String mentorRemove = "";

	public static String getCannotMentorSelf() {
		return cannotMentorSelf;
	}
	
	public static String getNoPerms() {
		return noPERM;
	}
	
	public static String getMissingModArgument() {
		return missingModArg;
	}
	
	public static String getMissingHelperArgument() {
		return missingHelperArg;
	}
	
	public static String getinvalidArgument() {
		return invalidArg;
	}
	
	public static String getnoMentors() {
		return noMentors;
	}
	
	public static String getnoFollow() {
		return noFollow;
	}
	
	public static String getnotAMod() {
		return invalidMod;
	}
	
	public static String getnotAHelper() {
		return invalidHelper;
	}
	
	public static String getmentorIsOffline() {
		return mentorIsOffline;
	}
	
	public static ItemStack createItem(Material material, @Nullable List<String> lore, String displayName) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		if (lore != null) {
			List<String> l = lore;
			meta.setLore(l);
		}
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		
		return item;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createHead(String player, @Nullable List<String> lore, String displayName) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		meta.setOwner(player);
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static boolean getHasMentor() {
		return hasMentor;
	}

	public static void setHasMentor(boolean hasMentor) {
		MentorUtils.hasMentor = hasMentor;
	}
	
	public static String getMentorSet(String modName, String helperName) {
		String msg = format("&a" + modName + " has been set as the mentor for " + helperName + ".");
		
		MentorUtils.mentorSet = msg;
		return MentorUtils.mentorSet;
	}
	
	public static String getMentorRemove(String modName, String helperName) {
		String msg = format("&a" + modName + " has been removed as the mentor for " + helperName + ".");
		
		MentorUtils.mentorRemove = msg;
		return MentorUtils.mentorRemove;
	}
	
	public static String getAlreadyHasMentor(String helperName) {
		String msg = format("&a" + helperName + " already has a mentor.");
		
		MentorUtils.alreadyHasMentor = msg;
		return MentorUtils.alreadyHasMentor;
	}
	
	public static String getIsNotMentoring(String modName, String helperName) {
		String msg = format("&a" + modName + " is not mentoring " + helperName + ".");
		
		MentorUtils.isNotMentoring = msg;
		return MentorUtils.isNotMentoring;
	}
	
	public static String getNotMod(String name) {
		String msg = format("&a" + name + " is not an Administrator or Moderator.");
		
		MentorUtils.notMod = msg;
		return MentorUtils.notMod;
	}
	
	public static String getNotHelper(String name) {
		String msg = format("&a" + name + " is not a Helper.");
		
		MentorUtils.notHelper = msg;
		return MentorUtils.notHelper;
	}
	
	public static void sendFollowing(Player mod, Player helper) {
		mod.sendMessage(format("&a" + helper.getName() + " is following you!"));
		helper.sendMessage(format("&aYou are now following your mentor, " + mod.getName() + "!"));
	}
	
}
