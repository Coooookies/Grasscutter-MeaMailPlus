![Group 148.jpg](https://s2.loli.net/2022/05/03/5ZPG2neTlrMW6Az.jpg)
# MeaMailPlus - Grasscutter 邮件增强
MeaMailPlus 是一个 [Grasscutter](https://github.com/Grasscutters/Grasscutter) 插件, 你可以用它来轻松的收发邮件.

[English](https://github.com/Scirese/Grasscutter-MeaMailPlus/blob/main/README.md) | 简体中文

## 💡Feature

- [x] **生日邮件**  - 玩家生日时向他们发送祝福邮件.
- [X] **每日奖励**  - 玩家每天登录时都能收到奖励.
- [X] **新手礼包**  - 玩家第一次进入服务器时会收到奖励.
- [X] **推送定时邮件**  - 你可以设置冒险等级限制，决定哪些玩家能收到定时邮件.
- [X] **支持命令**  - 支持用命令发送邮件.
- [ ] **RESTful API**  - 使用第三方API发送邮件.
- [ ] **更多**  - Comming soon...

## 🍗Setup
### 安装
1. [下载插件](https://github.com/Coooookies/Grasscutter-MeaMailPlus/releases)
2. 把插件放进你的Grasscutter服务器的 `plugins` 文件夹。
3. 启动服务器，插件会自动在你的服务器文件夹下生成 `MeaMailPlus` 文件夹。
```
Root
│   lib
│   keys
│   resources
│   ...
└───plugins
    │   ...
    └───MeaMailPlus
        │   template       // 模板文件
        └───config.json    // 插件配置
```

### 怎么用？

🤔 正在写......

### 命令和权限
Command:
```
发送邮件:
    /meamail send <模板Id> <uid>
    /meamail sendall <模板Id> <最小冒险等级>
    /meamail sendallonline <模板Id> <最小冒险等级>
其他:
    /meamail reload
    /meamail help
```

Permission:
```
meo.mail | 控制插件
```