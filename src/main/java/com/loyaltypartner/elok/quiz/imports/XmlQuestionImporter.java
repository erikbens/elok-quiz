package com.loyaltypartner.elok.quiz.imports;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.loyaltypartner.elok.quiz.xml.List;

public class XmlQuestionImporter {

    public static List readXml(String fileName) {
        File xmlFile = new File(fileName);

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(List.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (List) jaxbUnmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
