package com.example.commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Category> categories;
    private Scanner sc = new Scanner(System.in);
    private Cart cart = new Cart();

    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
    }

    public void start() {
        while (true) {
            showCategories();
            int choice = sc.nextInt();

            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else if (choice >= 1 && choice <= categories.size()) {
                showProducts(categories.get(choice - 1));
            } else if (choice == 4) {
                showCartMenu();
            } else if (choice == 5) {
                cart = new Cart();
                System.out.println("주문이 취소되었습니다.");
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showCategories() {
        System.out.println("\n[ 실시간 커머스 플랫폼 메인 ]");

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }

        System.out.println("0. 종료");

        if (!cart.isEmpty()) {
            System.out.println("\n[ 주문 관리 ]");
            System.out.println("4. 장바구니 확인");
            System.out.println("5. 주문 취소");
        }

        System.out.print("번호 입력: ");
    }

    private void showProducts(Category category) {
        while (true) {
            System.out.println("\n[ " + category.getName() + " ]");

            List<Product> products = category.getProducts();

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("%d. %-15s | %,d원 | %s | 재고: %d개\n",
                        i + 1,
                        p.getName(),
                        p.getPrice(),
                        p.getDescription(),
                        p.getStock());
            }

            System.out.println("0. 뒤로가기");
            System.out.print("번호 입력: ");

            int choice = sc.nextInt();

            if (choice == 0) return;

            if (choice >= 1 && choice <= products.size()) {
                Product p = products.get(choice - 1);

                System.out.printf("\n%s | %,d원 | %s\n",
                        p.getName(), p.getPrice(), p.getDescription());

                System.out.println("장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인  2. 취소");

                int input = sc.nextInt();

                if (input == 1) {
                    if (p.getStock() <= 0) {
                        System.out.println("재고가 부족합니다.");
                    } else {
                        cart.addProduct(p);
                        System.out.println(p.getName() + "가 장바구니에 추가되었습니다.");
                    }
                }
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showCartMenu() {
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return;
        }

        cart.showCart();

        System.out.println("1. 주문 확정  2. 돌아가기");
        int input = sc.nextInt();

        if (input == 1) {
            int total = cart.getTotalPrice();
            cart.order();
            System.out.printf("주문 완료! 총 금액: %,d원\n", total);
        }
    }
}