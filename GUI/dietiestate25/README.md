`<uses-permission android:name="android.permission.INTERNET" />`

# Windows

- Aquirente (Home)
- Agente (AgentHome)
- Amministratore

# Sistemi di Navigazione

- Principale: RouteWindow
  - stabilisce a tempo di login quale gui chiamare in base al kid del JWT
  - RouteWidow chiama i buildere della pagina spacifica generale la qule poi provvederà a gestire la sua innerRoute

# Note

- static AppBar appBarNotBackable = AppBar(); # Logo
- screenShooser di AccessController determina il tipo di utente tramite JWT
 - chiamato in toLogin in AccessController

# Logger

Livelli di log supportati
logger.v() → verbose
logger.d() → debug
logger.i() → info
logger.w() → warning
logger.e() → error
logger.wtf() → What a Terrible Failure (usalo per crash o casi assurdi)

# Comandi Shell interattiva di Flutter
r Hot reload.
R Hot restart.
h List all available interactive commands.
d Detach (terminate "flutter run" but leave application running).
c Clear the screen
q Quit (terminate the application on the device).