FROM kong/kong-gateway:3.9.0.1

# Aggiungi permessi di scrittura alla directory (se già esistente)
RUN chmod 777 /kong || true

# Copia il file kong.yaml nel container
COPY kong.yaml /kong/kong.yaml

# Impostare la variabile di ambiente per la configurazione
ENV KONG_DECLARATIVE_CONFIG=/kong/kong.yaml

# Altri comandi di configurazione...