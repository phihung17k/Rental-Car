/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Win 10
 */
public class OrderDetailDTO implements Serializable{
    
    private String carName;
    private int amount;
    private Date rentalDate;
    private Date returnDate;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String carName, int amount, Date rentalDate, Date returnDate) {
        this.carName = carName;
        this.amount = amount;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    
}





