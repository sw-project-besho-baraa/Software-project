package org.Library.DAL.Models;

public class User {
    public String userName;
    public String password;
    public String role;
    public int FineAmount;

    public void setUserName(String userName) {this.userName = userName;}
    public void setPassword(String password) {this.password = password;}
    public String getPassword() {return password;}
    public void setRole(String role) {this.role = role;}
    public String getRole() {return role;}
    public int getFineAmount() {
        return FineAmount;
    }
    public void setFineAmount(int FineAmount) {
        this.FineAmount = FineAmount;
    }
}
