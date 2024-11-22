# Operarii: information system of workers and proletarians alike

[![CodeFactor](https://www.codefactor.io/repository/github/17starplatinum/InfSys-I_VAR-100246/badge)](https://www.codefactor.io/repository/github/17starplatinum/InfSys-I_VAR-100246)
___

## Лабораторная работа `№1`

<p align="center">
    <img src="https://media1.tenor.com/m/Jk5eCnw0ekYAAAAC/novosphere-novo.gif" alt="businessmen"/>
</p>

### Вариант `100246`

## Результат: ${\color{white}?}\%$
___
## **_Внимание! У разных вариантов разный текст задания!_**

Реализовать информационную систему, которая позволяет взаимодействовать с объектами класса Worker, описание которого приведено ниже:
```java
public class Worker {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Organization organization; //Поле может быть null
    private Double salary; //Поле может быть null, Значение поля должно быть больше 0
    private int rating; //Значение поля должно быть больше 0
    private Position position; //Поле не может быть null
    private Status status; //Поле может быть null
    private Person person; //Поле не может быть null
}
public class Coordinates {
    private double x; //Максимальное значение поля: 990
    private int y; //Максимальное значение поля: 27
}
public class Organization {
    private Address officialAddress; //Поле не может быть null
    private Float annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private int employeesCount; //Значение поля должно быть больше 0
    private String fullName; //Длина строки не должна быть больше 1576, Строка не может быть пустой, Поле может быть null
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле не может быть null
}
public class Person {
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле не может быть null
    private java.time.LocalDate birthday; //Поле может быть null
    private Double weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Country nationality; //Поле не может быть null
}
public class Address {
    private String zipCode; //Поле не может быть null
    private Location town; //Поле может быть null
}
public class Location {
    private Float x; //Поле не может быть null
    private long y;
    private Long z; //Поле не может быть null
}
public enum Position {
    DIRECTOR,
    LABORER,
    BAKER
}
public enum Status {
    FIRED,
    HIRED,
    RECOMMENDED_FOR_PROMOTION,
    REGULAR
}
public enum OrganizationType {
    COMMERCIAL,
    PUBLIC,
    GOVERNMENT,
    TRUST,
    PRIVATE_LIMITED_COMPANY
}
public enum Color {
    GREEN,
    BLACK,
    BLUE,
    ORANGE
}
public enum Country {
    UNITED_KINGDOM,
    FRANCE,
    NORTH_KOREA
}
```
**Разработанная система должна удовлетворять следующим требованиям:**

- Основное назначение информационной системы — управление объектами, созданными на основе заданного в варианте класса. 
- Необходимо, чтобы с помощью системы можно было выполнить следующие операции с объектами: создание нового объекта, получение информации об объекте по ИД, обновление объекта (модификация его атрибутов), удаление объекта. Операции должны осуществляться в отдельных окнах (интерфейсах) приложения. При получении информации об объекте класса должна также выводиться информация о связанных с ним объектах. 
- При создании объекта класса необходимо дать пользователю возможность связать новый объект с объектами вспомогательных классов, которые могут быть связаны с созданным объектом и уже есть в системе. 
- Выполнение операций по управлению объектами должно осуществляться на серверной части (не на клиенте), изменения должны синхронизироваться с базой данных. 
- На главном экране системы должен выводиться список текущих объектов в виде таблицы (каждый атрибут объекта — отдельная колонка в таблице). При отображении таблицы должна использоваться пагинация (если все объекты не помещаются на одном экране). 
- Нужно обеспечить возможность фильтровать/сортировать строки таблицы, которые показывают объекты (по значениям любой из строковых колонок). Фильтрация элементов должна производиться только по полному совпадению.
- Переход к обновлению (модификации) объекта должен быть возможен из таблицы с общим списком объектов и из области с визуализацией объекта (при ее реализации). 
- При добавлении/удалении/изменении объекта, он должен автоматически появиться/исчезнуть/измениться в интерфейсах у других пользователей, авторизованных в системе. 
- Если при удалении объекта с ним связан другой объект, операция должна быть отменена, пользователю нужно сообщить о невозможности удаления объекта. 
- Пользователю системы должен быть предоставлен интерфейс для авторизации/регистрации нового пользователя. У каждого пользователя должен быть один пароль. Требования к паролю: пароль должен быть уникален _(Авторское примечание: бро што :skull:)_. В системе предполагается использование следующих видов пользователей (ролей): незарегистрированные пользователи, обычные пользователи и администраторы. Если в системе уже создан хотя бы один администратор, зарегистрировать нового администратора можно только при одобрении одним из существующих администраторов (у администратора должен быть реализован интерфейс со списком заявок и возможностью их одобрения). 
- Редактировать и удалять объекты могут только пользователи, которые их создали, и администраторы (администраторы могут удалять все объекты).
- Зарегистрированные пользователи должны иметь возможность просмотра всех объектов, но модифицировать (обновлять) могут только принадлежащие им (объект принадлежит пользователю, если он его создал). Для модификации объекта должно открываться отдельное диалоговое окно. При вводе некорректных значений в поля объекта должны появляться информативные сообщения о соответствующих ошибках. 

**В системе должен быть реализован отдельный пользовательский интерфейс для выполнения специальных операций над объектами:**

- Удалить один (любой) объект, значение поля person которого эквивалентно заданному. 
- Вернуть количество объектов, значение поля person которых равно заданному. 
- Вернуть количество объектов, значение поля rating которых меньше заданного. 
- Уволить сотрудника с заданным id из организации. 
- Переместить сотрудника из одной организации в другую с сохранением должности и заработной платы.

**Представленные операции должны быть реализованы в рамках компонентов бизнес-логики приложения без прямого использования функций и процедур БД.**

**Особенности хранения объектов, которые должны быть реализованы в системе:**

- Организовать хранение данных об объектах в реляционной СУБД (PostgreSQL). Каждый объект, с которым работает ИС, должен быть сохранен в базе данных.
- Все требования к полям класса (указанные в виде комментариев к описанию классов) должны быть выполнены на уровне ORM и БД.
- Для генерации поля id использовать средства базы данных.
- Пароли при хранении хэшировать алгоритмом `MD5` _(Авторское примечание: это чушь полная, используйте SHA-128 минимум)_.
- При хранении объектов сохранять информацию о пользователе, который создал этот объект, а также фиксировать даты и пользователей, которые обновляли и изменяли объекты. Для хранения информации о пользователях и об изменениях объектов нужно продумать и реализовать соответствующие таблицы.
- Таблицы БД, не отображающие заданные классы объектов, должны содержать необходимые связи с другими таблицами и соответствовать 3НФ.
- Для подключения к БД на кафедральном сервере использовать хост pg, имя базы данных — studs, имя пользователя/пароль совпадают с таковыми для подключения к серверу.

**При создании системы нужно учитывать следующие особенности организации взаимодействия с пользователем:**

- Система должна реагировать на некорректный пользовательский ввод, ограничивая ввод недопустимых значений и информируя пользователей о причине ошибки.
- Переходы между различными логически обособленными частями системы должны осуществляться с помощью меню.
- Во всех интерфейсах системы должно быть реализовано отображение информации о текущем пользователе (кто авторизован) и предоставляться возможность изменить текущего пользователя.
- [Опциональное задание — +2 балл] В отдельном окне ИС должна осуществляться визуализация объектов коллекции. При визуализации использовать данные о координатах и размерах объекта. Объекты от разных пользователей должны быть нарисованы разными цветами. При нажатии на объект должна выводиться информация об этом объекте.
- При добавлении/удалении/изменении объекта, он должен автоматически появиться/исчезнуть/измениться на области у всех других клиентов.

**При разработке ИС должны учитываться следующие требования:**

- В качестве основы для реализации ИС необходимо использовать `Spring MVC`.
- Для создания уровня хранения необходимо использовать `Hibernate`.
- Разные уровни приложения должны быть отделены друг от друга, разные логические части ИС должны находиться в отдельных компонентах.

**Содержание отчёта:**

1. Текст задания. 
2. UML-диаграммы классов и пакетов разработанного приложения. 
3. Исходный код системы или ссылка на репозиторий с исходным кодом. 
4. Выводы по работе.

**Вопросы к защите лабораторной работы:**

1. Шаблоны проектирования и архитектурные шаблоны.
2. Платформа `Jakarta EE`. Виды компонентов. 
3. `Jakarta EE`. Управляемые бины. CDI-бины. 
4. Концепция `ORM`. Библиотеки ORM `Hibernate` и `EclipseLink`. Особенности, API, сходства и отличия. 
5. Технология `Jakarta Persistence`. Особенности, API, интеграция с ORM-провайдерами. 
6. Технология `Jakarta Data`. 
7. Платформа `Spring`. Сходства и отличия с `Java EE`. 
8. `Spring Boot`. 
9. `Spring Data`.

___
### Инфологическая модель Operarii: [тык](/resources/Logical-View.png)
### Даталогическая модель Operarii: [тык](/resources/Data-View.png)
