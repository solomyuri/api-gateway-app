# api-gateway-app

**api-gateway-app** — сервис для маршрутизации запросов к микросервисам в системе, предоставляющий единый входной узел для клиентов. Он служит шлюзом, обеспечивая маршрутизацию, управление доступом, балансировку нагрузки и обработку ошибок.

## Описание

Сервис реализует API-шлюз с использованием **Spring Cloud Gateway** и интеграцией с системой аутентификации и авторизации через **Keycloak**. Основная цель — маршрутизация клиентских запросов к соответствующим микросервисам и выполнение промежуточных задач, таких как проверка токенов, фильтрация и логирование.

## Основной функционал

 **Маршрутизация**:
  - Перенаправление запросов к различным микросервисам на основе URI или других параметров.
  - Динамическое управление маршрутами.

 **Аутентификация и авторизация**:
  - Проверка JWT-токенов, выданных Keycloak.
  - Ограничение доступа к маршрутам на основе ролей.

 **Обработка ошибок**:
  - Генерация унифицированных ответов при возникновении ошибок.
  - Глобальная обработка исключений, включая сетевые ошибки и недоступность микросервисов.

 **Фильтрация**:
  - Возможность добавления пользовательских фильтров для модификации запросов или ответов.

 **Мониторинг и логирование**:
  - Логирование запросов и ответов для аудита и диагностики.
  - Поддержка метрик для мониторинга производительности шлюза.
