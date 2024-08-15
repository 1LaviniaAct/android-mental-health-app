package com.example.licentavarianta3.Clase;

public class Cartonas {
    private int imageResource;
    private String text;
    private String category;
//    private String categoryRosenberg;
    private String description;
    private String imageUrl;

    public Cartonas(int imageResource, String text, String category, String description, String imageUrl) {
        this.imageResource = imageResource;
        this.text = text;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
    }
//    public Cartonas(int imageResource, String text, String category, String categoryRosenberg, String description, String imageUrl) {
//        this.imageResource = imageResource;
//        this.text = text;
////        this.category = category;
//        this.categoryRosenberg = categoryRosenberg;
//        this.description = description;
//        this.imageUrl = imageUrl;
//    }

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

//    public String getCategoryRosenberg() {
//        return categoryRosenberg;
//    }
//
//    public void setCategoryRosenberg(String categoryRosenberg) {
//        this.categoryRosenberg = categoryRosenberg;
//    }
}
