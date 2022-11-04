package com.demo.model;

import java.io.Serializable;

public class TodoActivemqMessage implements Serializable {

    private Integer id;
    private String status;

    public TodoActivemqMessage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
