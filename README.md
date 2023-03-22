# Currency service

Bu servis Azərbaycan manatının xarici valyutakara qarşı rəsmi məzənnələrinin toplanması və axtarışı üçün olan REST servisdir.

Servisin işləməsi üçün Java 17 və Docker quraşdırılmış olmalıdır.

## Database işə salmaq

Əvvəlcə aşağıdakı əmrlə Postrges DB işə salırıq:
```shell
docker run --name currency-db -e POSTGRES_PASSWORD=password -p 5433:5432 -d postgres
```

## Build və test etmək

Servisi build etmək və testləri icra etmək üçün aşağıdakı əmri icra edirik:

```shell
mvnw clean test
```

## Servisi işə salmaq

Servisi işə salmaq üçün aşağıdakı əmri icra edirik:
```shell
mvnw spring-boot:run
```

## REST endpointlər

Servisdə aşağıdakı endpointlər var. Hər yerdə tarix (date) `dd.MM.yyyy` formatındadır.

Update və Delete üçün header-də ApiKey ötürmək lazımdır, digərləri isə basic authentication ilə işləyir. 

Test üçün API key (token):

ApiKey: api-key

Basic auth üçün:

Username: digirella

Password: password

### 1. UpdateCurrency

`POST http://localhost:8080/api/v1/currency/{date}/update` (empty body)

Verilmiş tarix üzrə məzənnələrin download edilib database-ə əlavə olunması üçündür. Əgər varsa 208 (already reported) qaytarır, əks halda database-ə əlavə edib 201 (created) qaytarır.

### 2. DeleteCurrency

`DELETE http://localhost:8080/api/v1/currency/{date}` (empty body) 

Verilmiş tarix üzrə məzənnələrin database-dən silib  200 (ok) qaytarır.

### 3. GetCurrencyByDateAndValute

`GET http://localhost:8080/api/v1/currency/{date}?valute={valCode}` (empty body)

Verilmiş tarix və valyutaya (optional) qarşı məzənnələrin database-dən əldə olunmasi. 200 (ok), data olmadığı halda 404 (not found) qaytarır.
Valyutanın kodlarını cbar.az-dan əldə edə bilərsiniz.

### 4. GetCurrencyByValute

`GET http://localhost:8080/api/v1/currency/?valute={valCode}` (empty body)

Verilmiş valyuta qarşı məzənnələrin database-dən əldə olunması. 200 (ok), data olmadığı halda 404 (not found) qaytarır.
Valyutanın kodlarını cbar.az-dan əldə edə bilərsiniz.