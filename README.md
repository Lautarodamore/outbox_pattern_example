## Start Database
`docker volume create outbox-pattern-pg-data`

`docker run --name outbox-pattern-pg --restart always -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=outbox -p 5532:5432 -v outbox-pattern-pg-data:/var/lib/postgresql/data -d postgres:12-alpine`

## Start RabbitMQ
`docker run -p 15672:15672 -p 5672:5672 --name outbox-rabbit --restart always -d rabbitmq:3.11-management`
