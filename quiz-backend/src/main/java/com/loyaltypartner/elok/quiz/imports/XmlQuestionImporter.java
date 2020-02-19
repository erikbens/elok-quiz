package com.loyaltypartner.elok.quiz.imports;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.loyaltypartner.elok.quiz.xml.List;

public class XmlQuestionImporter {

    public static List readXml(InputStream inStream) {
        try {

            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(List.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                return (List) jaxbUnmarshaller.unmarshal(inStream);
            } catch (JAXBException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
