package io.github.gmw;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.server.game.GameServerPacketHandler;
import io.github.gmw.module.ConfigParser;
import io.github.gmw.module.PluginCommand;
import io.github.gmw.packetHandler.PlayerBornHook;
import io.github.gmw.packetHandler.PlayerJoinHook;
import io.github.gmw.module.TaskManager;

public class MeaMailPlusCore extends Plugin {

    private static MeaMailPlusCore instance;
    public ConfigParser config;
    public TaskManager task;


    @Override
    public void onLoad() {
        this.logger("Loading...");
        instance = this;

        this.config = new ConfigParser();
        this.config.loadConfig();
        this.config.loadTemplates();
        this.task = new TaskManager();

        this.logger("Loaded!");
    }

    @Override
    public void onEnable() {
        CommandMap.getInstance().registerCommand("meamail", new PluginCommand());

        // register packet handler
        GameServerPacketHandler packetHandler = Grasscutter.getGameServer().getPacketHandler();
        packetHandler.registerPacketHandler(PlayerJoinHook.class);
        packetHandler.registerPacketHandler(PlayerBornHook.class);

        this.task.enable();
    }

    @Override
    public void onDisable() {
        CommandMap.getInstance().unregisterCommand("meamail");
        this.task.disable();
    }

    public static MeaMailPlusCore getInstance() {
        return instance;
    }

    public void reloadConfig() {
        this.task.disable();
        this.config.loadConfig();
        this.config.loadTemplates();
        this.task.enable();
    }

    public void logger(String message) {
        this.logger(message, "info");
    }

    public void logger(String message, String type) {
        if (Grasscutter.getLogger() == null) return;

        String msg = "[MeaMailPlusCore] " + message;
        switch (type) {
            case "info" -> Grasscutter.getLogger().info(msg);
            case "warn" -> Grasscutter.getLogger().warn(msg);
            case "error" -> Grasscutter.getLogger().error(msg);
        }
    }
}
