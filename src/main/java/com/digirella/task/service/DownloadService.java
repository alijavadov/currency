package com.digirella.task.service;

import com.digirella.task.dto.ValCurs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class DownloadService {
    @Value("${currency.url}")
    private String url;
    public ValCurs downloadValCurs(String date) {
        String currencyUrl = String.format(url, date);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(ValCurs.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        ValCurs valCurs = null;
        try {
            valCurs = (ValCurs) unmarshaller.unmarshal(new URL(currencyUrl));
        } catch (JAXBException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return valCurs;
    }
}
