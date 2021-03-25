/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.errors;

/**
 *
 * @author Win 10
 */
public class SearchError {
    private String nameEmptyError;
    private String rentalLessThanReturnDateError;
    private String amountEmptyError;
    private String amountInvalidError;
    
    public SearchError() {
    }

    public SearchError(String nameEmptyError, String rentalLessThanReturnDateError, String amountEmptyError, String amountInvalidError) {
        this.nameEmptyError = nameEmptyError;
        this.rentalLessThanReturnDateError = rentalLessThanReturnDateError;
        this.amountEmptyError = amountEmptyError;
        this.amountInvalidError = amountInvalidError;
    }

    public String getNameEmptyError() {
        return nameEmptyError;
    }

    public void setNameEmptyError(String nameEmptyError) {
        this.nameEmptyError = nameEmptyError;
    }

    public String getRentalLessThanReturnDateError() {
        return rentalLessThanReturnDateError;
    }

    public void setRentalLessThanReturnDateError(String rentalLessThanReturnDateError) {
        this.rentalLessThanReturnDateError = rentalLessThanReturnDateError;
    }

    public String getAmountEmptyError() {
        return amountEmptyError;
    }

    public void setAmountEmptyError(String amountEmptyError) {
        this.amountEmptyError = amountEmptyError;
    }

    public String getAmountInvalidError() {
        return amountInvalidError;
    }

    public void setAmountInvalidError(String amountInvalidError) {
        this.amountInvalidError = amountInvalidError;
    }

    
}


























