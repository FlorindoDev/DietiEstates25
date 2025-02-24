# Come sono formate DietiEstateException <br>
Ogni Exception lancera un stringa in formato Json formata nel seguente modo:<br>
- `code:` codice di errore<br>
- `Error:` Speigazione errore <br>

# Tipo di DietiEstateException <br>
Abbiamo due grandi categori: <br>
`DietiEstateDBexception` e `DietiEstateMicroSercviceException` ogni avra le sue sotto exception per i vai errori

# Codici di errore <br>
- `0:`Successo <br>
- `1-100:` Errori DB <br>
- `101-200:` Errori Mirco servizzi

## Lista

- `0:`Successo <br>
- `1:` Utente non trovato nel DB <br>
- `2:` Errore query non eseguita <br>
- `3:` Utente gia esiste <br>
- `4:` Agnecy gia esiste <br>
- `5:` nome Agnecy gia esiste <br>
- `6:` Utente non esiste <br>
- `7:` Appuntamento gia esiste <br>
- `8:` Errore aggiornamento appuntamento <br>
- `101:` Email non valida <br>
- `102:` Password non valida <br>
- `103:` nome agenzia non valido <br>
- `104:` Partita iva non valida <br>
- `105:` Sede non valida <br>
- `106:` Data non valida <br>
