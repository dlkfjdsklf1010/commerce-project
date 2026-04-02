package com.example.commerce;

public enum Grade {
    BRONZE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15);

    private final int discount;

    Grade(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }
}