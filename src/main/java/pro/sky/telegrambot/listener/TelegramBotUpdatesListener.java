package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.services.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private final TelegramBot telegramBot;

    private final NotificationTaskService service;

    @Autowired
    public TelegramBotUpdatesListener(final NotificationTaskService service, final TelegramBot telegramBot) {
        this.service = service;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message() != null) {
                Long chatId = update.message().chat().id();
                String text = update.message().text();
                if (text.equals("/start")) {
                    sendMessage(chatId, "Привет! Я бот BountyBot. Введи время и название задачи в виде: 01.01.2022 20:00 Сделать домашнюю работу, и я пришлю тебе напоминалку");
                } else {
                    Pattern pattern = Pattern.compile("([0-9.:\\s]{16})(\\s)(\\W+)");
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.matches()) {
                        String date = matcher.group(1);
                        String item = matcher.group(3);
                        service.create(Math.toIntExact(chatId), date, item);
                        sendMessage( chatId,"Задача успешно добавлена");
                    } else {
                        sendMessage( chatId, "Сообщение должно иметь вид: 01.01.2022 20:00 Сделать домашнюю работу");
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
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

    private void sendMessage(final Long chatId, final String text) {
        SendMessage message = new SendMessage(chatId, text);
        SendResponse response = telegramBot.execute(message);
        logger.info("Response: {}", response.isOk());
        logger.info("Error code: {}", response.errorCode());
    }
}
