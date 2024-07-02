package org.example;

import java.util.ArrayList;
import io.qameta.allure.Step;


public class Pets {
    private Integer id;
    private Category category;
    private String name;
    private ArrayList<String> photoUrls;
    private ArrayList<Tag> tags;
    private String status;

    public Pets(Integer id, Category category, String name, ArrayList<String> photoUrls, ArrayList<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }
}