package pro.sky.telegrambot.parsing;
import pro.sky.telegrambot.services.NotificationTaskRepository;
import pro.sky.telegrambot.model.NotificationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationParser {

    private NotificationTaskRepository taskRepository;

    @Autowired
    public void NotificationTaskService(NotificationTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void processAndSaveNotification(String messageText) {

        String regexPattern = "([0-9.:\\s]{16})(\\s)([\\W+]+)";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(messageText);

        if (matcher.find()) {
            String dateTimeStr = matcher.group(1);
            String taskText = matcher.group(3);

            LocalDateTime scheduledTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formatDateTime = scheduledTime.format(formatter);

            NotificationTask notificationTask = new NotificationTask(1,2,"05.09.2023 20:00 Сделать домашнюю работу",LocalDateTime.parse("05.09.2023 00:00"));
            notificationTask.setScheduledTime(scheduledTime);
            notificationTask.setMessage(taskText);

            taskRepository.save(notificationTask);
        }
    }

    public List<NotificationTask> getAllNotificationTasks() {
        return taskRepository.findAll();
    }
}

