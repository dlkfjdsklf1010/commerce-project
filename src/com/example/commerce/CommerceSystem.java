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
            showMenu();
            int choice = sc.nextInt();

            if (choice == 0) break;
            else if (choice >= 1 && choice <= categories.size())
                showProducts(categories.get(choice - 1));
            else if (choice == 4) showCart();
            else if (choice == 5) cart = new Cart();
            else if (choice == 6) adminLogin();
        }
    }

    private void showMenu() {
        System.out.println("\n[ 메인 ]");

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }

        System.out.println("0. 종료");
        System.out.println("6. 관리자 모드");

        if (!cart.isEmpty()) {
            System.out.println("4. 장바구니 5. 주문취소");
        }
    }

    private void showProducts(Category category) {

        List<Product> products = category.getProducts();

        System.out.println("1. 전체 2. 100만원 이하 3. 초과");
        int filter = sc.nextInt();

        List<Product> filtered =
                filter == 1 ? products :
                        products.stream()
                                .filter(p -> filter == 2
                                        ? p.getPrice() <= 1000000
                                        : p.getPrice() > 1000000)
                                .toList();

        for (int i = 0; i < filtered.size(); i++) {
            Product p = filtered.get(i);
            System.out.printf("%d. %s | %,d원 | %s | 재고: %d개\n",
                    i + 1, p.getName(), p.getPrice(), p.getDescription(), p.getStock());
        }

        int choice = sc.nextInt();
        if (choice == 0) return;

        Product p = filtered.get(choice - 1);

        System.out.println("장바구니 추가? 1.예 2.아니오");
        if (sc.nextInt() == 1) {
            cart.addProduct(p);
        }
    }

    private void showCart() {
        cart.showCart();
        System.out.println("1. 주문 2. 돌아가기");

        if (sc.nextInt() == 1) {
            cart.orderWithDiscount();
        }
    }

    private void adminLogin() {
        for (int i = 0; i < 3; i++) {
            System.out.print("관리자 비밀번호 입력: ");
            String input = sc.next();

            if (input.equals(ADMIN_PASSWORD)) {
                System.out.println("관리자 인증 성공!");
                adminMenu();
                return;
            } else {
                System.out.println("비밀번호가 틀렸습니다.");
                System.out.println("남은 시도 횟수: " + (2 - i));
            }
        }
        System.out.println("3회 실패! 메인 메뉴로 돌아갑니다.");
    }

        private void adminMenu() {
            System.out.println("1. 추가 2. 삭제 3. 수정");
            int c = sc.nextInt();

            if (c == 1) {

                System.out.println("카테고리 선택:");
                for (int i = 0; i < categories.size(); i++){
                   System.out.println((i + 1) + ". " + categories.get(i).getName());
                }

                int catIndex = sc.nextInt() - 1;
                Category selectedCategory = categories.get(catIndex);

                System.out.print("상품명: ");
                String name = sc.next();

                boolean exists = selectedCategory.getProducts().stream()
                        .anyMatch (p ->p.getName().equals(name));

                if(exists) {
                    System.out.println("이미 존재하는 상품입니다!");
                    return;
                }

                System.out.print("가격: ");
                int price = sc.nextInt();

                System.out.print("설명: ");
                String description = sc.nextLine();

                System.out.print("재고수량: ");
                int stock = sc.nextInt();

                selectedCategory.getProducts(). add(
                        new Product(name, price,description, stock)
                );

                System.out.println("상품이 추가되었습니다!");
            }

            if (c == 2) {
                String name = sc.next();
                categories.forEach(cat ->
                        cat.getProducts().removeIf(p -> p.getName().equals(name))
                );
                cart.removeByName(name);
            }

            if (c == 3){
                System.out.print("수정할 상품명: ");
                String name = sc.next();

                Product found = null;

                for (Category cat : categories) {
                    for(Product p : cat.getProducts()) {
                        if(p.getName().equals(name)) {
                            found = p;
                            break;
                        }
                    }
                    if (found != null) break;
                }

                if (found == null) {
                    System.out.println("상품을 찾을 수 없습니다.");
                    return;
                }

                System.out.println("수정 항목 선택:");
                System.out.println("1 가격 2. 설명 3. 재고");

                int choice = sc.nextInt();

                switch(choice) {
                    case 1:
                        System.out.print("새 가격: ");
                        found.setPrice(sc.nextInt());
                        break;

                    case 2:
                        System.out.print("새 설명: ");
                        found.setDescription(sc.next());
                        break;

                    case 3:
                        System.out.print("새 재고수량: ");
                        found.setStock(sc.nextInt());
                        break;

                    default:
                        System.out.println("잘못된 선택입니다.");
                        return;
                }

                System.out.println("상품 정보가 수정되었습니다.");
            }
        }
    }
