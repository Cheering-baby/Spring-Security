package com.example.springsecurity.dto;

/**
 * @Author Ning Meng
 * @Date 2022/4/28 14:51
 **/

public class MinioUploadDto {
    private String url;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MinioUploadDto{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}