package com.mcml.space.optimize;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.AzurePlayerList;

public class EntityClear implements Runnable{

	@Override
	public void run() {
		if(ConfigOptimize.EntityClearenable){
			int count = 0;
			List<World> worlds = Bukkit.getWorlds();
			List<LivingEntity> allents = new ArrayList<LivingEntity>();
			int ws = worlds.size();
			for(int i = 0;i<ws;i++){
				World world = worlds.get(i);
				List<LivingEntity> lents = world.getLivingEntities();
				int ls = lents.size();
				for(int ii = 0;ii<ls;ii++){
					LivingEntity le = lents.get(ii);
					if(ConfigOptimize.EntityClearClearEntityType.contains(le.getType().name())){
						allents.add(le);
						count = count + lents.size();
					}
				}
			}
			if(count > ConfigOptimize.EntityClearLimitCount){
				List<Player> players = AzurePlayerList.players();
				int ps = players.size();
				for(int i = 0;i<ps;i++){
					Player player = players.get(i);
					List<Entity> nents = player.getNearbyEntities(10, 10, 10);
					int ls = nents.size();
					for(int ii = 0;ii<ls;ii++){
						Entity le = nents.get(ii);
						if(allents.contains(le)){
							allents.remove(le);
						}
					}
				}
			}
			int aes = allents.size();
			AzureAPI.bc(ConfigOptimize.EntityClearClearMessage);
			for(int ii = 0;ii<aes;ii++){
				allents.get(ii).remove();
			}
		}
	}
}
