## Comandi utili
- `flutter create --platforms=android`
- `flutter emulators --launch flutter_emulator`
- `flutter run -d emulator-5554`
- `mvn clean verify sonar:sonar` # push changes on sonarQube Server

- `docker network connect microservice_dieti-net kong-gateway-dbless` 

- `curl -i -X POST http://localhost:8001/config --data-binary "@kong.yaml" -H "Content-Type: application/yaml"` # per aggiornare dinamicamente king.yml #da eseguire nella dire di kong.yml

# Dev knowleg

- Ricorda di controllare anche i codici proprietari del nostro dominio

## Flutter
- Esiste una variabile globale loggedUser
  - Assegnata durante l'accesso
  - eventuali riferimenti ad essa nei controller possono essere tolti ma rimasti per compatibilità

- La classe Connection contiene anche i path per kong accessibili tramite i suoi campi

- Utilizzare La classe Logger per errori ed informazioni utili per debug ed eliminare le restanti print

## Google
`android/app/src/main/AndroidManifest.xml`
Va messo dentro il tag `<application>` e non dentro `<activity>`:
```xml
<meta-data
    android:name="com.google.android.gms.client.id"
    android:value="@string/server_client_id" />```

```
`android/app/src/main/res/values/strings.xml`
Va creato il file se non esiste, altrimenti va aggiunto il tag `<string>` dentro il tag `<resources>`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="server_client_id">TUO_WEB_CLIENT_ID.apps.googleusercontent.com</string>
</resources>

```
