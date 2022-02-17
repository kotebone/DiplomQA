[![master](https://github.com/kotebone/DiplomQA/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/kotebone/DiplomQA/actions/workflows/gradle-publish.yml)

### Инструкция по запуску приложения и тестов:

1. Склонировать репозиторий https://github.com/kotebone/DiplomQA.git
2. Запустить  базы данных командой в терминале **docker-compose up -d**
3. Запустить приложение в терминале командой:
для PostgreSQL - **java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar** 
для MySQL - **java -Ddb.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar**
4. Запустить тесты командой в терминале **gradlew clean test**
5. Сгенерировать отчеты о тестировании командой в терминале **gradlew allureReport**
6. Открыть отчеты о проведенных тестах командой в терминале **gradlew allureServe**
7. Свернуть Docker-контейнеры в терминале командой **docker-compose down**
