package pro.sky.telegrambot.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    public List<NotificationTask> findByScheduledTime(LocalDateTime scheduledTime);

    List<NotificationTask> findByChatId(Long chatId);
}
