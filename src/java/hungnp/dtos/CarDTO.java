/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.dtos;

import java.io.Serializable;

/**
 *
 * @author Win 10
 */
public class CarDTO implements Serializable{
    private String carId;
    private String carName;
    private String color;
    private String year;
    private String category;
    private float price;
    private String unitPrice;
    private int quantity;

    public CarDTO() {
    }

    public CarDTO(String carId, String carName) {
        this.carId = carId;
        this.carName = carName;
    }

    public CarDTO(String carId, String carName, String category, float price) {
        this.carId = carId;
        this.carName = carName;
        this.category = category;
        this.price = price;
    }

    public CarDTO(String carId, String carName, String color, String year, String category, float price, String unitPrice, int quantity) {
        this.carId = carId;
        this.carName = carName;
        this.color = color;
        this.year = year;
        this.category = category;
        this.price = price;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    
    
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    
    
}






























