SET search_path TO public; SHOW search_path;

CREATE TYPE Modalita AS ENUM ('Vendita', 'Affitto');
CREATE TYPE Stato AS ENUM ('Nuovo', 'Ottimo', 'Buono', 'Da ristrutturare');
CREATE TYPE NotificaTipo AS ENUM ('Appuntamento Accettato', 'Appuntamento Rifiutato', 'Appuntamento Da Confermare', 'Nuovo Immobile', 'Modifica Prezzo Immobile','Immobile Cancellato');

-- Creazione della tabella Indirizzo
CREATE TABLE Indirizzo (
    IdIndirizzo SERIAL PRIMARY KEY,
    Stato VARCHAR(50) NOT NULL,
    Citta VARCHAR(50) NOT NULL,
    Quartiere VARCHAR(20),
    Via VARCHAR(100) NOT NULL,
    NumeroCivico VARCHAR(20) NOT NULL,
    CAP INT NOT NULL
);

-- Creazione della tabella Agenzia Immobiliare
CREATE TABLE AgenziaImmobiliare (
    PartitaIVA VARCHAR(20) PRIMARY KEY,
    Nome VARCHAR(100) UNIQUE NOT NULL,
    Sede VARCHAR(100) NOT NULL,
    idPushNotify VARCHAR(100),
    notify_appointment BOOLEAN NOT NULL
);

-- Creazione della tabella Agente Immobiliare
CREATE TABLE AgenteImmobiliare (
    IdAgente SERIAL PRIMARY KEY,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Biografia TEXT,
    ImmagineProfilo TEXT NOT NULL,
    Nome VARCHAR(50),
    Cognome VARCHAR(50),
    Password VARCHAR(255) NOT NULL,
    PartitaIVA VARCHAR(20),
    FOREIGN KEY (PartitaIVA) REFERENCES AgenziaImmobiliare(PartitaIVA) ON DELETE CASCADE
);

-- Creazione della tabella Amministratore
CREATE TABLE Amministratore (
    IdAmministratore SERIAL PRIMARY KEY,
    Email VARCHAR(100) UNIQUE NOT NULL,
    IsSupportoAmministratore BOOLEAN NOT NULL,
    Nome VARCHAR(50) NOT NULL,
    Cognome VARCHAR(50) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    PartitaIVA VARCHAR(20),
    FOREIGN KEY (PartitaIVA) REFERENCES AgenziaImmobiliare(PartitaIVA) ON DELETE CASCADE
);

-- Creazione della tabella Immobile
CREATE TABLE Immobile (
    IdImmobile SERIAL PRIMARY KEY,
    idAgente INT NOT NULL,
    idIndirizzo INT NOT NULL,
    partitaiva VARCHAR(20) NOT NULL,
    Foto TEXT NOT NULL,
    Descrizione TEXT NOT NULL,
    Prezzo DECIMAL(10, 2) NOT NULL,
    Dimensioni INT NOT NULL,
    Stanze INT NOT NULL,
    Piano INT NOT NULL,
    Bagni INT NOT NULL,
    Garage INT NOT NULL,
    Ascensore BOOLEAN NOT NULL,
    ClasseEnergetica VARCHAR(10) NOT NULL,
    Modalita Modalita NOT NULL,
    Stato Stato NOT NULL,
    FOREIGN KEY (idAgente) REFERENCES AgenteImmobiliare(idAgente),
    FOREIGN KEY (idIndirizzo) REFERENCES Indirizzo(idIndirizzo),
    FOREIGN KEY (idAgenzia) REFERENCES AgenziaImmobiliare(PartitaIVA) ON DELETE CASCADE
);

CREATE TABLE FotoImmobile (
    IdFoto SERIAL PRIMARY KEY,
    Foto BYTEA UNIQUE NOT NULL,
	idImmobile INT NOT NULL,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);


ALTER TABLE Immobile
    ADD CONSTRAINT IsValidDimensioni CHECK (Dimensioni > 0),
    ADD CONSTRAINT IsValidStanze CHECK (Stanze > 0),
    ADD CONSTRAINT IsValidPiano CHECK (Piano > 0),
    ADD CONSTRAINT IsValidPrezzo CHECK (Prezzo > 0);

-- Creazione della tabella Acquirente
CREATE TABLE Acquirente (
    IdAcquirente SERIAL PRIMARY KEY,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Nome VARCHAR(50) NOT NULL,
    Cognome VARCHAR(50) NOT NULL,
    Password VARCHAR(255) NOT NULL
    idPushNotify VARCHAR(100),
    notify_appointment BOOLEAN NOT NULL,
    notify_new_estate BOOLEAN NOT NULL,
    change_price_notify BOOLEAN NOT NULL
);

-- Creazione della tabella Notifiche Appuntamento
CREATE TABLE Appuntamento (
    IdAppuntamento SERIAL PRIMARY KEY,
    Esito VARCHAR(20) NOT NULL,
    Data DATE NOT NULL,
	idAcquirente INT NOT NULL,
	idImmobile INT NOT NULL,
    FOREIGN KEY (idAcquirente) REFERENCES Acquirente(idAcquirente) ON DELETE CASCADE,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- Creazione della tabella Notifiche Immobilie
CREATE TABLE Notifica (
    IdNotifica SERIAL PRIMARY KEY,
    TipoNotifica NotificaTipo NOT NULL, 
    Messaggio TEXT NOT NULL,
    Data DATE NOT NULL,
	idAcquirente INT NOT NULL,
	idImmobile INT NOT NULL,
    FOREIGN KEY (idAcquirente) REFERENCES Acquirente(idAcquirente) ON DELETE CASCADE,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- Creazione della tabella Feedback
CREATE TABLE Feedback (
    IdFeedback SERIAL PRIMARY KEY,
    NumeroStelle INT NOT NULL CHECK (NumeroStelle BETWEEN 0 AND 5),
    Messaggio TEXT NOT NULL,
	idAgente INT NOT NULL,
	idAcquirente INT NOT NULL,
    FOREIGN KEY (idAcquirente) REFERENCES Acquirente(idAcquirente),
    FOREIGN KEY (idAgente) REFERENCES AgenteImmobiliare(idAgente)
);

ALTER TABLE Indirizzo
    ADD CONSTRAINT IsValidNumeroCivico CHECK (NumeroCivico > 0);

-- Creazione della tabella Ricerca
CREATE TABLE Ricerca (
    IdRicerca SERIAL PRIMARY KEY,
    Comando TEXT NOT NULL,
	idAcquirente INT NOT NULL,
    FOREIGN KEY (idAcquirente) REFERENCES Acquirente(idAcquirente) ON DELETE CASCADE
);