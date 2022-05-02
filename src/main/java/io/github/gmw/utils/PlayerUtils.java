package io.github.gmw.utils;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerBirthday;
import io.github.gmw.MeaMailPlusCore;

import java.util.Calendar;

public final class PlayerUtils {
    public static long getPlayerBirthDayTime(Player player) {
        int[] updateTime = MeaMailPlusCore.getInstance().config.getConfig().updateTime;
        PlayerBirthday date = player.getBirthday();
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, date.getDay());
        cal.set(Calendar.MONTH, date.getMonth() - 1);

        cal.set(Calendar.HOUR_OF_DAY, updateTime[0]);
        cal.set(Calendar.MINUTE, updateTime[1]);
        cal.set(Calendar.SECOND, updateTime[2]);
        return cal.getTimeInMillis() / 1000;
    }

    public static boolean isPlayerBirthDay(Player player) {
        if (player.hasBirthday()) {
            long playerBirthDayTime = getPlayerBirthDayTime(player);
            long playerEndBirthDayTime = playerBirthDayTime + 24 * 60 * 60;
            long currentTime = System.currentTimeMillis() / 1000;

            return currentTime >= playerBirthDayTime && currentTime < playerEndBirthDayTime;
        }
        return false;
    }

    public static boolean isFirstLoginToday(Player player) {
        long startOfDayTime = MeaMailPlusCore.getInstance().config.getTomorrowUpdateTime() - 24 * 60 * 60;
        long playerLastActiveTime = player.getProfile().getLastActiveTime();
        long currentTime = System.currentTimeMillis() / 1000;

        return playerLastActiveTime < startOfDayTime && currentTime >= startOfDayTime;
    }
}
