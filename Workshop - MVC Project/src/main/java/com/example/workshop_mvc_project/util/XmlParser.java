package com.example.workshop_mvc_project.util;

public interface XmlParser {

    <T> T deserialize(String input, Class<T> tClass);
}
