# DOC ufficiale
- https://docs.konghq.com/gateway/latest/

# 1) Crea i file di configurazione postgre

1-postgre_container.yml
```yml
services:
  kong-database:
    image: postgres:13
    container_name: kong-database
    networks:
      - kong-net
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: kong
      POSTGRES_DB: kong
      POSTGRES_PASSWORD: kongpass

networks:
  kong-net:
    driver: bridge
```

2-bridge_postre_kong.yml
```yml
services:
  kong-migrations:
    image: kong/kong-gateway:3.9.0.1
    container_name: kong-migrations
    networks:
      - kong-net
    environment:
      KONG_DATABASE: postgres
      KONG_PG_HOST: kong-database
      KONG_PG_PASSWORD: kongpass
      KONG_PASSWORD: test
    command: kong migrations bootstrap
    depends_on:
      - kong-database

  kong-database:
    image: postgres:13
    container_name: kong-database
    networks:
      - kong-net
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: kong
      POSTGRES_DB: kong
      POSTGRES_PASSWORD: kongpass

networks:
  kong-net:
    driver: bridge
```


# 2) Crea il file di configurazione kong
Il file kong.yml è una configurazione declarativa utilizzata da Kong Gateway per definire le configurazioni di servizi, route, plugin, consumatori e altro ancora, senza dover eseguire comandi HTTP o CLI manuali.

3-kong_container.yml
```yml
services:
  kong-gateway:
    image: kong/kong-gateway:3.9.0.1
    container_name: kong-gateway
    networks:
      - kong-net
    environment:
      KONG_DATABASE: postgres
      KONG_PG_HOST: kong-database
      KONG_PG_USER: kong
      KONG_PG_PASSWORD: kongpass
      KONG_PROXY_ACCESS_LOG: /dev/stdout
      KONG_ADMIN_ACCESS_LOG: /dev/stdout
      KONG_PROXY_ERROR_LOG: /dev/stderr
      KONG_ADMIN_ERROR_LOG: /dev/stderr
      KONG_ADMIN_LISTEN: 0.0.0.0:8001
      KONG_ADMIN_GUI_URL: http://localhost:8002
      KONG_LICENSE_DATA: ${KONG_LICENSE_DATA}
    ports:
      - "8000:8000"
      - "8443:8443"
      - "8001:8001"
      - "8444:8444"
      - "8002:8002"
      - "8445:8445"
      - "8003:8003"
      - "8004:8004"
    depends_on:
      - kong-database

  kong-database:
    image: postgres:13
    container_name: kong-database
    networks:
      - kong-net
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: kong
      POSTGRES_DB: kong
      POSTGRES_PASSWORD: kongpass

networks:
  kong-net:
    driver: bridge
```

# 3) avvia il servizio doker

# 3) Create a Docker network

```docker network create kong-net```

You can name this network anything you want. We use kong-net as an example throughout this guide.

This step is not strictly needed for running Kong in DB-less mode, but it is a good precaution in case you want to add other things in the future (like a Rate Limiting plugin backed up by a Redis cluster).


# 4) lancia i comandi:
- docker-compose -f .\1-postgre_container.yml up -d
- docker-compose -f .\2-bridge_postre_kong.yml up -d
- docker-compose -f .\3-kong_container.yml up -d