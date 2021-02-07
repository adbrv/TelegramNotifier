# TelegramNotifier

A small library for sending messages to telegram users. Basically it is used to send messages to itself from programs or
from the command line, I will post the tools for this soon.

__**Checklist:**__

* The user must first subscribe to the bot.
* You must know user chat id. There are a channels, like this @chat_id_echo_bot for that.

If you receive status 400+, so you need to investigate the problem. More information
here [Telegram Api](https://core.telegram.org/method/messages.sendMessage).

**How to use**

```java
TelegramNotifier notifier=new TelegramNotifier.Builder()
        .withToken("Your Long Token from BotFather")
        .withChatId("Chat id of user")
        .withMessage("Message")
        .build();

int status=notifier.sendMessage();
```