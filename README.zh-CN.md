# MeaNotice - Grasscutter 定时公告插件
MeaNotice 是一个使用于 [Grasscutter](https://github.com/Grasscutters/Grasscutter) 的插件，你可以使用这个插件在游戏内发送定时公告。

[English](./README.md) | 简体中文

## 开始
### 安装插件
1. [下载插件本体](https://github.com/Coooookies/MeaNotice/releases)
2. 将插件本体放置在 `服务器根目录/plugins` 目录下
3. 启动服务器，插件将会在你的服务器根目录下创建一个 `MeaNotice` 文件夹.
```
Root
│   lib
│   keys
│   resources
│   plugins
│   ...
└───MeaNotice
    └───config.json
```

### 配置
```json
{
  "interval": 30000,
  "notices": [
    "Welcome to this server! If you want to learn how to use commands, please type /help in chatroom.",
    "Hey! Do you need help? Add UID1 as a friend, the admin will help you."
  ]
}
```
```
说明:
    interval: 必填项，用于设置定时公告的时间间隔，单位为毫秒，默认为 30000 毫秒（30 秒）。
    notices: 用于设置定时公告的内容，格式为字符串数组，每条公告都会以上到下顺序发送给玩家。
```

### 命令 & 权限
命令:
```
/meanotice reload
重载插件配置
```

权限:
```
mea.notice | 控制插件功能
```
