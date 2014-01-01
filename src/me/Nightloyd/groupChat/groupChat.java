package me.Nightloyd.groupChat;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class groupChat extends JavaPlugin{
	ArrayList<String> groupChat = new ArrayList<>();
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
		
		if(commandLabel.equalsIgnoreCase("grpPm")){
			int i = 0;
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
			}
		}
		
		else if(commandLabel.equalsIgnoreCase("grpAdd")){
			if(!groupChat.contains(args[0])){
				groupChat.add(args[0]);
				player.sendMessage(ChatColor.DARK_GREEN + args[0] + " is now a member of the Admin Channel");
			}
			else
				player.sendMessage(ChatColor.DARK_GREEN + args[0] + " is already a member!");
		}
		
		else if(commandLabel.equalsIgnoreCase("grpList")){
			String msg = "";
			for(int i = 0; i < groupChat.size(); i++){
				msg += groupChat.get(i) + ", ";
			}
			player.sendMessage(ChatColor.DARK_GREEN + "Admin Channel Members: " + msg);
		}
		
		else if(commandLabel.equalsIgnoreCase("grpRemove")){
			if(groupChat.contains(args[0])){
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
				player.sendMessage(ChatColor.DARK_GREEN + args[0] + " isn't a member of the Admin Channel");
		}
		
		return false;
	}
}
