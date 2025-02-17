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