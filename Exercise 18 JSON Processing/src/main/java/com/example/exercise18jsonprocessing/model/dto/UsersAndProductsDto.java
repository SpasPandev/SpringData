package com.example.exercise18jsonprocessing.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UsersAndProductsDto {

    @Expose
    private Integer usersCount;
    @Expose
    private List<UsersFirstAndLastNameAgeDto> users;

    public UsersAndProductsDto() {
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UsersFirstAndLastNameAgeDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersFirstAndLastNameAgeDto> users) {
        this.users = users;
    }
}
