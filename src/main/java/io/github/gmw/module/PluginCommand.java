package io.github.gmw.module;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import io.github.gmw.MeaMailPlusCore;
import io.github.gmw.config.MeaMailConfig;
import io.github.gmw.config.MeaMailTemplate;
import io.github.gmw.utils.MailCore;

import java.util.*;

@Command(label = "meamail", usage = "meamail help",
        description = "MeaMailPlusCore command", aliases = {"mmail"}, permission = "meo.mail")

public class PluginCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        switch (args.size()) {
            default -> // *No args*
                    showHelpList(sender);
            case 1 -> {
                switch (args.get(0)) {
                    case "reload" -> {
                        MeaMailPlusCore.getInstance().reloadConfig();
                        if (sender == null)
                            Grasscutter.getLogger().info("[MeaNoticeCore] Reloaded!");
                        else
                            CommandHandler.sendMessage(sender, "MeaNoticeCore config reloaded");
                    }
                    case "help" -> showHelpList(sender);
                    default -> CommandHandler.sendMessage(sender, "Invalid args.");
                }
            }
            case 2 -> {
                int templateId = Integer.parseInt(args.get(1));
                MeaMailTemplate template = MeaMailPlusCore.getInstance().config.getTemplate(templateId);


                switch (args.get(0)) {
                    case "sendall" -> {
                        if (template == null) {
                            CommandHandler.sendMessage(sender, "Invalid template id.");
                            return;
                        }

                        MailCore.sendMailToAllPlayers(template);
                        CommandHandler.sendMessage(sender, "Mail Sended!");
                    }
                    default -> CommandHandler.sendMessage(sender, "Invalid args.");
                }
            }
            case 3 -> {
                int templateId = Integer.parseInt(args.get(1));
                MeaMailTemplate template = MeaMailPlusCore.getInstance().config.getTemplate(templateId);

                switch (args.get(0)) {
                    case "send" -> {
                        if (template == null) {
                            CommandHandler.sendMessage(sender, "Invalid template id.");
                            return;
                        }

                        int uid = Integer.parseInt(args.get(2));
                        CommandHandler.sendMessage(sender, "Mail Sending...");
                        Grasscutter.getGameServer().getPlayers().forEach((index, player) -> {
                            if (player.getUid() == uid) {
                                MailCore.sendMailToPlayer(player, template);
                            }
                        });
                    }
                    case "sendall" -> {
                        if (template == null) {
                            CommandHandler.sendMessage(sender, "Invalid template id.");
                            return;
                        }

                        int minLevel = Integer.parseInt(args.get(2));
                        MailCore.sendMailToAllPlayers(template, minLevel);
                        CommandHandler.sendMessage(sender, "Mail Sended!");
                    }
                    case "sendallonline" -> {
                        if (template == null) {
                            CommandHandler.sendMessage(sender, "Invalid template id.");
                            return;
                        }

                        int minLevel = Integer.parseInt(args.get(2));
                        MailCore.sendMailToAllPlayers(template, minLevel, true);
                        CommandHandler.sendMessage(sender, "Mail Sended!");
                    }
                    default -> CommandHandler.sendMessage(sender, "Invalid args.");
                }
            }
        }
    }

    public void showHelpList(Player sender) {
        String[] helpMap = new String[]{
                "Send Mail:",
                "    /meamail send <templateId> <uid>",
                "    /meamail sendall <templateId> <minLevel>",
                "    /meamail sendallonline <templateId> <minLevel>",
//                "    /meamail welcomemail <uid>",
//                "    /meamail dailymail <uid>",
//                "    /meamail initialmail <uid>",
                "Other:",
                "    /meamail reload",
                "    /meamail help",
        };

        for (String text : helpMap) {
            CommandHandler.sendMessage(sender, text);
        }
    }
}

