package com.abb.cbar.service;

import com.abb.cbar.entity.ExchangeRate;
import com.abb.cbar.enums.SupportedCurrency;
import com.abb.cbar.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CbarXmlParserService {
    
    private static final DateTimeFormatter CBAR_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    private final RestClient cbarRestClient;
    
    public List<ExchangeRate> fetchRates (LocalDate date) {
        String dateStr = date.format(CBAR_DATE_FORMAT);
        try {
            String xml = cbarRestClient.get()
                    .uri("/{date}.xml", dateStr)
                    .retrieve()
                    .body(String.class);
            return parseXml(xml, date);
        }
        catch (ApiException e) {
            throw e;
        }
        catch (Exception e) {
            throw ApiException.serviceUnavailable("Failed to fetch rates from CBAR for date " + dateStr, e);
        }
    }
    
    public List<ExchangeRate> parseXml (String xml, LocalDate date) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            
            List<ExchangeRate> rates = new ArrayList<>();
            NodeList currencies = doc.getElementsByTagName("Valute");
            
            for (int i = 0; i < currencies.getLength(); i++) {
                Element valute = (Element) currencies.item(i);
                String code = valute.getAttribute("Code");
                if (!SupportedCurrency.isSupported(code) || code.equalsIgnoreCase("AZN")) continue;
                
                int nominal = Integer.parseInt(valute.getElementsByTagName("Nominal").item(0).getTextContent().trim());
                BigDecimal value = new BigDecimal(valute.getElementsByTagName("Value").item(0).getTextContent().trim());
                rates.add(new ExchangeRate(date, code.toUpperCase(), nominal, value));
            }
            return rates;
        }
        catch (Exception e) {
            throw ApiException.serviceUnavailable("Failed to parse CBAR XML", e);
        }
    }
}
