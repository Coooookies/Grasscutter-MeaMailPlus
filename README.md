![Group 148.jpg](https://s2.loli.net/2022/05/03/5ZPG2neTlrMW6Az.jpg)
# MeaMailPlus - Grasscutter Mail enhancements
MeaMailPlus is a plugin of [Grasscutter](https://github.com/Grasscutters/Grasscutter), you can use this plugin to send your mail conveniently.

English | 简体中文 (正在写)

## 💡Feature

- [x] **Birthday Mail**  - Send birthday emails to players on their birthdays.
- [X] **Daily Bonus**  - Players will receive rewards when they join the server.
- [X] **Newbie Gift Mail**  - Players will receive rewards when they first joined.
- [X] **Scheduled Mail push**  - You can limit the level threshold of players to receive the mail.
- [X] **Command support**  - Support sending Mails by command.
- [ ] **RESTful API**  - Send Mail via third-party API.
- [ ] **MORE**  - Comming soon...

## 🍗Setup
### Install
1. [Download Plugin Jar](https://github.com/Coooookies/Grasscutter-MeaMailPlus/releases)
2. Put plugins into `plugins` folder of your grasscutter server.
3. Start your server, then the plugin will create `MeaMailPlus` folder in your server plugin folder.
```
Root
│   lib
│   keys
│   resources
│   ...
└───plugins
    │   ...
    └───MeaMailPlus
        │   template       // template files
        └───config.json    // plugin config file
```

### How to use?

🤔 Comming soon...

### Command & Permission
Command:
```
Send Mail:
    /meamail send <templateId> <uid>
    /meamail sendall <templateId> <minLevel>
    /meamail sendallonline <templateId> <minLevel>
Other:
    /meamail reload
    /meamail help
```

Permission:
```
meo.mail | Control plugin functions
```