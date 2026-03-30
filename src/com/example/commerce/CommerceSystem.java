package com.example.commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    List<Category> categories;
    Scanner sc = new Scanner(System.in);

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
                Category selectedCategory = categories.get(choice - 1);
                showProducts(selectedCategory);
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 카테고리 출력
    private void showCategories() {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }

        System.out.println("0. 종료      | 프로그램 종료");
        System.out.print("번호 입력: ");
    }

    private void showProducts(Category category) {
        while (true) {
            System.out.println("\n[ " + category.getName() + " 카테고리 ]");

            List<Product> products = category.getProducts();

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("%d. %-15s | %,d원 | %s\n",
                        i + 1, p.name, p.price, p.description);
            }

            System.out.println("0. 뒤로가기");
            System.out.print("번호 입력: ");

            int choice = sc.nextInt();

            if (choice == 0) return;

            if (choice >= 1 && choice <= products.size()) {
                Product p = products.get(choice - 1);
                System.out.printf("선택한 상품: %s | %,d원 | %s | 재고: %d개\n\n",
                        p.name, p.price, p.description, p.stock);
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}