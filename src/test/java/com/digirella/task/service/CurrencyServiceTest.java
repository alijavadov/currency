package com.digirella.task.service;

import com.digirella.task.dto.ValCurs;
import com.digirella.task.dto.ValType;
import com.digirella.task.dto.Valute;
import com.digirella.task.model.Currency;
import com.digirella.task.repository.CurrencyRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
class CurrencyServiceTest {
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private DownloadService downloadService;
    @InjectMocks
    private CurrencyService currencyService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCurrencyByDate_NonExistingDate_UpdatesDb() {
        var date = "05.03.2022";

        var valutes = List.of(Valute.builder().code("USD").build(), Valute.builder().code("EUR").build());
        ValType valType = new ValType();
        valType.setType("val type");
        valType.setValutes(valutes);
        ValCurs valCurs = new ValCurs();
        valCurs.setValTypes(List.of(valType));
        valCurs.setDate(date);

        when(downloadService.downloadValCurs(eq(date))).thenReturn(valCurs);

        currencyService.updateCurrencyByDate(date);

        verify(currencyRepository, times(2)).save(any());
    }

    @Test
    void updateCurrencyByDate_ExistingDate_DoesNotUpdateDb() {
        var date = "05.03.2022";

        var valutes = List.of(Valute.builder().code("USD").build(), Valute.builder().code("EUR").build());
        ValType valType = new ValType();
        valType.setType("val type");
        valType.setValutes(valutes);
        ValCurs valCurs = new ValCurs();
        valCurs.setValTypes(List.of(valType));
        valCurs.setDate(date);

        when(downloadService.downloadValCurs(eq(date))).thenReturn(valCurs);
        when(currencyRepository.getDate(eq(date))).thenReturn(List.of(date));

        currencyService.updateCurrencyByDate(date);

        verify(currencyRepository, times(0)).save(any());
    }

    @Test
    void deleteCurrencyByDate() {
        var date = "05.03.2022";

        currencyService.deleteCurrencyByDate(date);

        verify(currencyRepository, times(1)).deleteByCurrencyDate(eq(date));
    }

    @Test
    void getCurrencyByDateAndValute() {
        var date = "05.03.2022";
        var valute = "USD";
        var currencies = List.of(Currency.builder().currencyDate(date).value(valute).build());
        when(currencyRepository.getCurrencyByDateAndValute(eq(date), eq(valute))).thenReturn(currencies);

        var currenciesResponse = currencyService.getCurrencyByDateAndValute(date, valute);

        assertEquals(currenciesResponse.size(), 1);
    }

    @Test
    void getCurrencyByDate() {
        var date = "05.03.2022";
        var currencies = List.of(Currency.builder().currencyDate(date).build());
        when(currencyRepository.getCurrencyByDate(eq(date))).thenReturn(currencies);

        var currenciesResponse = currencyService.getCurrencyByDate(date);

        assertEquals(currenciesResponse.size(), 1);
    }

    @Test
    void getCurrencyByValute() {
        var valute = "USD";
        var currencies = List.of(Currency.builder().currencyDate(valute).build());
        when(currencyRepository.getCurrencyByCode(eq(valute))).thenReturn(currencies);

        var currenciesResponse = currencyService.getCurrencyByValute(valute);

        assertEquals(currenciesResponse.size(), 1);
    }
}