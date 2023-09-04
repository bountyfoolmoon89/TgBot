package pro.sky.telegrambot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.services.NotificationTaskRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class NotificationTaskRepositoryImpl implements NotificationTaskRepository {

    @Autowired
    private EntityManager entityManager;

    public List<NotificationTask> findByScheduledTime(LocalDateTime scheduledTime) {
        return entityManager.createQuery(
                        "SELECT nt FROM NotificationTask nt WHERE nt.scheduledTime = :scheduledTime",
                        NotificationTask.class)
                .setParameter("scheduledTime", scheduledTime)
                .getResultList();
    }

    @Override
    public List<NotificationTask> findByChatId(Long chatId) {
        return entityManager.createQuery(
                        "SELECT nt FROM NotificationTask nt WHERE nt.chatId = :chatId",
                        NotificationTask.class)
                .setParameter("chatId", chatId)
                .getResultList();
    }

    @Override
    public List<NotificationTask> findAll() {
        return null;
    }

    @Override
    public List<NotificationTask> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<NotificationTask> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<NotificationTask> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(NotificationTask entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends NotificationTask> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends NotificationTask> S save(S entity) {
        return null;
    }

    @Override
    public <S extends NotificationTask> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<NotificationTask> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends NotificationTask> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends NotificationTask> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<NotificationTask> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public NotificationTask getOne(Long aLong) {
        return null;
    }

    @Override
    public NotificationTask getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends NotificationTask> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends NotificationTask> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends NotificationTask> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends NotificationTask> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends NotificationTask> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends NotificationTask> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends NotificationTask, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
