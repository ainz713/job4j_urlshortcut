package ru.job4j.domain;

import java.util.Objects;

public class Registration {
    private boolean registration;
    private String login;
    private String password;

    public Registration(boolean registration, String login, String password) {
        this.registration = registration;
        this.login = login;
        this.password = password;
    }

    public boolean isRegistration() {
        return registration;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Registration that = (Registration) o;
        return registration == that.registration
                && Objects.equals(login, that.login)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registration, login, password);
    }

    @Override
    public String toString() {
        return "Registration{"
                + "registration=" + registration
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
