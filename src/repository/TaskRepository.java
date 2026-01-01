// File: src/repository/TaskRepository.java
package repository;

import model.Subtask;
import model.Task;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с хранилищем задач.
 * Абстрагирует работу с данными, чтобы можно было легко менять реализацию.
 */
public interface TaskRepository {
    /**
     * Сохраняет задачу. Если задача с таким ID уже существует, обновляет её.
     * @param task задача для сохранения
     * @return сохраненная задача
     */
    Task save(Task task);

    /**
     * Находит задачу по ID
     * @param id идентификатор задачи
     * @return Optional с задачей, если найдена
     */
    Optional<Task> findById(int id);

    /**
     * Возвращает все задачи
     * @return список всех задач
     */
    List<Task> findAll();

    /**
     * Возвращает задачи по типу
     * @param taskClass класс задачи (SimpleTask.class, Epic.class, Subtask.class)
     * @return список задач указанного типа
     */
    List<Task> findByType(Class<? extends Task> taskClass);

    /**
     * Удаляет задачу по ID
     * @param id идентификатор задачи для удаления
     * @return true если задача была удалена, false если не найдена
     */
    boolean deleteById(int id);

    /**
     * Удаляет все задачи
     */
    void deleteAll();

    /**
     * Возвращает следующий доступный ID
     * @return следующий ID
     */
    int getNextId();

    /**
     * Возвращает подзадачи эпика
     * @param epicId ID эпика
     * @return список подзадач
     */
    List<Subtask> findSubtasksByEpicId(int epicId);


}