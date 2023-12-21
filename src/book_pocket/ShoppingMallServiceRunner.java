package book_pocket;

import book_pocket.dto.ExistUserDto;
import book_pocket.dto.SignInDto;
import book_pocket.dto.SignUpDto;
import book_pocket.entity.Admin;
import book_pocket.entity.User;
import book_pocket.service.AdminService;
import book_pocket.service.ShoppingMallService;
import book_pocket.service.UserService;

import java.util.Optional;
import java.util.Scanner;

public class ShoppingMallServiceRunner {
    static Scanner sc = new Scanner(System.in);
    static ShoppingMallService shoppingMallService = new ShoppingMallService(sc);
    static AdminService adminService = new AdminService();
    static UserService userService = UserService.getInstance();

    public static void main(String[] args) throws Exception {
        boolean initLoop = true;
        while (initLoop) {
            System.out.println("1. 회원가입 | 2. 로그인 | 3. 종료");
            String initChoice = sc.nextLine();

            if (initChoice.equals("1")) { // 고객 회원가입
                String id = "";

                boolean duplicateName = true; // 이름이 중복이면 다시 입력 받게 반복
                while (duplicateName) { // 고객 아이디 중복 할인
                    System.out.println("아이디를 입력해주세요 : ");
                    id = sc.nextLine();

                    SignUpDto dto = new SignUpDto(id);
                    duplicateName = userService.existById(dto); // 아이디가 중복되지 않으면 false 반환
                    if (duplicateName) {
                        System.out.println("이미 존재하는 아이디 입니다.");
//                        throw new Exception("이미 존재하는 아이디입니다.");
                    }
                }

                System.out.println("비밀번호를 입력해주세요 : ");
                String password = sc.nextLine();
                System.out.println("연락처를 입력해주세요 : ");
                String phone = sc.nextLine();
                System.out.println("이름을 입력해주세요 : ");
                String name = sc.nextLine();

                User user = new User.Builder()
                        .id(id)
                        .password(password)
                        .phone(phone)
                        .name(name)
                        .build();
                userService.signUp(user); // UserList에 회원 정보 저장

            } else if (initChoice.equals("2")) { // 로그인 시작
                SignInDto dto = getSignInDto();
                Optional<User> optionalUser = userService.findByIdAndPassword(dto);
                if (optionalUser.isEmpty()) {
                    System.out.println("잘못된 계정입니다.");
//                        throw new Exception("잘못된 계정입니다.");
                }
                User user = optionalUser.get();

                welComeToShoppingMall();

                ExistUserDto userDto = new ExistUserDto(user.getId());

                boolean userLoop = true;
                while (userLoop) {
                    userLoop = shoppingMallService.displayMenu(userDto);

                    Thread.sleep(1000);
                }

            } else if (initChoice.equals("3")) { // 프로그램 종료
                initLoop = false;
                System.out.println("프로그램을 종료합니다.");

                ImageDisplay imageDisplay = new ImageDisplay();
                imageDisplay.printImage();

            } else if (initChoice.equals("4")) { // 아이디가 관리자 아이디일 경우
                SignInDto dto = getSignInDto("관리자");

                if (dto.getId().length() > 6 && dto.getId().substring(0, 6).equals("Admin_")) {
                    Optional<Admin> optionalAdmin = adminService.signIn(dto);
                    if (optionalAdmin.isPresent()) {
                        boolean adminLoop = true;
                        while (adminLoop) { // 관리자 메뉴 실행
                            adminLoop = shoppingMallService.adminMenu();
                        }
                    }
                } else {
                        throw new Exception("잘못된 계정입니다.");
                }
            } else { // 잘못된 값을 입력된 경우
                System.out.println("회원가입, 로그인, 종료 중에서 골라주세요.");
//                throw new Exception("회원가입, 로그인, 종료 외의 값이 입력되었습니다.");

                Thread.sleep(1000);
            }
        }
        sc.close();
    }

    private static SignInDto getSignInDto() {
        return getSignInDto("");
    }

    private static SignInDto getSignInDto(String s) {
        System.out.printf("=====%s로그인 화면=====\n", s);
        System.out.printf("%s아이디를 입력해주세요 : ", s);
        String id = sc.nextLine();
        System.out.printf("%s비밀번호를 입력해주세요 : ", s);
        String password = sc.nextLine();

        return new SignInDto(id, password);
    }

    private static void welComeToShoppingMall() {
        System.out.println("*".repeat(54));
        System.out.printf("%s\tWelcome to Shopping Mall\n", " ".repeat(10));
        System.out.printf("%s\t Welcome to Book Market\n", " ".repeat(10));
    }
}
