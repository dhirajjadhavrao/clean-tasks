package com.demo.utils.data.seed;

import com.demo.dao.TodoItemDao;
import com.demo.model.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSeeder.class);
    @Autowired
    private TodoItemDao todoItemDao;

    @EventListener
    public void seed(ContextRefreshedEvent event){
        seedData();
    }

    private void seedData(){
        LOGGER.info("Started DatabaseSeeder : seedData()");
        List<TodoItem> itemList = Arrays.asList(new TodoItem(1,"First","Add Crud Operations", LocalDate.now(), "Added"),
                new TodoItem(2,"Second","Add Oracle DB Support", LocalDate.now(), "Added"),
                new TodoItem(3,"Third","Add ActiveMQ Operations", LocalDate.now(), "Added"),
                new TodoItem(4,"Fourth","Add JWT Support", LocalDate.now(), "Added"),
                new TodoItem(5,"Fifth","Add Wildfly Server", LocalDate.now(), "Added"));

        List<TodoItem> allItems = todoItemDao.getAllItems();
        if(allItems == null || allItems.size() <= 0){
            LOGGER.info("DatabaseSeeder : seedData() Data Seeding is started");
            itemList.stream().forEach(todoItem -> todoItemDao.addItem(todoItem));
        } else {
            LOGGER.info("DatabaseSeeder : seedData() Data Seeding not required");
        }
    }
}
