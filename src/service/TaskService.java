package service;

import dto.EpicDto;
import dto.TaskDto;
import dto.EpicDto;
import dto.TaskDto;
import model.*;
import repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskService {
    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Subtask> getSubtasksByEpicId(int epicId) {
        return taskRepository.findSubtasksByEpicId(epicId);
    }

    public Task createTask(TaskDto taskDto) {
        if (!taskDto.isValid()) {
            throw new IllegalArgumentException("Введены некорректные данные");
        }

        int nextId = taskRepository.getNextId();

        if (taskDto.hasEpicId()) {
            // Создаём подзадачу
            Subtask subtask = new Subtask(taskDto.getStatus(), taskDto.getName(), taskDto.getDescription(), nextId, taskDto.getEpicId());
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
            Task task = new SimpleTask(taskDto.getStatus(), taskDto.getName(), taskDto.getDescription(), nextId);
            return taskRepository.save(task);
        }
    }

    public Epic createEpic(EpicDto epicDto) {
        int nextId = taskRepository.getNextId();
        Epic epic = new Epic(TaskStatus.NEW, epicDto.getName(), epicDto.getDescription(), nextId);
        return (Epic) taskRepository.save(epic);
    }

    public Task updateTask(TaskDto updateTaskDto) {
        Task updatedTask;
        if (updateTaskDto.hasEpicId()) {
            Subtask subtask = new Subtask(updateTaskDto.getStatus(), updateTaskDto.getName(), updateTaskDto.getDescription(), updateTaskDto.getId(), updateTaskDto.getEpicId());
            updatedTask = subtask;
            taskRepository.save(updatedTask);

            Optional<Task> maybeEpic = taskRepository.findById(subtask.getParentEpicId());
            if (maybeEpic.isPresent() && maybeEpic.get() instanceof Epic) {
                Epic epic = (Epic) maybeEpic.get();
                epic.addSubtaskId(subtask.getId());

                TaskStatus newEpicStatus = getRecalculateEpicStatus(epic.getId());
                epic.setStatus(newEpicStatus);

                taskRepository.save(epic);
            }
        } else {
            SimpleTask simpleTask = new SimpleTask(updateTaskDto.getStatus(), updateTaskDto.getName(), updateTaskDto.getDescription(), updateTaskDto.getId());
            updatedTask = simpleTask;
            taskRepository.save(updatedTask);
        }
        return updatedTask;
    }

    public Epic updateEpic(EpicDto epicDto) {
        TaskStatus epicStatus = getRecalculateEpicStatus(epicDto.getId());
        List<Integer> subtasksIds = getSubtasksByEpicId(epicDto.getId()).stream().map(subtask -> subtask.getId()).collect(Collectors.toList());
        Epic updatedEpic = new Epic(epicStatus, epicDto.getName(), epicDto.getDescription(), epicDto.getId(), subtasksIds);
        taskRepository.save(updatedEpic);
        return updatedEpic;
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElse(null);
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

    public TaskStatus getRecalculateEpicStatus(int epicId) {
        List<Subtask> subtasks = taskRepository.findSubtasksByEpicId(epicId);

        if (subtasks.isEmpty()) {
            return TaskStatus.NEW;
        }

        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;

        for (Subtask subtask : subtasks) {
            TaskStatus status = subtask.getStatus();
            switch (status) {
                case NEW:
                    hasNew = true;
                    break;
                case IN_PROGRESS:
                    hasInProgress = true;
                    break;
                case DONE:
                    hasDone = true;
                    break;
            }
        }

        if (hasInProgress) {
            return TaskStatus.IN_PROGRESS;
        } else if (hasNew && !hasInProgress && !hasDone) {
            return TaskStatus.NEW;
        } else if (hasDone && !hasNew && !hasInProgress) {
            return TaskStatus.DONE;
        }

        return TaskStatus.NEW;
    }
}
