package book_pocket.entity;

public class Person {
    private String id;
    private String password;
    private String phone;
    private Authority authority;

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Person() {}

    public Person(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Person(String id, String password, String phone, Authority authority) {
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.authority = authority;
    }

    protected String printInfo() {
        return "아이디 : " + this.id + "\n"
                + "연락처 : " + this.phone;
    }
}
