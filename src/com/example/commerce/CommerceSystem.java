package com.example.commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Category> categories;
    private Scanner sc = new Scanner(System.in);
    private Cart cart = new Cart();

    private final String ADMIN_PASSWORD = "admin123";

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
            } else if (choice == 6) {
                adminLogin();
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
        System.out.println("6. 관리자 모드");

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

                System.out.println("장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인  2. 취소");

                int input = sc.nextInt();

                if (input == 1) {
                    if (p.getStock() <= 0) {
                        System.out.println("재고 부족");
                    } else {
                        cart.addProduct(p);
                        System.out.println("장바구니 추가 완료");
                    }
                }
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
            System.out.printf("주문 완료! %,d원\n", total);
        }
    }

    private void adminLogin() {
        int attempt = 0;

        while (attempt < 3) {
            System.out.print("비밀번호: ");
            String input = sc.next();

            if (input.equals(ADMIN_PASSWORD)) {
                adminMenu();
                return;
            }
            attempt++;
        }
        System.out.println("3회 실패");
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n[ 관리자 모드 ]");
            System.out.println("1. 상품 추가");
            System.out.println("2. 상품 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("4. 전체 상품 조회");
            System.out.println("0. 돌아가기");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 4 -> showAllProducts();
                case 0 -> { return; }
            }
        }
    }

    private void addProduct() {
        System.out.println("카테고리 선택:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }

        Category c = categories.get(sc.nextInt() - 1);

        System.out.print("상품명: ");
        String name = sc.next();

        for (Product p : c.getProducts()) {
            if (p.getName().equals(name)) {
                System.out.println("중복 상품");
                return;
            }
        }

        System.out.print("가격: ");
        int price = sc.nextInt();

        System.out.print("설명: ");
        String desc = sc.next();

        System.out.print("재고: ");
        int stock = sc.nextInt();

        c.getProducts().add(new Product(name, price, desc, stock));
        System.out.println("추가 완료");
    }

    private void updateProduct() {
        System.out.print("상품명: ");
        String name = sc.next();

        for (Category c : categories) {
            for (Product p : c.getProducts()) {
                if (p.getName().equals(name)) {
                    System.out.println("1. 가격 2. 설명 3. 재고");
                    int ch = sc.nextInt();

                    if (ch == 1) p.setPrice(sc.nextInt());
                    if (ch == 2) p.setDescription(sc.next());
                    if (ch == 3) p.setStock(sc.nextInt());

                    System.out.println("수정 완료");
                    return;
                }
            }
        }
    }

    private void deleteProduct() {
        System.out.print("상품명: ");
        String name = sc.next();

        for (Category c : categories) {
            for (Product p : c.getProducts()) {
                if (p.getName().equals(name)) {
                    c.getProducts().remove(p);
                    cart.removeByName(name);
                    System.out.println("삭제 완료");
                    return;
                }
            }
        }
    }

    private void showAllProducts() {
        for (Category c : categories) {
            System.out.println("\n[" + c.getName() + "]");
            for (Product p : c.getProducts()) {
                System.out.printf("%s | %,d원 | 재고:%d\n",
                        p.getName(), p.getPrice(), p.getStock());
            }
        }
    }
}