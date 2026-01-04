// File: src/repository/InMemoryTaskRepository.java
package repository;

import dto.CreateTaskDto;
import model.Epic;
import model.SimpleTask;
import model.Subtask;
import model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Реализация репозитория, хранящая задачи в оперативной памяти.
 * Используется для тестирования или временного хранения.
 */
public class InMemoryTaskRepository implements TaskRepository {
    // Все задачи
    private final Map<Integer, Task> tasks = new ConcurrentHashMap<>();

    // Генератор ID
    private final AtomicInteger currentId = new AtomicInteger(0);

    // Обновление или сохранение задачи
    public Task save(Task task) {
        Task savedTask;
        boolean isNewTask = !tasks.keySet().contains(task.getId());
        if (isNewTask) {
            int newId = getNextId();
            Task newTask = createTaskWithId(task, newId);
            tasks.put(newTask.getId(), newTask);
            savedTask = newTask;
        } else {
            tasks.replace(task.getId(), task);
            savedTask = task;
        }
        return savedTask;
    }

    @Override
    public List<Task> findAll() {
        return tasks.values().stream().toList();
    }

    @Override
    public void deleteAll() {
        tasks.clear();
    }

    @Override
    public boolean deleteById(int id) {
        Task result = tasks.remove(id);
        return result != null;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> findByType(Class<? extends Task> taskClass) {
        return tasks.values().stream().filter(task -> taskClass.isInstance(task)).toList();
    }

    @Override
    public List<Subtask> findSubtasksByEpicId(int epicId) {
        return tasks.values().stream().filter(task -> task instanceof Subtask && ((Subtask) task).getParentEpicId() == epicId).map(task -> (Subtask) task).toList();
    }

    public Task createTaskWithId(Task task, int id) {
        try {
            if (task instanceof SimpleTask) {
                return new SimpleTask(task.getStatus(), task.getName(), task.getDescription(), id);
            } else if (task instanceof Epic) {
                Epic epic = (Epic) task;
                return new Epic(epic.getStatus(), epic.getName(), epic.getDescription(), id);
            } else if (task instanceof Subtask) {
                Subtask subtask = (Subtask) task;
                return new Subtask(subtask.getStatus(), subtask.getName(), subtask.getDescription(), id, subtask.getParentEpicId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания задачи с ID: " + id);
        }
        throw new IllegalArgumentException("Неизвестный тип задачи");
    }

    public int getNextId() {
        return currentId.getAndIncrement();
    }
}