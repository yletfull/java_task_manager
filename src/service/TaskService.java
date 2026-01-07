package service;

import dto.CreateEpicDto;
import dto.CreateTaskDto;
import model.*;
import repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class TaskService {
    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Subtask> getSubtasksByEpicId(int epicId) {
        return taskRepository.findSubtasksByEpicId(epicId);
    }

    public Task createTask(CreateTaskDto createTaskDto) {
        if (!createTaskDto.isValid()) {
            throw new IllegalArgumentException("Введены некорректные данные");
        }

        int nextId = taskRepository.getNextId();

        if (createTaskDto.hasEpicId()) {
            // Создаём подзадачу
            Subtask subtask = new Subtask(createTaskDto.getStatus(), createTaskDto.getName(), createTaskDto.getDescription(), nextId, createTaskDto.getEpicId());
            Task savedSubtask = taskRepository.save(subtask);

            // Находим эпик и добавляем в него подзадачу
            Optional<Task> maybeEpic = taskRepository.findById(subtask.getParentEpicId());
            if (maybeEpic.isPresent() && maybeEpic.get() instanceof Epic) {
                Epic epic = (Epic) maybeEpic.get();
                epic.addSubtaskId(subtask.getId());

                TaskStatus newEpicStatus = getRecalculateEpicStatus(epic.getId());
                epic.setStatus(newEpicStatus);

                taskRepository.save(epic);
            }

            return savedSubtask;
        } else {
            Task task = new SimpleTask(createTaskDto.getStatus(), createTaskDto.getName(), createTaskDto.getDescription(), nextId);
            return taskRepository.save(task);
        }
    }
    
    public Epic createEpic(CreateEpicDto createEpicDto) {
        int nextId = taskRepository.getNextId();
        Epic epic = new Epic(TaskStatus.NEW, createEpicDto.getName(), createEpicDto.getDescription(), nextId);
        return (Epic) taskRepository.save(epic);
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseGet(null);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Epic> getAllEpics() {
        return this.taskRepository.findAll().stream().filter(task -> task instanceof Epic).map(task -> (Epic) task).collect(Collectors.toList());
    }

    public List<Task> getByType(Class<? extends Task> taskClass) {
        return taskRepository.findByType(taskClass);
    }

    public TaskStatus getRecalculateEpicStatus (int epicId) {
        List<Subtask> subtasks = taskRepository.findSubtasksByEpicId(epicId);

        AtomicBoolean isAnyInProcessed = new AtomicBoolean(false);
        AtomicBoolean isAllIsDone = new AtomicBoolean(true);

        if(subtasks.isEmpty()) {
            return TaskStatus.NEW;
        }

        subtasks.stream().forEach(subtask -> {
            TaskStatus status = subtask.getStatus();
            if(status == TaskStatus.IN_PROGRESS) {
                isAnyInProcessed.set(true);
                isAllIsDone.set(false);
            } else if (status == TaskStatus.NEW) {
                isAllIsDone.set(false);
            }
        });

        if(isAnyInProcessed.get()) {
            return TaskStatus.IN_PROGRESS;
        } else if (isAllIsDone.get()) {
            return TaskStatus.DONE;
        }

        return TaskStatus.NEW;
    }
}
