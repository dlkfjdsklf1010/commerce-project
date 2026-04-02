package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product) {

        if (product.getStock() <= 0) {
            System.out.println("재고가 부족합니다.");
            return;
        }
        for (CartItem item : items) {
            if (item.getProduct() == product) {

                if(item.getQuantity() >= product.getStock()) {
                    System.out.println("재고를 초과할 수 없습니다.");
                    return;
                }
                item.increaseQuantity();
                return;
            }
        }
        items.add(new CartItem(product));
    }

    public void showCart() {
        int total = 0;

        System.out.println("\n[ 장바구니 내역 ]");

        for (CartItem item : items) {
            Product p = item.getProduct();

            for (int i = 0; i < item.getQuantity(); i++) {
                System.out.printf("%s | %,d원 | 수량: 1개\n",
                        p.getName(), p.getPrice());

                total += p.getPrice();
            }
        }
        System.out.println("\n[ 총 주문 금액 ]");
        System.out.printf("%,d원\n", total);
    }

    public int getTotalPrice() {
        return items.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public void orderWithDiscount() {
        Scanner sc = new Scanner(System.in);

        int total = getTotalPrice();

        System.out.println("고객 등급 선택:");
        System.out.println("1.BRONZE 2.SILVER 3.GOLD 4.PLATINUM");

        int choice = sc.nextInt();
        Grade grade = Grade.values()[choice - 1];

        int discount = total * grade.getDiscount() / 100;
        int finalPrice = total - discount;

        System.out.println("\n주문 완료!");
        System.out.printf("할인 전 금액: %,d원\n", total);
        System.out.printf("%s 할인(%d%%): -%,d원\n",
                grade.name(), grade.getDiscount(), discount);
        System.out.printf("최종 금액: %,d원\n", finalPrice);

        items.forEach(item ->
                item.getProduct().setStock(
                        item.getProduct().getStock() - item.getQuantity()
                )
        );

        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void removeByName(String name) {
        items.removeIf(item ->
                item.getProduct().getName().equals(name)
        );
    }
}