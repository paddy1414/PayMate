package com.example.aisling.finalprojectaislingstafford.DTO;

import java.io.Serializable;

/**
 * Created by Patrick on 03/05/2017.
 */

public class SavingsGoalDTO implements Serializable {

    private int savingId;
    private String savingsGoal;
    private double targetAmount,currentAmount;
    private int groupId;

    public SavingsGoalDTO(int savingId, String savingsGoal, double targetAmount, double currentAmount, int groupId) {
        this.savingId = savingId;
        this.savingsGoal = savingsGoal;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.groupId = groupId;
    }

    public String getSavingsGoal() {
        return savingsGoal;
    }

    public void setSavingsGoal(String savingsGoal) {
        this.savingsGoal = savingsGoal;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSavingId() {
        return savingId;
    }

    public void setSavingId(int savingId) {
        this.savingId = savingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavingsGoalDTO that = (SavingsGoalDTO) o;

        if (savingId != that.savingId) return false;
        if (Double.compare(that.targetAmount, targetAmount) != 0) return false;
        if (Double.compare(that.currentAmount, currentAmount) != 0) return false;
        if (groupId != that.groupId) return false;
        return savingsGoal != null ? savingsGoal.equals(that.savingsGoal) : that.savingsGoal == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = savingId;
        result = 31 * result + (savingsGoal != null ? savingsGoal.hashCode() : 0);
        temp = Double.doubleToLongBits(targetAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(currentAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + groupId;
        return result;
    }


    @Override
    public String toString() {
        return "SavingsGoalDTO{" +
                "savingId=" + savingId +
                ", savingsGoal='" + savingsGoal + '\'' +
                ", targetAmount=" + targetAmount +
                ", currentAmount=" + currentAmount +
                ", groupId=" + groupId +
                '}';
    }
}
