package com.example.expbook;
import java.io.Serializable;
public class Expense implements Serializable{
    private String expenditure;
    private String month;
    private String charge;
    private String comment;
    public Expense(String expenditure, String month, String charge, String comment){
        this.expenditure = expenditure;
        this.month = month;
        this.charge = charge;
        this.comment = comment;
    }
    public String getExpenditure(){return expenditure;}
    public void setExpenditure(String expenditure){this.expenditure = expenditure;}
    public String getMonth(){return month;}
    public void setMonth(String month){this.month = month;}
    public String getCharge(){return charge;}
    public void setCharge(String charge){this.charge = charge;}
    public String getComment(){return comment;}
    public void setComment(String comment){this.comment = comment;}
}