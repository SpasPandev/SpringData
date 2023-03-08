package com.example.workshop_mvc_project.util.Impl;

import com.example.workshop_mvc_project.util.XmlParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Component
public class XmlParserImpl implements XmlParser {

    @Override
    public <T> T deserialize(String input, Class<T> tClass) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (T) unmarshaller.unmarshal(new StringReader(input));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
