package com.demo.service;

import com.demo.activmq.MessageSender;
import com.demo.dao.TodoItemDao;
import com.demo.model.TodoActivemqMessage;
import com.demo.model.TodoItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Component
public class TodoItemsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoItemsService.class);

    @Autowired
    private TodoItemDao todoItemDao;
    @Autowired
    private MessageSender messageSender;
    public List<TodoItem> getAllItems(){
        return todoItemDao.getAllItems();
    }

    public TodoItem getItem(int id) {
        return todoItemDao.getItem(id);
    }

    public void addItem(TodoItem item) {
        todoItemDao.addItem(item);
    }

    public void removeItem(int id) {
        todoItemDao.removeItem(id);
    }

    public void updateItem(TodoItem item) {
        LOGGER.info("Started TodoItemsService:updateItem() item {}", item.getId());
        if(item != null){
            LOGGER.info("TodoItemsService:updateItem() Sending message item {}", item.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode message = objectMapper.createObjectNode();

            message.put("id", String.valueOf(item.getId()));
            message.put("status", "Updated");

            messageSender.sendMessage(String.valueOf(message));
        }

        todoItemDao.updateItem(item);
    }

}
