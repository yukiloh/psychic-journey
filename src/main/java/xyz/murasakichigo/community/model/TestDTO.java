package xyz.murasakichigo.community.model;

import java.io.Serializable;

public class TestDTO implements Serializable {
    private String name;
    private String paswd;
    private String date;

    @Override
    public String toString() {
        return "TestDTO{" +
                "name='" + name + '\'' +
                ", paswd='" + paswd + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaswd() {
        return paswd;
    }

    public void setPaswd(String paswd) {
        this.paswd = paswd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
