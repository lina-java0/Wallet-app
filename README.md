Тренировочное приложение для освоения Spring, REST API, Docker.
Иммитирует простую логику приложения кошелька для снятия и пополнения баланса.
Задание нашла на просторах интернета. Попробовала реализовать с помощью чата GPT.

Само ТЗ:
Напишите приложение, которое по REST принимает запрос вида:
POST api/v1/wallet
{
valletId: UUID,
operationType: DEPOSIT or WITHDRAW,
amount: 1000
}
после выполнять логику по изменению счета в базе данных
также есть возможность получить баланс кошелька
GET api/v1/wallets/{WALLET_UUID}
стек:
java 8-17
Spring 3
Postgresql
Предусмотрите соблюдение формата ответа для заведомо неверных запросов, когда
кошелька не существует, не валидный json, или недостаточно средств.
приложение должно запускаться в докер контейнере, база данных тоже, вся система
должна подниматься с помощью docker-compose

