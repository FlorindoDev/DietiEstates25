version: "3.8"
# File di configurazione docker di SonarQube: docker-compose.yml 

```yml
services:
  sonarqube:
    image: sonarqube:lts
    container_name: sonarqube
    depends_on:
      - db
    ports:
      - "9000:9000"
    environment:
      SONAR_JDBC_URL: jdbc:postgresql://db:5432/sonarqube
      SONAR_JDBC_USERNAME: sonar
      SONAR_JDBC_PASSWORD: sonar
    volumes:
      - sonarqube_data:/opt/sonarqube/data
      - sonarqube_logs:/opt/sonarqube/logs
      - sonarqube_extensions:/opt/sonarqube/extensions

  db:
    image: postgres:13
    container_name: sonarqube_db
    environment:
      POSTGRES_USER: sonar
      POSTGRES_PASSWORD: sonar
      POSTGRES_DB: sonarqube
    volumes:
      - postgresql_data:/var/lib/postgresql/data

volumes:
  sonarqube_data:
  sonarqube_logs:
  sonarqube_extensions:
  postgresql_data:
```

# avvia il servizio

```
docker-compose up -d
```

# Accedi a SonarQube in locale su:

```
http://localhost:9000
```

Credenziali predefinite:
User: admin
Password: admin


# Accesso post configurazione

- Clonare il repo, assicurarsi di avere Docker installato.
- Lanciare il comando docker-compose up -d.
- Tutti avranno accesso agli stessi progetti e alla cronologia grazie ai volumi condivisi, che mantengono i dati anche se i container vengono rimossi.

# in caso di errore provare questo comando
Aumento della memoria <br>
`sudo sysctl -w vm.max_map_count=262144`
