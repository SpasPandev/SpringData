package org.example;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("unit");


    }
}