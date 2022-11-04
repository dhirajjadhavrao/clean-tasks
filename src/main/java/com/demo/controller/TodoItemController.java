package com.demo.controller;

import com.demo.model.TodoItem;
import com.demo.service.TodoItemsService;
import com.demo.utils.mappings.AttributeNames;
import com.demo.utils.mappings.Mappings;
import com.demo.utils.mappings.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping
public class TodoItemController {
    @Autowired
    private TodoItemsService todoItemsService;

    // --- Model Attributes ---

    @ModelAttribute
    public List<TodoItem> todoItems(){
        log.info("Into TodoItemController: todoItems :{}",this.todoItemsService.getAllItems());
        return this.todoItemsService.getAllItems();
    }

    // --- Handler Method --
    // Handler method connects Controller output to view(.jsp file)
    @GetMapping(Mappings.ITEMS)
    public String items(){
        return ViewNames.ITEMS_LIST;
    }

    @GetMapping(Mappings.ADD_ITEM)
    public String editEditItem(@RequestParam(required = false, defaultValue = "-1") int id,
                               Model model){
//        log.info("Into TodoItemController id: {}", id);
        TodoItem todoItem = this.todoItemsService.getItem(id);
        if (todoItem == null) {
            todoItem = new TodoItem("", "", LocalDate.now());
        }
        model.addAttribute(AttributeNames.TODO_ITEM, todoItem);
        return ViewNames.ADD_ITEM;
    }

    @PostMapping(Mappings.ADD_ITEM)
    public String  addItem(@ModelAttribute(AttributeNames.TODO_ITEM) TodoItem todoItem){
//        log.info("Into TodoItemController addItem: {}", todoItem);
        if(todoItem.getId() == 0)
            this.todoItemsService.addItem(todoItem);
        else
            this.todoItemsService.updateItem(todoItem);
        return "redirect:/" +Mappings.ITEMS;
    }

    @GetMapping(Mappings.DELETE_ITEM)
    public String deleteItem(@RequestParam int id){
//        log.info("Into TodoItemController id: {}", id);
        this.todoItemsService.removeItem(id);
        return "redirect:/" + Mappings.ITEMS;
    }

    @GetMapping(Mappings.VIEW_ITEM)
    public String viewItem(@RequestParam int id, Model model){
        TodoItem todoItem = this.todoItemsService.getItem(id);
        model.addAttribute(AttributeNames.TODO_ITEM, todoItem);
        return ViewNames.VIEW_ITEM;
    }

}
