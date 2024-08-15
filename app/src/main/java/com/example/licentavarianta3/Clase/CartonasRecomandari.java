package com.example.licentavarianta3.Clase;

public class CartonasRecomandari {
    private int imageResource;
    private String text;
    private String category;
    private String description;
    private String imageUrl;

    public CartonasRecomandari(int imageResource, String text, String category, String description, String imageUrl) {
        this.imageResource = imageResource;
        this.text = text;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
