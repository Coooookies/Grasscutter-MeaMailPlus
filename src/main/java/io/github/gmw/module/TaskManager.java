package io.github.gmw.module;

import io.github.gmw.MeaMailPlusCore;
import io.github.gmw.config.MeaMailConfig;
import io.github.gmw.utils.MailCore;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import io.github.gmw.config.MeaMailConfig.DailyRepetitionTask;

public class TaskManager {
    private Timer timer;

    private static class dailyBeginTask extends TimerTask {
        @Override
        public void run() {
            // new day
            MailCore.dailyUpdate();
        }
    }

    private static class dailyRepetitionTask extends TimerTask {
        private DailyRepetitionTask task;

        public dailyRepetitionTask(DailyRepetitionTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            MailCore.repetitionUpdate(task);
        }
    }

    public Date getTaskStartTime(int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        if (cal.getTime().before(new Date())) {
            cal.add(Calendar.DATE, 1);
        }

        return cal.getTime();
    }

    public void enable() {
        DailyRepetitionTask[] tasks = MeaMailPlusCore.getInstance().config.getConfig().dailyRepetitionMail;
        int[] updateTime = MeaMailPlusCore.getInstance().config.getConfig().updateTime;
        int cycleTime = 1000 * 60 * 60 * 24;

        // create timer and task
        this.timer = new Timer();
        this.timer.schedule(
                new dailyBeginTask(),
                getTaskStartTime(updateTime[0], updateTime[1], updateTime[2]),
                cycleTime
        );

        for(DailyRepetitionTask task : tasks) {
            this.timer.schedule(
                    new dailyRepetitionTask(task),
                    getTaskStartTime(task.triggerTime[0], task.triggerTime[1], task.triggerTime[2]),
                    cycleTime
            );
        }
    }

    public void disable() {
        this.timer.cancel();
    }
}
