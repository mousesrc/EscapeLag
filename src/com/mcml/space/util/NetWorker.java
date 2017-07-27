package com.mcml.space.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mcml.space.config.Settings;
import com.mcml.space.core.VLagger;

/**
 * @author Vlvxingze
 */
public class NetWorker implements Runnable {

    public static void CheckAndDownloadPlugin() {
        if (Settings.AutoUpdate == true) {
            try {
                // 整体获取
                File NetworkerFile = new File(VLagger.MainThis.getDataFolder(), "URLLog");
                DowloadFile("http://bgm.mcml.space/VLagger/networker.yml", NetworkerFile);
                YamlConfiguration URLLog = YamlConfiguration.loadConfiguration(NetworkerFile);
                // 检查插件并下载新版本
                VLagger.MainThis.getLogger().info("正在检查新版本插件，请稍等...");
                int NewVersion = URLLog.getInt("UpdateVersion");
                int NowVersion = Integer.valueOf("%BUILD_NUMBER%");
                if (NewVersion > NowVersion) {
                    VLagger.MainThis.getLogger().info("插件检测到新版本 " + NewVersion + "，正在自动下载新版本插件...");
                    DowloadFile("http://bgm.mcml.space/VLagger/VLagger.jar", VLagger.PluginFile);
                    VLagger.MainThis.getLogger().info("插件更新版本下载完成！正在重启服务器！");
                    Bukkit.shutdown();
                } else {
                    VLagger.MainThis.getLogger().info("VLG插件工作良好，暂无新版本检测更新。");
                }
                // 完成提示
                VLagger.MainThis.getLogger().info("全部网络工作都读取完毕了...");
                NetworkerFile.delete();
            } catch (IOException ex) {
            }
        }
    }

    public static void DownloadAntiAttack() {
        if (Bukkit.getPluginManager().getPlugin("AntiAttack") != null) {
            Bukkit.broadcastMessage("§a§l[VLagger]§c错误！您的服务器已经安装了AntiAttack反压测模块！无需再次安装！");
            return;
        }
        try {
            File AntiAttackFile = new File(VLagger.MainThis.getDataFolder(), "AntiAttack.jar");
            DowloadFile("http://bgm.mcml.space/AntiAttack.jar", AntiAttackFile);
            Bukkit.broadcastMessage("§a§l[VLagger]§b成功下载了AntiAttack反压测插件，重启即可生效！");
        } catch (IOException ex) {
        }
    }

    public static void DowloadFile(String urlStr, File savefile) throws IOException {
        if (savefile.exists() == true) {
            savefile.delete();
        }
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3 * 1000); // 3秒超时，不知道这样行不行
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        InputStream inputStream = conn.getInputStream();
        byte[] getData = readInputStream(inputStream);
        File file = savefile;
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    @Override
    public void run() {
        CheckAndDownloadPlugin();
    }
}
