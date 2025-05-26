#  Automated Testing Project for Dashboard & Widgets (UI + API)

##  Структура проекта

Проект состоит из двух частей:

### 1.UI Тесты (UITests):
  -  Что проверяют: Добавление нового виджета на дашборд через веб-интерфейс.
  -  Фреймворк: JUnit 5 + Selenide + Allure.
  -  Аннотации: *`@Epic`*, *`@Feature`*, *`@Story`*, *`@Description`*, *`@DisplayName`* для отображения в Allure.
  -  Действия:
     -  Открытие дашборда
     -  Добавление виджета
     -  Проверка отображения
     -  Удаление виджета

### 2.API Тесты (DashboardApiTests):

Разделены на два *`@Nested`* класса:

  - PositiveCases – успешные создания дашборда (статус 201), с параметрами из *`dashboards_valid.csv`*.
  - NegativeCases – попытки создать дашборды с ошибками (400, 409), с параметрами из *`dashboards_invalid.csv`*.

Функциональность:

  - Проверка обязательных полей (*`name`*, *`description`*)
  - Валидация ограничений по длине и пустым значениям
  - Проверка дублирования имен (409)
  - Очистка данных после тестов (удаление дашбордов)

> **Примечание:** файл `src/main/java/webproject/Main.java` не используется и не влияет на проект. Весь рабочий код и тесты находятся в `src/test`.

###  Структура

```text
src/
└── test/
    ├── java/
    │   ├── apiproject/
    │   │   ├── configurations/           # Конфигурации (базовый URL, токены)
    │   │   │   └── TestConfig.java
    │   │   ├── controllers/              # REST API-контроллер
    │   │   │   └── DashboardController.java
    │   │   ├── models/                   # Модели данных для десериализации
    │   │   │   └── Dashboard.java
    │   │   └── tests/                    # API тесты
    │   │       └── DashboardApiTests.java
    │   └── webproject/
    │       ├── base/                     # Базовые классы тестов
    │       │   ├── AuthorizedTest.java
    │       │   └── UITestBase.java
    │       ├── elements/                 # Общие элементы UI
    │       │   └── MainMenu.java
    │       ├── modaldialogs/             # UI-диалоги
    │       │   ├── AddNewWidgetDialog.java
    │       │   └── DeleteWidgetDialog.java
    │       ├── pages/                    # Страницы UI
    │       │   ├── AllDashboardsPage.java
    │       │   ├── DashboardPage.java
    │       │   └── LoginPage.java
    │       ├── tests/                    # UI тесты
    │       │   └── UITests.java
    │       └── utils/                    # Утилиты
    │           ├── CustomActions.java
    │           └── DashboardUtils.java
    └── resources/
        ├── testdata/
        │   ├── dashboards_valid.csv      # Наборы данных для позитивных API тестов
        │   └── dashboards_invalid.csv    # Наборы данных для негативных API тестов
        ├── logback.xml                   # Конфигурация логгирования
        └── reportportal.properties       # ReportPortal конфигурация (если используется)
```

---

##  Запуск тестов

###  Предварительные требования

Перед запуском проекта убедитесь, что на машине установлены следующие компоненты:

####  Установленное ПО

| Компонент                      | Версия                                       | Комментарий                                                                                                          |
| ------------------------------ | -------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| **Java Development Kit (JDK)** | `17` или **выше** (желательно `21` или `23`) | В `pom.xml` указано `23`, но JDK 23 может не поддерживаться некоторыми инструментами. Рекомендуется JDK **21** (LTS) |
| **Maven**                      | `3.9.x` или выше                             | Для сборки проекта и запуска тестов                                                                                  |
| **Allure CLI** *(опционально)* | `2.29.1` или совместимая                     | Для генерации отчетов: [Инструкция](https://docs.qameta.io/allure/#_installing_a_commandline)                        |

###  Зависимости Maven

Проект использует следующие ключевые библиотеки:
  - JUnit 5 5.11.4 — модульные тесты
  - AssertJ 3.27.3 — удобные fluent-проверки
  - Rest Assured 5.5.2 — тестирование REST API
  - Selenium 4.32.0 + Selenide 7.9.2 — UI тестирование
  - Jackson Databind 2.19.0 — работа с JSON
  - Allure JUnit5 и Allure Rest-Assured 2.29.1 — интеграция отчетов
  -  SLF4J + Logback — логирование

###  Maven плагины
  
  - *`maven-surefire-plugin`* — запуск тестов
  - *`allure-maven — генерация Allure-отчета`*

---

###  Команды для запуска

#### 1.  Запуск всех тестов (UI + API)

```text
mvn clean test
```
#### 2.  Запуск только UI-тестов

```text
mvn test -Dtest=UITests
```
#### 3.  Запуск только API-тестов

```text
mvn test -Dtest=DashboardApiTests
```

###  Формирование Allure-отчета

#### 1.  Генерация отчета после тестов:

```text
allure serve target/allure-results
```
#### 2.  Сохранение отчета локально:

```text
allure generate target/allure-results --clean -o target/allure-report
```
#### 2.  Открытие сохраненного отчета:

```text
allure open target/allure-report
```

###  Наборы тестовых данных

**`dashboards_valid.csv`**
```text
description,name,testType
"Basic description 001","db1","Min length name"
"Basic description 002","Maximal_length_name_...","Max length name"
"Basic description 003","1234567890!@#$%^&*(){}_-[]?:;<>~|/","All special characters"
,"Test_dashboard_004","No description"
```

**`dashboards_invalid.csv`**
```text
description,name,message,testType
"Empty name dashboard",,"Incorrect Request. [Field 'name' should not be null.] ","Empty name"
"Dashboard with short name","D1","Incorrect Request. [Field 'name' should have size from '3' to '128'.] ","Short name"
"Dashboard with long name","Maximal_length_name_..._M","Incorrect Request. [Field 'name' should have size from '3' to '128'.] ","Long name"
"Dashboard with space in name","   ","Incorrect Request. [Field 'name' should not contain only white spaces and shouldn't be empty. Field 'name' should have size from '3' to '128'.] ","Spaces only"
```
