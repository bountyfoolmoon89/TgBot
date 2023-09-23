package telegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.services.NotificationTaskRepository;
import pro.sky.telegrambot.services.NotificationTaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static telegrambot.constants.TaskConstants.*;

@ExtendWith(MockitoExtension.class)
class NotificationTaskServiceTest {

    @Mock
    private NotificationTaskRepository repository;

    @InjectMocks
    private NotificationTaskService service;

    @Test
    void createNotificationTaskTest() {
        NotificationTask task1 = new NotificationTask();
        task1.setChatId(1);
        LocalDateTime dateTime = LocalDateTime.parse("2023-07-28 13:46:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        task1.setScheduledTime(dateTime);
        task1.setMessage("Текст задания");

        when(repository.save(task1)).thenReturn(task1);

        assertThat(task1).isSameAs(service.create(1, "28.07.2023 13:46", "Текст задания"));

        verify(repository).save(task1);
        verify(repository, times(1)).save(task1);
    }

    @Test
    void getNotificationTasksByTaskTime() {
        LocalDateTime dateTime = LocalDateTime.parse("2023-07-31 21:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        when(repository.findByScheduledTime(dateTime))
                .thenReturn(TASK_LIST);

        assertThat(repository.findByScheduledTime(dateTime))
                .hasSize(3)
                .containsExactlyInAnyOrder(TASK_3, TASK_1, TASK_2);
    }

}