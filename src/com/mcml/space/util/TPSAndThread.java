package com.mcml.space.util;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;

import com.mcml.space.core.VLagger;

public class TPSAndThread implements Runnable{
	
	private static long ServerTime = System.currentTimeMillis();
	private static int ServerTick = 0;
	private static int LastTPS = 20;
	
	
	public TPSAndThread(){
		Bukkit.getScheduler().runTaskTimer(VLagger.MainThis, new Runnable(){
			public void run(){
				ServerTime = System.currentTimeMillis();
				ServerTick++;
			}
		}, 1, 1);
		Timer AsyncTickTimer = new Timer();
		AsyncTickTimer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				LastTPS = ServerTick;
				ServerTick = 0;
			}
		}, 50L, 50L);
	}
	
	public static int getTPS(){
		return LastTPS;
	}
	
	public void run(){
		if(System.currentTimeMillis() - ServerTime >= 1000){
			AzureAPI.log("警告！服务器主线程陷入停顿超过1秒！这可能是有其他插件进行网络操作、出现死循环或耗时操作所致！");
		}
	}

}
