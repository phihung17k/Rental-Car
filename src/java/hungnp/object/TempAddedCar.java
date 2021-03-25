/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.object;

import java.io.Serializable;

/**
 *
 * @author Win 10
 */
public class TempAddedCar implements Serializable{
    private String carId;
    private String carName;
    private String carCategory;
    private String rentalDate;
    private String returnDate;
    private float price;

    public TempAddedCar() {
    }

    public TempAddedCar(String carId, String rentalDate, String returnDate) {
        this.carId = carId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public TempAddedCar(String carId, String carName, String carCategory, String rentalDate, String returnDate, float price) {
        this.carId = carId;
        this.carName = carName;
        this.carCategory = carCategory;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
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

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    
}

















