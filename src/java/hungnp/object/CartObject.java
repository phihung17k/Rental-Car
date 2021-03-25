/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Win 10
 */
public class CartObject implements Serializable{
    //key: list car is added temp, value: quantity
    private Map<TempAddedCar, Integer> cars;

    public CartObject() {
    }

    public CartObject(Map<TempAddedCar, Integer> cars) {
        this.cars = cars;
    }

    public Map<TempAddedCar, Integer> getCars() {
        return cars;
    }

    public void setCars(Map<TempAddedCar, Integer> cars) {
        this.cars = cars;
    }
    
    public void addCar(TempAddedCar tempCar){
        if(cars==null){
            cars=new HashMap<>();
        }
        Set<TempAddedCar> setKey = cars.keySet();
        Iterator iter = setKey.iterator();
        int flag = 1;
        while (iter.hasNext() && flag == 1) {
            TempAddedCar carInCart = (TempAddedCar) iter.next();
            //find car which is existed in list yet 
            if (carInCart.getCarId().equals(tempCar.getCarId()) && carInCart.getRentalDate().compareTo(tempCar.getRentalDate()) == 0
                    && carInCart.getReturnDate().compareTo(tempCar.getReturnDate()) == 0) {
                int quantityInCart = cars.get(carInCart);
                cars.put(carInCart, quantityInCart + 1);
                flag = 0;
            }
        }
        if (flag == 1) {
            cars.put(tempCar, 1);
        }
    }
    
    //reduce quantity of Car: 1 unit
    //if quantity of car = 1: remove it
    //if quantity of car = 1 and cart has a car => remove cart
    public void reduceQuantityCar(TempAddedCar tempCar){
        if(cars==null){
            return;
        }
        Set<TempAddedCar> setKey = cars.keySet();
        Iterator iter = setKey.iterator();
        int flag = 1;
        while (iter.hasNext() && flag == 1) {
            TempAddedCar carInCart = (TempAddedCar) iter.next();
            //find car which is existed in list yet 
            if (carInCart.getCarId().equals(tempCar.getCarId()) && carInCart.getRentalDate().compareTo(tempCar.getRentalDate()) == 0
                    && carInCart.getReturnDate().compareTo(tempCar.getReturnDate()) == 0) {
                int quantityInCart = cars.get(carInCart);
                if(quantityInCart>1){
                    cars.put(carInCart, quantityInCart-1);
                }else{
                    cars.remove(carInCart);
                }
                flag = 0;
            }
        }
        if (cars.isEmpty()) {
            cars = null;
        }
    }
    
    public void removeCar(TempAddedCar tempCar){
        if(cars==null){
            return;
        }
        Set<TempAddedCar> setKey = cars.keySet();
        Iterator iter = setKey.iterator();
        int flag = 1;
        while (iter.hasNext() && flag == 1) {
            TempAddedCar carInCart = (TempAddedCar) iter.next();
            //find car which is existed in list yet 
            if (carInCart.getCarId().equals(tempCar.getCarId()) && carInCart.getRentalDate().compareTo(tempCar.getRentalDate()) == 0
                    && carInCart.getReturnDate().compareTo(tempCar.getReturnDate()) == 0) {
                cars.remove(carInCart);
                flag = 0;
            }
        }
        if (cars.isEmpty()) {
            cars = null;
        }
    }
    
}

































































































