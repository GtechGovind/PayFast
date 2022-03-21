package com.gtech.payfast.Model.Auth;

public class User {

    String id ,name ,email ,number ,operator ,is_verified ,timestamp;

    /* CONSTRUCTOR */

    // USER RESPONSE
    public User(String id, String name, String email, String number, String operator, String is_verified, String timestamp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.operator = operator;
        this.is_verified = is_verified;
        this.timestamp = timestamp;
    }

    // CHECK USER REQUEST
    public User(String number) {
        this.number = number;
    }

    // REGISTER USER
    public User(String name, String email, String number, String operator) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.operator = operator;
    }

    /* GETTERS AND SETTERS */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
