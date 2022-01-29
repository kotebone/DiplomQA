[![master](https://github.com/kotebone/DiplomQA/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/kotebone/DiplomQA/actions/workflows/gradle-publish.yml)

### Инструкция по запуску приложения и тестов:

1. Склонировать репозиторий https://github.com/kotebone/DiplomQA.git
2. Запустить  базы данных командой в терминале docker-compose up -d
3. Запустить приложение в терминале командой:
через PostgreSQL - java -Dspring/datasource/url=jdbc:postgresql://localhost:5432/db -jar artifacts/aqa-shop.jar
через MySQL - java -Dspring/datasource/url=jdbc:mysql://localhost:3306/db -jar artifacts/aqa-shop.jar
4. Запустить тесты 
5. Открыть отчеты о проведенных тестах командой в терминале gradlew allureServe
6. Свернуть Docker-контейнеры в терминале командой docker-compose down
