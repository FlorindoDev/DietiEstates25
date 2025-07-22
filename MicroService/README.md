# MicroService

Questa cartella contiene tutti i microservizi del progetto DietiEstates25.

## Build delle immagini Docker

Per buildare tutte le immagini Docker dei microservizi, utilizza lo script:

- **Windows:**  
  `ImageBuilder.bat`
- **Linux/Mac:**  
  `ImageBuilder.sh`

Questi script eseguono il build di tutte le immagini necessarie tramite Docker.

## Variabili d'ambiente

Per impostare le variabili d'ambiente richieste dai microservizi (es. credenziali DB, chiavi, client id Google), utilizza:

- **Windows:**  
  `SetEnvForProduction.bat`
- **Linux/Mac:**  
  `SetEnvForProduction.sh`

Questi script configurano automaticamente tutte le variabili d'ambiente necessarie per l'esecuzione in produzione.

## Avvio dei servizi

Dopo aver buildato le immagini e impostato le variabili d'ambiente, puoi avviare tutti i servizi con:

```sh
docker compose up -d
```

Questo comando utilizza il file [`docker-compose.yaml`](docker-compose.yaml) per avviare tutti i microservizi in modalità detached.

## Struttura principale

- `AccessService/`
- `AdminManagementService/`
- `AdsEstateService/`
- `AgencyService/`
- `AgentManagementService/`
- `API Gateway/`
- `AppointmentService/`
- `Dependacy/`
- `ManagementAccountService/`
- `NotifyService/`
- `SearchService/`

## Note

- Assicurati di avere Docker installato e configurato.
- Esegui prima lo script di variabili d'ambiente, poi builda le immagini.
- Consulta la documentazione di ciascun microservizio per