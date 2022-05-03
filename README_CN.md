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

在插件目录中的`MeaMailPlus`目录下, 包含 `template` 文件夹和 `config.json` 配置文件
#### 配置文件设置
配置文件是一个 `JSON` 格式的文件。你可以通过修改配置文件来控制插件的行为。
配置完后，在Console中输入`/meamail reload`即可重载配置文件。
```
{
  "updateTime": [4, 0, 0],         // 设置服务器的邮件推送时间，这里设置的是 04:00:00 (24小时时间制)
  "initialMail": [ 1001 ],         // 设置新玩家第一次进服时接收的邮件TemplateId，可以设置很多个，例如： [1001, 1002, 1003]
  "birthDayMail": [ 1004 ],        // 设置玩家生日邮件TemplateId，当然也可以设置很多个（如果玩家有点贪）
  
  "dailySignInMail": [             // 玩家每日登录的邮件，会在每天推送时间或者是玩家上线时发送，一天只能领取一次，不上线就没有。
    {
      "templateId": 1002,          // 邮件的TemplateId
      "minLevel": 0                // 设置冒险等级限制，0表示不限制，小于这个冒险等级的玩家不会收到邮件
    },
    {
      "templateId": 1003,          // 设置多个邮件...
      "minLevel": 25                
    },
    {
      "templateId": 1004,          // 设置多个邮件...
      "minLevel": 55                
    }
  ],
  
  "dailyRepetitionMail": [         // 设置每日定时邮件
    {
      "onlineOnly": false,         // 是否只有在线玩家能接收邮件，如果设置成离线，则所有玩家都能接收到
      "triggerTime": [12, 0, 0],   // 触发时间 这里是 12:00:00
      "templateId": 1003,          // 邮件templateId
      "minLevel": 0                // 设置冒险等级限制，0表示不限制，小于这个冒险等级的玩家不会收到邮件
    },
    {                              // 设置多个邮件...
      "onlineOnly": true,
      "triggerTime": [16, 0, 0],   // 16:00:00
      "templateId": 1003,
      "minLevel": 0
    },
    {                              // 设置多个邮件...
      "onlineOnly": true,
      "triggerTime": [0, 0, 0],    // 24:00:00
      "templateId": 1003,
      "minLevel": 0
    }
  ]
}
```
#### Template 模板文件
你可以自行创建模板文件放进 `template` 文件夹, 同样只支持 `JSON`文件.
举个栗子:
TemplateExample.json
```
{
  "templateId": 1001,              // 你的邮件TemplateID，不能重复。
  "title": "Mail title",           // 邮件标题
  "sender": "KiritaniIwako",       // 邮件发送人，你可以设置为 "Server"
  "expireTime": 0,                 // 过期时间，稍后会详细介绍
  "remainTime": 2592000,           // 剩余时间，如果你设置了剩余时间，则过期时间不会生效
  "importance": 0,                 // 邮件重要等级，0为默认，1为收藏
  "body": {                        // 邮件内容，稍后会详细介绍
    "content": "Mail content",
    "items": [
      {
        "id": 223,                 // 物品id
        "count": 1,                // 数量
        "level": 1                 // 等级(0-90?)
      }
    ]
  }
}
```
1. 如何设置过期时间 `expireTime` 或者是剩余时间 `remainTime`?
- [时间戳转换器](https://tool.lu/timestamp/)
```
{
  "expireTime": 1651571451,
  // use 10 digits timestamp, like this: 
  // you can use convert tool to convert the timestamp to 10 digits timestamp, 
  // https://tool.lu/timestamp/
  
  "remainTime": 2592000
  // time in second
  // 2592000 is the seconds of 30 days: 
  // 2592000 = (d) * 24(h) * 60(m) * 60(s)
  // if you are not sure, you can use two 10 digits timestamp subtraction:
  // 2592000 = 1651568400 (2022-5-3 9:00:00GMT) - 1648976400 (2022-4-3 9:00:00GMT)
}
```

2. How to set the mail `body/content`?
```
{
  "body": {
    "content": "Mail content",
    // you can set the content of the mail, 
    // you can use the variable: 
    // `{playerName}`: (Testing) player name
    // `\r\n`: line feed
    // you can use link to open Webview in-game & Browser:
    // `<type=\"browser\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>`: open a Browser, tag name is "Discord".
    // `<type=\"webview\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>`: open a Browser, tag name is "Discord".
    
    "items": [                     // items in the mail, if you set `[]`, no item will be sent
      {
        "id": 223,
        "count": 1,
        "level": 1
      }
      {
        "id": 223,                 // item id
        "count": 1,                // amount
        "level": 1                 // item level(1-90?)
      },
      {
        "id": 224,                 // Multiple items...
        "count": 1,
        "level": 1
      },
      {
        "id": 202,                 // Multiple items...
        "count": 10000000,
        "level": 1
      }
    ]
  }
}
```

Hyperlinks in the mail content:
```
<type=\"browser\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>
<type=\"webview\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>
```

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