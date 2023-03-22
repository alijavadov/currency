package com.digirella.task.service;

import com.digirella.task.dto.ValCurs;
import com.digirella.task.model.Currency;
import com.digirella.task.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {
    private CurrencyRepository currencyRepository;
    private DownloadService downloadService;


    public CurrencyService(CurrencyRepository currencyRepository, DownloadService downloadService) {
        this.currencyRepository = currencyRepository;
        this.downloadService = downloadService;
    }

    public boolean updateCurrencyByDate(String date) {
        if (isExists(date))
            return false;

        ValCurs valCurs = downloadService.downloadValCurs(date);
        writeToDb(valCurs);
        return true;
    }

    private void writeToDb(ValCurs valCurs) {
        for (var valType : valCurs.getValTypes()) {
            for (var valute : valType.getValutes()) {
                var currency = Currency.builder()
                        .valType(valType.getType())
                        .code(valute.getCode())
                        .nominal(valute.getNominal())
                        .value(valute.getValue())
                        .name(valute.getName())
                        .currencyDate(valCurs.getDate())
                        .build();
                currencyRepository.save(currency);
            }
        }
    }

    private boolean isExists(String currencyDate) {
        List<String> dateList = currencyRepository.getDate(currencyDate);
        return dateList != null && !dateList.isEmpty();

    }

    public void deleteCurrencyByDate(String currencyDate) {
        currencyRepository.deleteByCurrencyDate(currencyDate);
    }

    public List<Currency> getCurrencyByDateAndValute(String date, String valute) {
        return currencyRepository.getCurrencyByDateAndValute(date, valute);
    }

    public List<Currency> getCurrencyByDate(String date) {
        return currencyRepository.getCurrencyByDate(date);
    }

    public List<Currency> getCurrencyByValute(String valute) {
        return currencyRepository.getCurrencyByCode(valute);
    }
}
