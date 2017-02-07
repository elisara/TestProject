package com.example.elisara.mymind;

/**
 *Methods to be able to reach and modify the elements in recycler views header
 */


public class Header {
    private String currentCategory;

    public Header(){}

    public String getCurrentCategory() {
        return currentCategory;
    }
    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }
}