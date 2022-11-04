package com.demo.service;

import com.demo.activmq.MessageReceiver;
import com.demo.dao.TodoItemDao;
import com.demo.model.TodoItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatusUpdateScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusUpdateScheduler.class);

    @Autowired
    private MessageReceiver messageReceiver;
    @Autowired
    private TodoItemDao todoItemDao;

    @Scheduled(fixedRate = 5000)
    public void updateTodoTaskStatus() throws JsonProcessingException {
        LOGGER.info("Started StatusUpdateScheduler:updateTodoTaskStatus() ");

        String message = messageReceiver.receiveMessage();
        LOGGER.info("StatusUpdateScheduler:updateTodoTaskStatus() message received message : {}",message);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);

        if (!node.isEmpty()){
            TodoItem todoItem = todoItemDao.getItem(node.get("id").asInt());

            todoItem.setStatus(node.get("status").asText());

            todoItemDao.updateItem(todoItem);
            LOGGER.info("StatusUpdateScheduler:updateTodoTaskStatus() status is updated id : {}",todoItem.getId());

        }
        LOGGER.info("Ended StatusUpdateScheduler:updateTodoTaskStatus() ");

    }
}
