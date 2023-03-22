package com.digirella.task.repository;

import com.digirella.task.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    @Query(value = "SELECT currency_date FROM currency WHERE currency_date = ?1", nativeQuery = true)
    List<String> getDate(String inputDate);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM currency WHERE currency_date = ?1", nativeQuery = true)
    void deleteByCurrencyDate(String currencyDate);
    @Query(value = "SELECT * FROM currency WHERE currency_date = ?1 and code = ?2", nativeQuery = true)
    List<Currency> getCurrencyByDateAndValute(String date, String valute);
    @Query(value = "SELECT * FROM currency WHERE currency_date = ?1 ", nativeQuery = true)
    List<Currency> getCurrencyByDate(String date);
    @Query(value = "SELECT * FROM currency WHERE code = ?1 ", nativeQuery = true)
    List<Currency> getCurrencyByCode(String date);
}
