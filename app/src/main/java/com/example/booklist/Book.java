package com.example.booklist;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private int coverResourceId;
    private double price;

    public Book(String title, int coverResourceId, double price) {
        this.title = title;
        this.coverResourceId = coverResourceId;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
