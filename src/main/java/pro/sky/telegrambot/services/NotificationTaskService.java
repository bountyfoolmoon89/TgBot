package pro.sky.telegrambot.services;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class NotificationTaskService {

    private final NotificationTaskRepository repository;

    public NotificationTaskService(final NotificationTaskRepository repository) {
        this.repository = repository;
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public NotificationTask create(Long chatId, String taskTime, String text) {
        LocalDateTime dateTime;
        NotificationTask task = new NotificationTask();
        try {
            dateTime = LocalDateTime.parse(taskTime, DATE_TIME_FORMATTER);
            task.setChatId(chatId);
            task.setScheduledTime(dateTime);
            task.setMessage(text);
        } catch (DateTimeParseException e) {
            System.err.println("Неправильный формат даты и времени: " + e.getMessage());
        }

        return repository.save(task);
    }

    public List<NotificationTask> getNotificationTasksByTaskTime(LocalDateTime time) {
        return repository.findByScheduledTime(time);
    }
}