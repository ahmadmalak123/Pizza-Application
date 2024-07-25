package com.example.pizzapp;

public class FoodClass {

    private String Foodname;
    private String Fooddesc;
    private int Foodprice;
    private String Foodimage;
    private String Foodcategory;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FoodClass(String foodname, String fooddesc, int foodprice, String foodimage, String foodcategory) {
        Foodname = foodname;
        Fooddesc = fooddesc;
        Foodprice = foodprice;
        Foodimage = foodimage;
        Foodcategory = foodcategory;
    }

    public String getFoodcategory() {
        return Foodcategory;
    }

    public void setFoodcategory(String foodcategory) {
        Foodcategory = foodcategory;
    }


    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getFooddesc() {
        return Fooddesc;
    }

    public void setFooddesc(String fooddesc) {
        Fooddesc = fooddesc;
    }

    public int getFoodprice() {
        return Foodprice;
    }

    public void setFoodprice(int foodprice) {
        Foodprice = foodprice;
    }

    public String getFoodimage() {
        return Foodimage;
    }

    public void setFoodimage(String foodimage) {
        Foodimage = foodimage;
    }

    public FoodClass(){}

}
