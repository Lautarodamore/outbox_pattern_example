## Start Database
`docker volume create outbox-pattern-pg-data`

`docker run --name outbox-pattern-pg --restart always -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=outbox -p 5532:5432 -v outbox-pattern-pg-data:/var/lib/postgresql/data -d postgres:12-alpine`
