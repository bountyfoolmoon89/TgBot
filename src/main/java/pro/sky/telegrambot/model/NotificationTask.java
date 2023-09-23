package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false)
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "scheduled_time", nullable = false)
    private LocalDateTime scheduledTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    String formatDateTime = scheduledTime.format(formatter);


    public NotificationTask(long id, long chatId, String message, LocalDateTime scheduledTime) {
        this.id = id;
        this.chatId = chatId;
        this.message = message;
        this.scheduledTime = scheduledTime;
    }

    public NotificationTask() {

    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(message, that.message) && Objects.equals(scheduledTime, that.scheduledTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, message, scheduledTime);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", message='" + message + '\'' +
                ", scheduledTime=" + scheduledTime +
                '}';
    }
}