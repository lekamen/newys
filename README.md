# newys

### Baza
* PostgreSQL
* Docker

### Aplikacija
* Java 8
* Spring Boot 2.4 - spring security, spring data JPA
* Vaadin 14.4.3

#### Dizanje baze:
* Koristi se PostgreSQL baza & docker - docker-compose file
* Nakon kloniranja projekta, u src/main/resources/db se nalazi docker-compose file
* Unutar tog foldera pokrenuti naredbu "docker-compose up -d". Pored toga što se digne docker container s bazom, izvršavaju se i inicijalne skripte, koje se nalaze u podfolderu scripts. 
* Skripte su podijeljene na 2 dijela:
  * 1-schema.sql - skripta u kojoj se kreiraju db objekti
  * 2-data.sql - skripta koja puni tablice

#### Dizanje aplikacije:
* U root folderu projekta pokrenuti "mvn spring-boot:run". Prvi put će potrajati nekoliko minuta, dok Vaadin ne skine sve potrebne komponente.
* Aplikacija se nalazi na adresi http://localhost:8080/newys
