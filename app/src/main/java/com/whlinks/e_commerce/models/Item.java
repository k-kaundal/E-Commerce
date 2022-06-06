package com.whlinks.e_commerce.models;

public class Item {
  private String name ,descripton, price, image;
  public  Item(String name, String descripton, String price, String image){
        this.descripton = descripton;
        this.name = name;
        this.price= price;
        this.image= image;
    }

    public String getDescripton() {
        return descripton;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
