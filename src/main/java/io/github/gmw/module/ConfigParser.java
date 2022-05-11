package io.github.gmw.module;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import emu.grasscutter.Grasscutter;
import io.github.gmw.MeaMailPlusCore;
import io.github.gmw.config.MeaMailConfig;
import io.github.gmw.config.MeaMailTemplate;

public class ConfigParser {
    private MeaMailConfig config;
    private ArrayList<MeaMailTemplate> templates;
    private final String configPath = Grasscutter.getConfig().folderStructure.plugins + "MeaMailPlus";
    private final String templatePath = this.configPath + "/template";
    private final File configFile = new File( this.configPath + "/config.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void loadTemplates() {
        switch (this.createConfigFolder(this.templatePath)){
            case -1: return;
            case 0: { // Without Template
                this.createTemplate();
                break;
            }
            case 1: {
                break;
            }
        }


        ArrayList<MeaMailTemplate> templates = new ArrayList<>();
        File[] files = new File(this.templatePath)
                .listFiles();

        if (files == null) return; // No Template
        else {
            for (File file : files) {
                try (FileReader fileReader = new FileReader(file)) {
                    MeaMailTemplate template = gson.fromJson(fileReader, MeaMailTemplate.class);
                    templates.add(template); // 加入模板列表
                    MeaMailPlusCore.getInstance()
                            .logger("Loaded template: " + file.getName() + " (TemplateId: " + template.templateId + ")");
                } catch (Exception e) {
                    MeaMailPlusCore.getInstance()
                            .logger("Unable to load template file: " + file.getName(),"error");
                }
            }
        }

        this.templates = templates;
        MeaMailPlusCore.getInstance()
                .logger("Loaded " + templates.size() + " templates. :D");

    }

    //  get template by templateId
    public MeaMailTemplate getTemplate(int templateId) {
        for (MeaMailTemplate template : this.templates) {
            if (template.templateId == templateId) return template;
        }
        return null;
    }

    public void createTemplate() {
        MeaMailTemplate template_initialMail = new MeaMailTemplate();
        MeaMailTemplate template_dailySignInMail = new MeaMailTemplate();
        MeaMailTemplate template_dailyRepetitionMail = new MeaMailTemplate();
        MeaMailTemplate template_birthDayMail = new MeaMailTemplate();

        template_initialMail.templateId = 1001;
        template_initialMail.remainTime = 2592000; // 一个月后过期
        template_initialMail.title = "Thank you for using MeaMailPlus!";
        template_initialMail.sender= "MeaKiritaniIwako";
        template_initialMail.importance = 1;
        template_initialMail.body.content =
                "Hi!\r\n" +
                "Thank you for using MeaMailPlus! You can use MeaMailPlus to send mail to your friends more conveniently.\r\n\r\n" +
                "Github HomePage" +
                "<type=\"browser\" text=\"Github\" href=\"https://github.com/Coooookies/Grasscutter-MeaMailPlus\"/>\r\n" +
                "Grasscutter Discord" +
                "<type=\"browser\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>";
        template_initialMail.body.items = new MeaMailTemplate.MailBody.ItemInfo[]{
                new MeaMailTemplate.MailBody.ItemInfo(80544, 1, 20),
                new MeaMailTemplate.MailBody.ItemInfo(223, 150)
        };

        template_dailySignInMail.templateId = 1002;
        template_dailySignInMail.remainTime = 604800; // 1 week
        template_dailySignInMail.title = "Daily-Bonus";
        template_dailySignInMail.body.content = "Have a nice day！";
        template_dailySignInMail.body.items = new MeaMailTemplate.MailBody.ItemInfo[]{
                new MeaMailTemplate.MailBody.ItemInfo(223, 20),
                new MeaMailTemplate.MailBody.ItemInfo(224, 20)
        };

        template_dailyRepetitionMail.templateId = 1003;
        template_dailyRepetitionMail.remainTime = 1209600; // 2 weeks later
        template_dailyRepetitionMail.title = "Have a question?";
        template_dailyRepetitionMail.sender= "TeamDarkshin";
        template_dailyRepetitionMail.body.content =
                "Join Grasscutter Discord server to ask questions and get answers:" +
                "<type=\"browser\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>";

        template_birthDayMail.templateId = 1004;
        template_birthDayMail.remainTime = 31536000; // 1 year
        template_birthDayMail.title = "Best Wishes on Your Birthday";
        template_birthDayMail.sender = "Mailing System";
        template_birthDayMail.importance = 1;
        template_birthDayMail.body.content =
                "Happy Birthday, Traveler! Please find your gift attached to this message.\r\n" +
                "Thank you for all your support. We wish you a wonderful day, wherever in this world you may roam.";
        template_birthDayMail.body.items = new MeaMailTemplate.MailBody.ItemInfo[]{
                new MeaMailTemplate.MailBody.ItemInfo(118003, 1)
        };


        this.saveTemplate("InitialMail", template_initialMail);
        this.saveTemplate("DailySignInMail", template_dailySignInMail);
        this.saveTemplate("DailyRepetitionMail", template_dailyRepetitionMail);
        this.saveTemplate("BirthDayMail", template_birthDayMail);
    }

    public boolean saveTemplate(String fileName, MeaMailTemplate templateClass) {
        try (FileWriter file = new FileWriter(this.templatePath + "/" + fileName + ".json")) {
            file.write(gson.toJson(templateClass));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // load config.json
    public void loadConfig() {
        try (FileReader file = new FileReader(this.configFile)) {
            this.config = gson.fromJson(file, MeaMailConfig.class);
            MeaMailPlusCore.getInstance().logger("Config Loaded!");
        } catch (Exception e) {
            this.config = new MeaMailConfig();
            MeaMailPlusCore.getInstance().logger("Basic config creating...");
        }

        if (!saveConfig()) {
            MeaMailPlusCore.getInstance().logger("Unable to save config file.","error");
        }
    }

    // save config.json

    public int createConfigFolder(String path) {
        File dir = new File(path);

        if (!dir.exists() || !dir.isDirectory()) {
            if(new File(String.valueOf(dir)).mkdirs()) return 0;
            else return -1;
        }

        return 1;
    }

    public boolean saveConfig() {
        if (this.createConfigFolder(this.configPath) == -1) return false;

        try (FileWriter file = new FileWriter(this.configFile)) {
            file.write(gson.toJson(this.config));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public MeaMailConfig getConfig() {
        return this.config;
    }

    public long getTomorrowUpdateTime() {
        // get tomorrow's update time
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, this.config.updateTime[0]);
        cal.set(Calendar.MINUTE, this.config.updateTime[1]);
        cal.set(Calendar.SECOND, this.config.updateTime[2]);
        return cal.getTimeInMillis() / 1000;
    }
}
