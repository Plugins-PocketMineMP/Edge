package me.alvin0319.edge.task;

import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import me.alvin0319.edge.Main;
import me.alvin0319.edge.edge.Edge;

public class InfoTask extends Task{

	@Override
	public void onRun(int i){
		Server.getInstance().getOnlinePlayers().forEach((key, value) -> {
			try{
				Edge edge = Main.getInstance().getEdge(value);
				edge.despawnTo();
				edge.spawnTo();
			}catch(NullPointerException e){
			}
		});
	}
}
