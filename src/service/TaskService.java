package service;

import dto.CreateEpicDto;
import dto.CreateTaskDto;
import model.*;
import repository.TaskRepository;

import java.io.*;
import java.util.List;
import java.util.Optional;

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

            // Находим эпик и добавляем в него подзадачу
            Optional<Task> maybeEpic = taskRepository.findById(subtask.getParentEpicId());
            if (maybeEpic.isPresent() && maybeEpic.get() instanceof Epic) {
                Epic epic = (Epic) maybeEpic.get();
                epic.addSubtaskId(epic.getId());
                taskRepository.save(epic);
            }

            return taskRepository.save(subtask);
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
        return taskRepository.findById(id).get();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getByType(Class<? extends Task> taskClass) {
        return taskRepository.findByType(taskClass);
    }
}
