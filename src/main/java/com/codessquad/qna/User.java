package com.codessquad.qna;

public class User {
    // 필드이름은 중요X
    private String userId;
    private String password;
    private String name;
    private String email;

    // 스프링이 Setter 메서드를 통해서 자동으로 데이터를 전달해줌
    // set{name} 구조
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
