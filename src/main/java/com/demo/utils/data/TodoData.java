package com.demo.utils.data;

import com.demo.model.TodoItem;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
//import org.example.model.TodoItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

//@Slf4j
public class TodoData {
    private static int idValue = 1;

    private final List<TodoItem> items = new ArrayList<>();

    public TodoData() {
        addTodoItem(new TodoItem(1,"First","Add Crud Operations", LocalDate.now(), "Added"));
        addTodoItem(new TodoItem(2,"Second","Add Oracle DB Support", LocalDate.now(), "Added"));
        addTodoItem(new TodoItem(3,"Third","Add ActiveMQ Operations", LocalDate.now(), "Added"));
        addTodoItem(new TodoItem(4,"Fourth","Add JWT Support", LocalDate.now(), "Added"));
        addTodoItem(new TodoItem(5,"Fifth","Add Wildfly Server", LocalDate.now(), "Added"));
    }

    public List<TodoItem> getItems(){
        return Collections.unmodifiableList(items);
    }

    public void addTodoItem(TodoItem todoItem){
        if(todoItem == null){
//            log.info("Todo Item is null.....");
        } else {
            items.add(todoItem);
        }
    }

    public void removeItemById(int id){
        ListIterator<TodoItem> itemListIterator = items.listIterator();

        while (itemListIterator.hasNext()){
            TodoItem item = itemListIterator.next();

            if(item.getId() == id){
                itemListIterator.remove();
                break;
            }
        }
    }

    public TodoItem getSingleTodoItems(int id){
        for(TodoItem item: items){
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public void updateTodoItem(@NonNull TodoItem todoItem){
        ListIterator<TodoItem> itemListIterator = items.listIterator();
        while (itemListIterator.hasNext()){
            TodoItem item = itemListIterator.next();

            if(item.getId() == todoItem.getId()){
                itemListIterator.set(todoItem);
                break;
            }
        }
    }
}
