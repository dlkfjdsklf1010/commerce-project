package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Product> electronics = new ArrayList<>();
        electronics.add(new Product("Galaxy S24", 1200000, "최신 안드로이드 스마트폰", 50));
        electronics.add(new Product("iPhone 15", 1350000, "Apple의 최신 스마트폰", 30));
        electronics.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 20));
        electronics.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 100));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("전자제품", electronics));
        categories.add(new Category("의류", new ArrayList<>()));
        categories.add(new Category("식품", new ArrayList<>()));

        CommerceSystem system = new CommerceSystem(categories);
        system.start();
    }
}