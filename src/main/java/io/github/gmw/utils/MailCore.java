package io.github.gmw.utils;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.mail.Mail;
import io.github.gmw.MeaMailPlusCore;
import io.github.gmw.config.MeaMailConfig;
import io.github.gmw.config.MeaMailTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public final class MailCore {
    public static void saveMailToDatabase(Player player, MeaMailTemplate template) {
        saveMailToDatabase(player, template, 0);
    }

    public static void saveMailToDatabase(Player player, MeaMailTemplate template, int minLevel) {
        if (player.getLevel() < minLevel) return;

        Mail mail = templateToMail(template);
        mail.setOwnerUid(player.getUid());
        DatabaseHelper.saveMail(mail);
        MeaMailPlusCore.getInstance().logger("Mail sent to " + player.getUid() + "(Offline): " + template.title);
    }

    public static void sendMailToPlayer(int uid, MeaMailTemplate template, boolean onlineOnly) {
        AtomicBoolean mailSent = new AtomicBoolean(false);
        Grasscutter.getGameServer().getPlayers().forEach((index, player) -> {
            if (player.getUid() == uid) {
                mailSent.set(true);
                sendMailToPlayer(player, template);
            }
        });

        if(!onlineOnly && !mailSent.get()) {
            DatabaseHelper.getAllPlayers().forEach(player -> {
                if (player.getUid() == uid) {
                    saveMailToDatabase(player, template);
                }
            });
        }
    }

    public static void sendMailToPlayer(Player player, MeaMailTemplate template) {
        sendMailToPlayer(player, template, 0);
    }

    public static void sendMailToPlayer(Player player, int templateId) {
        sendMailToPlayer(player, templateId, 0);
    }

    public static void sendMailToPlayer(Player player, int templateId, int minLevel) {
        MeaMailTemplate template = getTemplateById(templateId);
        if(template == null) {
            MeaMailPlusCore.getInstance().logger("Template with id " + templateId + " not found");
            return;
        }
        sendMailToPlayer(player, template, minLevel);
    }

    public static void sendMailToPlayer(Player player, MeaMailTemplate template, int minLevel) {
        if (player.getLevel() >= minLevel) {
            player.sendMail(templateToMail(template));
            MeaMailPlusCore.getInstance().logger("Mail sent to " + player.getUid() + ": " + template.title);
        } else {
            MeaMailPlusCore.getInstance().logger("Player " + player.getUid() + " is not level " + minLevel + " and mail was not sent");
        }
    }

    private static Mail templateToMail(MeaMailTemplate mailTemplate) {
        Mail mail = new Mail();
        mail.mailContent.content = mailTemplate.body.content;
        mail.mailContent.title = mailTemplate.title;
        mail.mailContent.sender = mailTemplate.sender;
        mail.importance = mailTemplate.importance;

        if (mailTemplate.remainTime == 0) {
            mail.expireTime = mailTemplate.expireTime;
        } else {
            mail.expireTime = (int) Instant.now().getEpochSecond() + mailTemplate.remainTime;
        }

        mail.itemList = Arrays.stream(mailTemplate.body.items)
                .map(item -> new Mail.MailItem(item.id, item.count, item.level))
                .collect(Collectors.toList());
        return mail;
    }

    private static MeaMailConfig getConfig() {
        return MeaMailPlusCore.getInstance().config.getConfig();
    }

    private static MeaMailTemplate getTemplateById(int id) {
        return MeaMailPlusCore.getInstance().config.getTemplate(id);
    }

    public static void sendMailToAllPlayers(int templateId) {
        sendMailToAllPlayers(templateId, 0);
    }

    public static void sendMailToAllPlayers(MeaMailTemplate template) {
        sendMailToAllPlayers(template, 0);
    }

    public static void sendMailToAllPlayers(int templateId, int minLevel) {
        sendMailToAllPlayers(templateId, minLevel, false);
    }

    public static void sendMailToAllPlayers(MeaMailTemplate template, int minLevel) {
        sendMailToAllPlayers(template, minLevel, false);
    }

    public static void sendMailToAllPlayers(int templateId, int minLevel, boolean onlineOnly) {
        MeaMailTemplate template = getTemplateById(templateId);
        if(template == null) {
            MeaMailPlusCore.getInstance().logger("Template with id " + templateId + " not found");
            return;
        }
        sendMailToAllPlayers(template, minLevel, onlineOnly);
    }

    public static void sendMailToAllPlayers(MeaMailTemplate template, int minLevel, boolean onlineOnly) {
        if (template == null) return;

        List<Player> onlinePlayers = new ArrayList<>();
        List<Player> offlinePlayers = new ArrayList<>();
        Grasscutter.getGameServer().getPlayers().forEach((index, player) -> onlinePlayers.add(player));

        if (!onlineOnly) {
            DatabaseHelper.getAllPlayers().forEach(player -> {
                if (onlinePlayers.stream().noneMatch(onlinePlayer -> onlinePlayer.getUid() == player.getUid())) {
                    offlinePlayers.add(player);
                }
            });
        }

        // two methods to send mail
        onlinePlayers.forEach(player -> sendMailToPlayer(player, template, minLevel));
        offlinePlayers.forEach(player -> saveMailToDatabase(player, template, minLevel));
    }

    public static void sendDailySignInMailToPlayer(int uid) {
        Grasscutter.getGameServer().getPlayers().forEach((index, player) -> {
            if (player.getUid() == uid)
                sendDailySignInMailToPlayer(player);
        });
    }

    public static void sendDailySignInMailToPlayer(Player player) {
        MeaMailConfig.DailySignInMailTask[] dailyTasks = getConfig().dailySignInMail;
        for(MeaMailConfig.DailySignInMailTask task : dailyTasks) {
            sendMailToPlayer(player, task.templateId, task.minLevel);
        }
    }

    public static void sendBirthDayMailToPlayer(int uid) {
        Grasscutter.getGameServer().getPlayers().forEach((index, player) -> {
            if (player.getUid() == uid)
                sendBirthDayMailToPlayer(player);
        });
    }

    public static void sendBirthDayMailToPlayer(Player player) {
        int[] BirthdayMailTemplateIds = getConfig().birthDayMail;
        for(int templateId : BirthdayMailTemplateIds) {
            sendMailToPlayer(player, templateId);
        }
    }

    public static void sendInitialMailToPlayer(int uid) {
        Grasscutter.getGameServer().getPlayers().forEach((index, player) -> {
           if (player.getUid() == uid)
               sendInitialMailToPlayer(player);
        });
    }

    public static void sendInitialMailToPlayer(Player player) {
        int[] InitialMailTemplateIds = getConfig().initialMail;
        for(int templateId : InitialMailTemplateIds) {
            sendMailToPlayer(player, templateId);
        }
    }

    public static void dailyUpdate() {
        // update daily
        Grasscutter.getGameServer().getPlayers().forEach((index, player) -> {
            // birthday mail
            if (PlayerUtils.isPlayerBirthDay(player)) {
                sendBirthDayMailToPlayer(player);
            }

            // daily sign in
            sendDailySignInMailToPlayer(player);
        });
    }

    public static void repetitionUpdate(MeaMailConfig.DailyRepetitionTask task) {
        // update daily on time
        sendMailToAllPlayers(task.templateId, task.minLevel, task.onlineOnly);
    }
}
