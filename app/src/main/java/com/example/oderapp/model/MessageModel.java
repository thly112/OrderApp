package com.example.oderapp.model;

public class MessageModel {
    boolean success;
    String message;
    String name;

    public String getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(String iddonhang) {
        this.iddonhang = iddonhang;
    }

    String iddonhang;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
