package com.gutefind.mobile.ui.products;

public class Product {

    private Integer id;
    private String name;
    private Integer drawableId;

    public Product(Integer id, String name, Integer drawableId) {
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(Integer drawableId) {
        this.drawableId = drawableId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", drawableId=" + drawableId +
                '}';
    }
}
