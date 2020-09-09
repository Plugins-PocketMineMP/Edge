package me.alvin0319.edge.edge;

import cn.nukkit.Player;
import cn.nukkit.Server;
import me.onebone.economyapi.EconomyAPI;

public class SimpleEdgeReplacer implements EdgeReplacer{

	public String getInfo(Player player){
		String str = "";
		str += "§d<§fINFO§d>§r\n";
		str += "§fName: §d" + player.getName() + "§r\n";
		str += "§fPlayers: §d" + Server.getInstance().getOnlinePlayers().size() + "§r\n";
		if(Server.getInstance().getPluginManager().getPlugin("EconomyAPI") != null){
			if(EconomyAPI.getInstance().hasAccount(player)){
				str += "§fMoney: §d" + EconomyAPI.getInstance().myMoney(player);
			}
		}
		return str;
	}
}
