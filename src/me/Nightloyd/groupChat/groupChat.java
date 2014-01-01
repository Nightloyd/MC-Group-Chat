package me.Nightloyd.groupChat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class groupChat extends JavaPlugin{
	ArrayList<String> groupChat = new ArrayList<>();
	HashMap<String, Channel> channelMap = new HashMap<String, Channel>();
	public final Logger logger = Logger.getLogger("Minecraft");
	public static groupChat plugin;
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
	} 
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled!");		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		
		if(commandLabel.equalsIgnoreCase("grpCreate")){
			if(args.length == 0)
				player.sendMessage(ChatColor.DARK_RED + "You need to specify a Channel name!");
			else{
				if(channelMap.get(args[0]) == null)
					channelMap.put(args[0], new Channel(args[0], player.getName()));
				else
					player.sendMessage(ChatColor.DARK_RED + "This group does already exist! Choose another name!");
			}
		}
		
		else if(commandLabel.equalsIgnoreCase("grpPm")){
			if(args.length == 0)
				player.sendMessage(ChatColor.DARK_RED + "You need to specify where and what you want to send!");
			else{
				if(channelMap.get(args[0]).searchForMember(player.getName())){
					String msg = "";
					for(int n = 1; n < args.length; n++){	// Puts all words in one string
						msg += args[n] + " ";
					}
					channelMap.get(args[0]).chSendMsg(player, msg);
				}
				else
					player.sendMessage(ChatColor.DARK_RED + "You are not a member of this group!");
			}
			/*int i = 0;
			String msg = "";
			for(int n = 0; n < args.length; n++){	// Puts all words in one string
				msg += args[n] + " ";
			}
			while(i < groupChat.size()){
				if(player.getServer().getPlayer(args[0]) != null){
					Player targetPlayer = player.getServer().getPlayerExact(groupChat.get(i));
					targetPlayer.sendMessage(ChatColor.DARK_GREEN + "AdminChannel: " + player.getName() + ": " + msg);
				}
				i++;
			}*/
		}
		
		else if(commandLabel.equalsIgnoreCase("grpAdd")){
			if(args.length == 0)
				player.sendMessage(ChatColor.DARK_RED + "You need to specify what group and who to add to it!");
			else{
				if(channelMap.get(args[0]) != null){
					if(channelMap.get(args[0]).searchForMember(player.getName())){
						if(channelMap.get(args[0]).addChMember(args[1]))			// True if the player has been added
							player.sendMessage(ChatColor.DARK_GREEN + args[1] + " has been added to the channel " + args[0]);
						else
							player.sendMessage(ChatColor.DARK_RED + args[1] + " is already a member of this group!");
					}
					else
						player.sendMessage(ChatColor.DARK_RED + "You are not a member of this group!");
				}
				else
					player.sendMessage(ChatColor.DARK_RED + "This group does not exist");
			}
			
			/*if(!groupChat.contains(args[0])){
				groupChat.add(args[0]);
				player.sendMessage(ChatColor.DARK_GREEN + args[0] + " is now a member of the Admin Channel");
			}
			else
				player.sendMessage(ChatColor.DARK_GREEN + args[0] + " is already a member!");*/
		}
		
		else if(commandLabel.equalsIgnoreCase("grpList")){
			if(args.length == 0)
				player.sendMessage(ChatColor.DARK_RED + "You need to specify which group to print the members list from");
			else
				channelMap.get(args[0]).printMemberList(player);
			/*String msg = "";
			for(int i = 0; i < groupChat.size(); i++){
				msg += groupChat.get(i) + ", ";
			}
			player.sendMessage(ChatColor.DARK_GREEN + "Admin Channel Members: " + msg);*/
		}
		
		else if(commandLabel.equalsIgnoreCase("grpRemove")){
			if(args.length == 0)
				player.sendMessage(ChatColor.DARK_RED + "You need to specify which group and who to remove!");
			else{
				if(channelMap.get(args[0]).searchForMember(player.getName())){
					if(channelMap.get(args[0]).rmChMember(args[1])){
						player.sendMessage(ChatColor.DARK_GREEN + args[1] + " has been removed from the group!");
						if(channelMap.get(args[0]).getMemberCount() == 0)
							channelMap.remove(args[0]);
					}
					else
						player.sendMessage(ChatColor.DARK_RED + args[1] + " is not a member of this group, can't remove!");
				}
				else
					player.sendMessage(ChatColor.DARK_RED + "You are not a member of this group!");
			}
			
			/*if(groupChat.contains(args[0])){
				groupChat.remove(args[0]);
				Player targetPlayer = null;
				if(player.getServer().getPlayer(args[0]) != null){
					targetPlayer = player.getServer().getPlayerExact(args[0]);
					targetPlayer.sendMessage(ChatColor.DARK_GREEN + "You have been kicked from the Admin Channel!");
				}
				int i = 0;
				while(i < groupChat.size()){
					targetPlayer = player.getServer().getPlayerExact(groupChat.get(i));
					targetPlayer.sendMessage(ChatColor.DARK_GREEN + args[0] + " has been kicked from the Admin Channel");
					i++;
				}
			}
			else
				player.sendMessage(ChatColor.DARK_GREEN + args[0] + " isn't a member of the Admin Channel");*/
		}
		
		return false;
	}
}
