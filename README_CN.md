![Group 148.jpg](https://s2.loli.net/2022/05/03/5ZPG2neTlrMW6Az.jpg)
# MeaMailPlus - Grasscutter 邮件增强
MeaMailPlus 是一个 [Grasscutter](https://github.com/Grasscutters/Grasscutter) 插件, 你可以用它来轻松的收发邮件.

[English](./README.md) | 简体中文

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
3. 启动服务器，插件会自动在你的服务器插件文件夹下生成 `MeaMailPlus` 文件夹。
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
### 游戏内乱码了！
将配置文件和Template模板文件
转换成gb2312编码，主流编辑器（包括记事本）都可以转换。

### 怎么用？

在插件目录中的`MeaMailPlus`目录下, 包含 `template` 文件夹和 `config.json` 配置文件
#### 配置文件设置
配置文件是一个 `JSON` 格式的文件。你可以通过修改配置文件来控制插件的行为。
配置完后，在Console中输入`/meamail reload`即可重载配置文件。
```
{
  "updateTime": [4, 0, 0],         // 设置服务器的邮件推送时间，这里设置的是 04:00:00 (24小时时间制)
  "initialMail": [ 1001 ],         // 设置新玩家第一次进服时接收的邮件模板ID，可以设置很多个，例如： [1001, 1002, 1003]
  "birthDayMail": [ 1004 ],        // 设置玩家生日邮件模板ID，当然也可以设置很多个（如果玩家有点贪）
  
  "dailySignInMail": [             // 玩家每日登录的邮件，会在每天推送时间或者是玩家上线时发送，一天只能领取一次，不上线就没有。
    {
      "templateId": 1002,          // 邮件模板ID
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
      "templateId": 1003,          // 邮件模板ID
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
你可以自行创建多个模板文件放进 `template` 文件夹，命名随意, 同样只支持 `JSON`文件.
举个栗子:
TemplateExample.json
```
{
  "templateId": 1001,              // 你的邮件模板ID，不能重复。
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
  // 使用十位时间戳来定义到期时间
  // 你可以使用下面的转换工具来将时间转换为时间戳（记得选择时间单位为秒）
  // https://tool.lu/timestamp/
  
  "remainTime": 2592000
  // 剩余时间（秒）
  // 2592000 秒 = 30 天，计算方法：: 
  // 2592000 = 30(天) * 24(小时) * 60(分钟) * 60(秒)
  // 如果你不确定的话，也可以将两个时间转换成时间戳后相减，得到时间间隔：
  // 2592000 = 1651568400 (2022-5-3 9:00:00GMT) - 1648976400 (2022-4-3 9:00:00GMT)
}
```

2. 如何设置邮件的内容 `body/content`?
```
{
  "body": {
    "content": "Mail content",
    // 你可以设置邮件的内容，支持变量：, 
    // `{playerName}`: (未上线) 玩家名称
    // `\r\n`: 换行符
    // 你甚至可以创建超链接，有两种打开方式：`browser`对应的是打开系统浏览器，`webview`对应的是打开游戏自带的浏览器:
    // <type=\"browser\" text=\"标题\" href=\"你的链接地址"/>
    // 比如：
    // `<type=\"browser\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>`: 打开一个系统浏览器，标签的名字叫 "Discord".
    // `<type=\"webview\" text=\"Discord\" href=\"https://discord.gg/T5vZU6UyeG\"/>`: 打开一个游戏自带的浏览器，标签的名字叫 "Discord".
    
    "items": [                     // 邮件附带的物品，如果设置为`[]`则不会附带物品
      {
        "id": 223,                 // 物品id
        "count": 1,                // 数量
        "level": 1                 // 等级(1-90?)
      },
      {
        "id": 224,                 // 多个物品...
        "count": 1,
        "level": 1
      },
      {
        "id": 202,                 // 多个物品...
        "count": 10000000,
        "level": 1
      }
    ]
  }
}
```

在邮件内容里设置超链接:
```
<type=\"browser\" text=\"标题\" href=\"你的链接地址\"/>
```
比如：
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