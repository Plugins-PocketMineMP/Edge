package me.alvin0319.edge.edge;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.*;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import me.alvin0319.edge.Main;

import java.util.Objects;
import java.util.UUID;

final public class Edge{

	private Player player;

	private UUID uuid;

	private long entityRuntimeId;

	private EntityMetadata entityMetadata;

	public Edge(Player target){
		player = target;
		uuid = UUID.randomUUID();
		entityRuntimeId = Entity.entityCount++;
		entityMetadata = new EntityMetadata();

		setDataProperty(new FloatEntityData(38, (float) 0.001));

		setDataFlag(0, 16, true);
	}

	public void spawnTo(){
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();

		double newX = x + (-Math.sin((player.getYaw() + 33) / 180 * Math.PI) * Math.cos((player.getPitch() + 5) / 180 * Math.PI)) * 6.0;
		double newY = y + 3.2 + (-Math.sin(player.getPitch() / 180 * Math.PI)) * 8.0;
		double newZ = z + (Math.cos((player.getYaw() + 33) / 180 * Math.PI) * Math.cos((player.getPitch() + 5) / 180 * Math.PI)) * 6.0;

		AddPlayerPacket pk = new AddPlayerPacket();
		pk.entityRuntimeId = entityRuntimeId;
		pk.entityUniqueId = entityRuntimeId;

		pk.username = Main.getInstance().getReplacer().getInfo(player);
		pk.uuid = uuid;

		pk.metadata = entityMetadata;
		pk.x = (float) newX;
		pk.y = (float) newY;
		pk.z = (float) newZ;
		pk.item = Item.get(0);
		pk.yaw = 0.0F;
		pk.pitch = 0.0F;
		player.dataPacket(pk);
	}

	public void despawnTo(){
		RemoveEntityPacket pk = new RemoveEntityPacket();
		pk.eid = entityRuntimeId;
		player.dataPacket(pk);
	}

	public Player getPlayer(){
		return player;
	}

	public long getEntityRuntimeId(){
		return entityRuntimeId;
	}

	public UUID getUUID(){
		return uuid;
	}

	private void setDataFlag(int propertyId, int id){
		this.setDataFlag(propertyId, id, true);
	}

	private void setDataFlag(int propertyId, int id, boolean value){
		if(this.getDataFlag(propertyId, id) != value){
			if(propertyId == 26){
				byte flags = (byte) this.entityMetadata.getByte(propertyId);
				flags = (byte) (flags ^ 1 << id);
				this.setDataProperty(new ByteEntityData(propertyId, flags));
			}else{
				long flags = this.getDataProperties().getByte(propertyId);
				flags ^= 1L << id;
				this.setDataProperty(new LongEntityData(propertyId, flags));
			}
		}
	}

	private boolean getDataFlag(int propertyId, int id){
		return ((propertyId == 26 ? (long) (((byte) this.getDataProperties().getByte(propertyId)) & 255) : this.getDataProperties().getLong(propertyId)) & 1L << id) > 0L;
	}

	private boolean setDataProperty(EntityData data){
		if(!Objects.equals(data, this.getDataProperties().get(data.getId()))){
			this.getDataProperties().put(data);
			return true;
		}else{
			return false;
		}
	}

	private EntityMetadata getDataProperties(){
		return this.entityMetadata;
	}

}