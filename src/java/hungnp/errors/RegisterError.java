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
public class RegisterError {
    private String emailEmptyError;
    private String emailFormatError;
    private String emailExistedError;
    private String phoneEmptyError;
    private String phoneInvalidError;//10-15
    private String nameEmptyError;
    private String addressEmptyError;
    private String passwordEmptyError;
    private String confirmNotMatchError;

    public RegisterError() {
    }

    public RegisterError(String emailEmptyError, String emailFormatError, String emailExistedError, String phoneEmptyError, String phoneInvalidError, String nameEmptyError, String addressEmptyError, String passwordEmptyError, String confirmNotMatchError) {
        this.emailEmptyError = emailEmptyError;
        this.emailFormatError = emailFormatError;
        this.emailExistedError = emailExistedError;
        this.phoneEmptyError = phoneEmptyError;
        this.phoneInvalidError = phoneInvalidError;
        this.nameEmptyError = nameEmptyError;
        this.addressEmptyError = addressEmptyError;
        this.passwordEmptyError = passwordEmptyError;
        this.confirmNotMatchError = confirmNotMatchError;
    }

    public String getEmailEmptyError() {
        return emailEmptyError;
    }

    public void setEmailEmptyError(String emailEmptyError) {
        this.emailEmptyError = emailEmptyError;
    }

    public String getEmailFormatError() {
        return emailFormatError;
    }

    public void setEmailFormatError(String emailFormatError) {
        this.emailFormatError = emailFormatError;
    }

    public String getEmailExistedError() {
        return emailExistedError;
    }

    public void setEmailExistedError(String emailExistedError) {
        this.emailExistedError = emailExistedError;
    }

    public String getPhoneEmptyError() {
        return phoneEmptyError;
    }

    public void setPhoneEmptyError(String phoneEmptyError) {
        this.phoneEmptyError = phoneEmptyError;
    }

    public String getPhoneInvalidError() {
        return phoneInvalidError;
    }

    public void setPhoneInvalidError(String phoneInvalidError) {
        this.phoneInvalidError = phoneInvalidError;
    }

    public String getNameEmptyError() {
        return nameEmptyError;
    }

    public void setNameEmptyError(String nameEmptyError) {
        this.nameEmptyError = nameEmptyError;
    }

    public String getAddressEmptyError() {
        return addressEmptyError;
    }

    public void setAddressEmptyError(String addressEmptyError) {
        this.addressEmptyError = addressEmptyError;
    }

    public String getPasswordEmptyError() {
        return passwordEmptyError;
    }

    public void setPasswordEmptyError(String passwordEmptyError) {
        this.passwordEmptyError = passwordEmptyError;
    }

    public String getConfirmNotMatchError() {
        return confirmNotMatchError;
    }

    public void setConfirmNotMatchError(String confirmNotMatchError) {
        this.confirmNotMatchError = confirmNotMatchError;
    }
    
    
    
}




















