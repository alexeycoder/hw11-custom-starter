## Урок 11. Spring Boot Starter.

Проблематика: имеется несколько микросервисов (проектов) на spring-boot: reader-service, book-service, issue-service,...
Хочется, чтобы в каждом из этих проектов работал аспект-таймер, замеряющий время выполнения метода бина, помеченного аннотацией @Timer (см. дз к уроку 8)

Решение: создать стартер, который будет инкапсулировать в себе аспект и его автоматический импорт в подключающий проект.
То есть:
1. Пишем стартер, в котором задекларирован аспект и его работа
2. Подключаем стартер в reader-service, book-service, issue-service, ...

Шаги реализации:
1. Создаем новый модуль в микросервисном проекте - это и будет наш стартер
2. Берем код с ДЗ-8 (класс аспекта и аннотации) и переносим в стартер
3. В стартере декларируем Configuration и внутри нее декларируем бин - аспект
4. В проекте стартера в resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports прописываем полный путь конфигурации
5. Подключаем зависимость стартера (pom-dependency) в микросервисы
6. Проверяем, что аспект работает

### Решение:

Проект стартера: [/execution-timer-starter](execution-timer-starter)

В приложения микро-сервисы *book-service*, *reader-service* и *issue-service* подключена зависимость *execution-timer-starter* предоставляющая аннотацию `@Timer` и авто-конфигурацию, автоматически создающую singleton-бин аспекта `ExecutionTimerAspect` в контексте каждого приложения.

	<dependency>
		<groupId>org.someother</groupId>
		<artifactId>execution-timer-starter</artifactId>
		<version>1.0.0</version>
	</dependency>

*Примечания:*
* Для чистоты эксперимента проект стартера принадлежит иному `groupId`, нежели микро-сервисы &nbsp;&mdash; `org.someother`.
* Классы контроллеров микро-сервисов помечены @Timer.

*Пример работы:*

![Example](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-spring-hw11-starter/example.png)


## Урок 9. Spring Cloud. Микросервисная архитектура.

1\. Восстановить пример, рассмотренный на уроке (запустить эврику и 2 сервиса; заставить их взаимодействовать).

2\.* Добавить третий сервис: сервис читателей.

### Решение:

*Eureka: running micro-services*

![Eureka: running micro-services](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-spring-hw9-eureka/eureka_micro-services.png)
