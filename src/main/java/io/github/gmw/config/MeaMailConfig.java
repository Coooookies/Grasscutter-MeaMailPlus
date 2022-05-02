package io.github.gmw.config;

public final class MeaMailConfig {
    public int[] updateTime = {4,0,0};
    public int[] initialMail = {1001};
    public int[] birthDayMail = {1004};

    public DailySignInMailTask[] dailySignInMail = {
            new DailySignInMailTask(1002,0)
    };

    public DailyRepetitionTask[] dailyRepetitionMail = {
            new DailyRepetitionTask(1003,0,false, new int[]{12, 0, 0})
    };

    public static class DailyRepetitionTask extends MailTask {
        public boolean onlineOnly;
        public int[] triggerTime;

        public DailyRepetitionTask(int templateId, int minLevel, boolean onlineOnly, int[] triggerTime) {
            super(templateId, minLevel);
            this.onlineOnly = onlineOnly;
            this.triggerTime = triggerTime;
        }
    }

    public static class DailySignInMailTask extends MailTask {
        public DailySignInMailTask(int templateId, int minLevel) {
            super(templateId, minLevel);
        }
    }

    public static class MailTask {
        public int templateId;
        public int minLevel; // 0 - 60

        public MailTask(int templateId, int minLevel) {
            this.templateId = templateId;
            this.minLevel = minLevel;
        }
    }
}
