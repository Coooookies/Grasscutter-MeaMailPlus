package io.github.gmw.config;

public final class MeaMailTemplate {
    public int templateId = 0;
    public String title = "";
    public String sender = "Server";
    public long expireTime = 0;

    public long remainTime = 2592000;  // 31 days
    public int importance = 0; // Starred mail, 0 = No star, 1 = Star.
    public MailBody body = new MailBody();
    public static class MailBody {
        public String content = "";
        public ItemInfo[] items = {};

        public static class ItemInfo {
            public int id;
            public int count;
            public int level;

            public ItemInfo(int id, int count) {
                this(id, count, 1);
            }

            public ItemInfo(int id, int count, int level) {
                this.id = id;
                this.count = count;
                this.level = level;
            }
        }
    }
}
