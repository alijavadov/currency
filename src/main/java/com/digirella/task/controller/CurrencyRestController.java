package com.digirella.task.controller;

import com.digirella.task.model.Currency;
import com.digirella.task.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency/")
public class CurrencyRestController {
    private CurrencyService currencyService;

    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/{date}/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateCurrencyByDate(@PathVariable String date) {
        boolean updated = currencyService.updateCurrencyByDate(date);
        if (updated)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }

    @DeleteMapping("/{date}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCurrencyByDate(@PathVariable String date) {
        currencyService.deleteCurrencyByDate(date);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Currency>> getCurrencyByDateAndCode(@PathVariable String date, @RequestParam(required = false) String valute) {
        if (StringUtils.hasText(valute)) {
            List<Currency> currency = currencyService.getCurrencyByDateAndValute(date, valute);
            if (currency.isEmpty())
                return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(currency, HttpStatus.OK);
        }

        List<Currency> currency = currencyService.getCurrencyByDate(date);
        if (currency.isEmpty())
            return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Currency>> getCurrencyByValute(@RequestParam String valute) {
        List<Currency> currency = currencyService.getCurrencyByValute(valute);
        if (currency.isEmpty()) {
            return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }
}
