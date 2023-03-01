package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UsersAndProductsRootDto {

    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "user")
    private List<UsersAndProductsDto> users;

    public UsersAndProductsRootDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UsersAndProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersAndProductsDto> users) {
        this.users = users;
    }
}
