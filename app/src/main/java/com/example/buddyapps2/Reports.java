package com.example.buddyapps2;

public class Reports {
    private int image;
    private String title;
    private String description;

    public Reports(int image, String title, String description){
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getImage(){
        return image;
    }

    public void setImage(){
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
