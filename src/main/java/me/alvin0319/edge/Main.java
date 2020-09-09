package me.alvin0319.edge;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import me.alvin0319.edge.edge.Edge;
import me.alvin0319.edge.edge.EdgeReplacer;
import me.alvin0319.edge.edge.SimpleEdgeReplacer;
import me.alvin0319.edge.task.InfoTask;

import java.util.HashMap;

public class Main extends PluginBase implements Listener{

	private static Main instance;

	private EdgeReplacer replacer;

	protected HashMap<String, Edge> map = new HashMap<>();

	@Override
	public void onLoad(){
		instance = this;
	}

	public static Main getInstance(){
		return instance;
	}

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		this.replacer = new SimpleEdgeReplacer();

		getServer().getScheduler().scheduleRepeatingTask(new InfoTask(), 20);
	}

	@Override
	public void onDisable(){
		this.map.forEach((key, value) -> {
			Player target = getServer().getPlayerExact(key);
			if(target != null){
				value.despawnTo();
			}
		});
		map.clear();
	}

	public EdgeReplacer getReplacer(){
		return replacer;
	}

	public void setReplacer(EdgeReplacer replacer){
		this.replacer = replacer;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();

		Edge edge = new Edge(player);

		this.map.put(player.getName(), edge);

		edge.spawnTo();
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();

		if(this.map.containsKey(player.getName())){
			this.map.remove(player.getName());
		}
	}

	public Edge getEdge(Player player) throws NullPointerException{
		return this.map.get(player.getName());
	}
}