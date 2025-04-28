## Comandi utili
- `flutter create --platforms=android`
- `flutter emulators --launch flutter_emulator`
- `flutter run -d emulator-5554`
- `mvn clean verify sonar:sonar` # push changes on sonarQube Server

- `docker network connect microservice_dieti-net kong-gateway-dbless` 
`

# Google
`android/app/src/main/AndroidManifest.xml`
Va messo dentro il tag <application> e non dentro <activity>:
```xml
<meta-data
    android:name="com.google.android.gms.client.id"
    android:value="@string/server_client_id" />```

```
`android/app/src/main/res/values/strings.xml`
Va creato il file se non esiste, altrimenti va aggiunto il tag <string> dentro il tag <resources>:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="server_client_id">TUO_WEB_CLIENT_ID.apps.googleusercontent.com</string>
</resources>

```
