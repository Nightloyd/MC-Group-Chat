package me.Nightloyd.groupChat;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Channel {
	private String name;
	private ArrayList<String> members = new ArrayList<>();
	private int memberCount = 0;
	
	public Channel(String name, String creator){
		this.name = name;
		members.add(0, creator);
		memberCount++;
	}
	
	public boolean addChMember(String name){
		if(!members.contains(name)){
			members.add(name);
			memberCount++;
			return true;
		}
		else
			return false;
	}
	
	public boolean rmChMember(String name){
		if(members.contains(name)){
			members.remove(name);
			memberCount--;
			return true;
		}
		else
			return false;
	}
	
	public int getMemberCount(){
		return memberCount;
	}
	
	public boolean searchForMember(String name){
		if(members.contains(name))
			return true;
		else
			return false;
	}
	
	public void printMemberList(Player player){
		String msg = "";
		for(int i = 0; i < members.size(); i++){
			msg += members.get(i) + ", ";
		}
		player.sendMessage(ChatColor.DARK_GREEN + "Admin Channel Members: " + msg);
	}
	
	public String getChName(){
		return name;
	}
	
	public void chSendMsg(Player player, String msg){
		int i = 0;
		while(i < members.size()){
			if(player.getServer().getPlayer(members.get(i)) != null){
				Player targetPlayer = player.getServer().getPlayerExact(members.get(i));
				targetPlayer.sendMessage(ChatColor.DARK_GREEN + name + ": " + player.getName() + ": " + msg);
			}
			i++;
		}
	}
}
