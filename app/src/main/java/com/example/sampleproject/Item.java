package com.example.sampleproject;

public class Item {


    private Integer id;
    private String name;
    private String data;

    public Item(String name, String data){
        this.name = name;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) {
        this.data = data;
    }

}
