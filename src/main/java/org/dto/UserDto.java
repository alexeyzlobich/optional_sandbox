package org.dto;

public class UserDto {

    private String name;

    private String password;

    private Integer age;

    private Integer iban;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public Integer getIban() {
        return iban;
    }

    public void setIban(final Integer iban) {
        this.iban = iban;
    }
}
