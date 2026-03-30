package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
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
            int price = p.getPrice() * item.getQuantity();

            System.out.printf("%s | %,d원 | 수량: %d개\n",
                    p.getName(),
                    p.getPrice(),
                    item.getQuantity());

            total += price;
        }

        System.out.println("\n[ 총 주문 금액 ]");
        System.out.printf("%,d원\n", total);
    }

    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public void order() {
        for (CartItem item : items) {
            Product p = item.getProduct();
            p.setStock(p.getStock() - item.getQuantity());
        }
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void removeByName(String name) {
        items.removeIf(item -> item.getProduct().getName().equals(name));
    }
}