package pro.sky.telegrambot.component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.services.NotificationTaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class NotificationTimer {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final NotificationTaskService service;

    private final TelegramBot telegramBot;

    public NotificationTimer(NotificationTaskService service, TelegramBot telegramBot) {
        this.service = service;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotifications() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> notificationTaskList = service.getNotificationTasksByTaskTime(now);
        notificationTaskList.forEach((notificationTask -> {
            Long chatId = notificationTask.getChatId();
            String text = notificationTask.getMessage();
            sendMessage(chatId, text);
        }));
    }

    public void sendMessage(final Long chatId, final String text) {
        SendMessage message = new SendMessage(chatId, text);
        SendResponse response = telegramBot.execute(message);
        logger.info("Response: {}", response.isOk());
        logger.info("Error code: {}", response.errorCode());
    }

}
