package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Product> electronics = new ArrayList<>();
        electronics.add(new Product("Galaxy S24", 1200000, "스마트폰", 50));
        electronics.add(new Product("iPhone 15", 1350000, "아이폰", 30));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("전자제품", electronics));
        categories.add(new Category("의류", new ArrayList<>()));
        categories.add(new Category("식품", new ArrayList<>()));

        new CommerceSystem(categories).start();
    }
}