package com.demo.dao;

import com.demo.model.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TodoItemDao {

    @Autowired
    HibernateTemplate hibernateTemplate;

    public List<TodoItem> getAllItems() {
        return hibernateTemplate.loadAll(TodoItem.class);
    }

    public TodoItem getItem(int id) {
        return hibernateTemplate.get(TodoItem.class, id);
    }

    @Transactional
    public void addItem(TodoItem item) {
        hibernateTemplate.save(item);
    }

    public void removeItem(int id) {
//        hibernateTemplate.delete();
    }

    @Transactional
    public void updateItem(TodoItem item) {
        hibernateTemplate.save(item);
    }

    public void cleanUp() {

    }

}
