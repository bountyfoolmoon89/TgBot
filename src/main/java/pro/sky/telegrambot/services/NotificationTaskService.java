package pro.sky.telegrambot.services;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.services.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificationTaskService {

    private final NotificationTaskRepository repository;

    public NotificationTaskService(final NotificationTaskRepository repository) {
        this.repository = repository;
    }

    public NotificationTask create(Integer chatId, String taskTime, String text) {
        LocalDateTime dateTime = LocalDateTime.parse(taskTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        NotificationTask task = new NotificationTask();
        task.setChatId(chatId);
        task.setScheduledTime(dateTime);
        task.setMessage(text);

        return repository.save(task);
    }

    public List<NotificationTask> getNotificationTasksByTaskTime(LocalDateTime time) {
        return repository.findByScheduledTime(time);
    }
}