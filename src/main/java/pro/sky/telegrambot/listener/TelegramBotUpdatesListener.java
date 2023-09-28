package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.component.NotificationTimer;
import pro.sky.telegrambot.services.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final NotificationTimer notificationTimer;

    private final NotificationTaskService service;

    private static final Pattern PATTERN = Pattern.compile("([0-9.:\\s]{16})(\\s)(\\W+)");

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTimer notificationTimer, NotificationTaskService service) {
        this.telegramBot = telegramBot;
        this.notificationTimer = notificationTimer;
        this.service = service;
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
                if (text != null && !text.isEmpty()) {
                    if (text.equals("/start")) {
                        notificationTimer.sendMessage(chatId, "Привет! Я бот BountyBot. Введи время и название задачи в виде: 01.01.2022 20:00 Сделать домашнюю работу, и я пришлю тебе напоминалку");
                    } else {
                        Matcher matcher = PATTERN.matcher(text);
                        if (matcher.matches()) {
                            String date = matcher.group(1);
                            String item = matcher.group(3);
                            service.create(chatId, date, item);
                            notificationTimer.sendMessage( chatId,"Задача успешно добавлена");
                        } else {
                            notificationTimer.sendMessage( chatId, "Сообщение должно иметь вид: 01.01.2022 20:00 Сделать домашнюю работу");
                        }
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void executeTask() {
        notificationTimer.sendNotifications();
    }

}
