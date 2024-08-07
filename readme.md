## Демо проект различных подходов и библиотек для работы с GRPC API.

Этот демо-проект исследует различные подходы и библиотеки для работы с gRPC API. Проект организован в виде многомодульного приложения Maven, где каждый модуль фокусируется на разных аспектах разработки с использованием gRPC.

### Структура проекта
* gateway-service: Cервис шлюза rest-api.
  * Используемые технологии:
    * Java 21
    * Spring Boot: 3.2.3
    * GRPC библиотека: net.devh:grpc-client-spring-boot-starter:3.0.0.RELEASE
* grpc-user-cache-server: gRPC-сервис для кеширования данных пользователей.
  * Используемые технологии:
    * Java 21
    * Spring Boot: 3.2.3
    * GRPC библиотека: io.github.lognet:grpc-spring-boot-starter:5.1.5
* grpc-user-service: gRPC-сервис пользователей.
  * Используемые технологии:
    * Java 21
    * Spring Boot: 3.3.2
    * GRPC библиотека: net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE
  * Функциональные примеры:
    * Перехватчики запросов для [сервера и клиента](grpc-user-service/src/main/java/com/akhazov/grpc/userservice/config/interceptor). В примерах показан проброс rqUid запроса в логи.
    * Реализация централизованной [обработки исключений](grpc-user-service/src/main/java/com/akhazov/grpc/userservice/error).
    * Валидация входящих gRPC запросов с помощью библиотеки [protovalidate](grpc-user-service/src/main/java/com/akhazov/grpc/userservice/service/validation/GrpcRequestValidationService.java).