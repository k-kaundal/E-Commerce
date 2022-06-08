package com.whlinks.e_commerce.models;

public class Item {
  private String name ,descripton, price, image, doc_id;
  public  Item(String name, String descripton, String price, String image, String doc_id){
        this.descripton = descripton;
        this.name = name;
        this.price= price;
        this.image= image;
        this.doc_id = doc_id;
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

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }
}
