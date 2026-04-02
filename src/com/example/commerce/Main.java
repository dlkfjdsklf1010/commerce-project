package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Product> electronics = new ArrayList<>();
        electronics.add(new Product("Galaxy S24", 1200000, "스마트폰", 50));
        electronics.add(new Product("iPhone 15", 1350000, "아이폰", 30));
        electronics.add(new Product("AirPods Pro", 350000, "이어폰", 100));

        List<Product> clothes = new ArrayList<>();
        clothes.add(new Product("티셔츠", 30000, "면 티셔츠", 100));
        clothes.add(new Product("바지", 40000, "청바지", 3));
        clothes.add(new Product("치마", 50000, "미니스커트", 300));

        List<Product> foods = new ArrayList<>();
        foods.add(new Product("사과", 500, "신선한 사과", 200));
        foods.add(new Product("라면", 1000, "매운 라면", 300));
        foods.add(new Product("과자", 1500, "새우깡", 100));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("전자제품", electronics));
        categories.add(new Category("의류", clothes));
        categories.add(new Category("식품", foods));


        new CommerceSystem(categories).start();
    }
}