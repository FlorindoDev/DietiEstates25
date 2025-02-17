# 1) crea il file di configurazione kong
Il file kong.yml è una configurazione declarativa utilizzata da Kong Gateway per definire le configurazioni di servizi, route, plugin, consumatori e altro ancora, senza dover eseguire comandi HTTP o CLI manuali.

```yml
_format_version: "3.0"
_transform: true

services:
- host: httpbin.konghq.com
  name: example_service
  port: 80
  protocol: http
  routes:
  - name: example_route
    paths:
    - /mock
    strip_path: true
```

# 2) avvia il servizio doker

# 3) Create a Docker network

```docker network create kong-net```

You can name this network anything you want. We use kong-net as an example throughout this guide.

This step is not strictly needed for running Kong in DB-less mode, but it is a good precaution in case you want to add other things in the future (like a Rate Limiting plugin backed up by a Redis cluster).

# 4) avvia il container

LINUX version

```
docker run -d --name kong-dbless \
--network=kong-net \
-v "$(pwd):/kong/declarative/" \
-e "KONG_DATABASE=off" \
-e "KONG_DECLARATIVE_CONFIG=/kong/declarative/kong.yml" \
-e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
-e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
-e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
-e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
-e "KONG_ADMIN_LISTEN=0.0.0.0:8001" \
-e "KONG_ADMIN_GUI_URL=http://localhost:8002" \
-e KONG_LICENSE_DATA \
-p 8000:8000 \
-p 8443:8443 \
-p 8001:8001 \
-p 8444:8444 \
-p 8002:8002 \
-p 8445:8445 \
-p 8003:8003 \
-p 8004:8004 \
kong/kong-gateway:3.9.0.1
```

WINDOWS version

```bat
@echo off
docker run -d --name kong-dbless ^
--network=kong-net ^
-v "%cd%:/kong/declarative/" ^
-e "KONG_DATABASE=off" ^
-e "KONG_DECLARATIVE_CONFIG=/kong/declarative/kong.yml" ^
-e "KONG_PROXY_ACCESS_LOG=/dev/stdout" ^
-e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" ^
-e "KONG_PROXY_ERROR_LOG=/dev/stderr" ^
-e "KONG_ADMIN_ERROR_LOG=/dev/stderr" ^
-e "KONG_ADMIN_LISTEN=0.0.0.0:8001" ^
-e "KONG_ADMIN_GUI_URL=http://localhost:8002" ^
-e KONG_LICENSE_DATA ^
-p 8000:8000 ^
-p 8443:8443 ^
-p 8001:8001 ^
-p 8444:8444 ^
-p 8002:8002 ^
-p 8445:8445 ^
-p 8003:8003 ^
-p 8004:8004 ^
kong/kong-gateway:3.9.0.1
```