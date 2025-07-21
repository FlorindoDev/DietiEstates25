package org.ma.applyChangeAcquirente;

import org.dao.Interfacce.AcquirenteDAO;
import org.dao.postgre.AcquirentePostgreDAO;
import org.exc.DataBaseException.UserAlreadyExists;
import org.exc.DietiEstateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ma.MainApp.ManagementAccount;
import org.md.Utente.Acquirente;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TestApplyChangeAcquirente {

    private ManagementAccount service;
    private AcquirenteDAO mockDAO;

    @BeforeEach
    void setUp() throws Exception {
        service = new ManagementAccount();
        mockDAO = mock(AcquirentePostgreDAO.class);

        // Iniezione del mock nel ManagementAccount tramite reflection
        Field daoField = ManagementAccount.class.getDeclaredField("acquirenteDAO");
        daoField.setAccessible(true);
        daoField.set(service, mockDAO);

    }

    @Test
    @DisplayName("TO1: Nome non valido")
    void testApplyChangeAcquirente_to1() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("lownametest");
        utente.setCognome("Namikaze");
        utente.setEmail("mario@example.com");
        utente.setPassword("password123");

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("TO2: Nome vuoto")
    void testApplyChangeAcquirente_to2() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("");
        utente.setCognome("Namikaze");
        utente.setEmail("mario@example.com");
        utente.setPassword("password123");

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("TO3: aggiornamento con email gia esistente")
    void testApplyChangeAcquirente_to3() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("Namikaze");
        utente.setEmail("mario@example.com");
        utente.setPassword("password123");

        doThrow(new UserAlreadyExists())
                .when(mockDAO).updateUser(any(Acquirente.class));

        // Act
        String result = service.applyChangeAcquirente(utente);


        // Assert
        String expected = "{\"code\": 3 , \"message\": \" User Already Exists\"}";

        assertEquals(expected, result);
        verify(mockDAO, times(1)).updateUser(utente);

    }

    @Test
    @DisplayName("TO4: email senza @")
    void testApplyChangeAcquirente_to4() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("Namikaze");
        utente.setEmail("marioexample.com");
        utente.setPassword("password123");

        doThrow(new UserAlreadyExists())
                .when(mockDAO).updateUser(any(Acquirente.class));

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("TO5: email senza suffisso")
    void testApplyChangeAcquirente_to5() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("Namikaze");
        utente.setEmail("marioe@xample");
        utente.setPassword("password123");

        doThrow(new UserAlreadyExists())
                .when(mockDAO).updateUser(any(Acquirente.class));

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("TO6: email vuota")
    void testApplyChangeAcquirente_to6() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("Namikaze");
        utente.setEmail("");
        utente.setPassword("password123");

        doThrow(new UserAlreadyExists())
                .when(mockDAO).updateUser(any(Acquirente.class));

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("TO7: Cognome vuoto")
    void testApplyChangeAcquirente_to7() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("");
        utente.setEmail("mario@example.com");
        utente.setPassword("password123");

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("TO8: Cognome non valido")
    void testApplyChangeAcquirente_to8() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("namikaze");
        utente.setEmail("mario@example.com");
        utente.setPassword("password123");

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("TO9: Cognome non valido")
    void testApplyChangeAcquirente_to9() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("namikaze");
        utente.setEmail("mario@example.com");
        utente.setPassword("1234567");

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 102 , \"message\": \" User Password not valid\"}", result);
        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

    @Test
    @DisplayName("T10: Aggiornamento dell'Acquirente")
    void testApplyChangeAcquirente_t10() throws DietiEstateException {

        // Arrange
        Acquirente utente = new Acquirente();
        utente.setNome("Minato");
        utente.setCognome("Namikaze");
        utente.setEmail("mario@example.com");
        utente.setPassword("12345679");

        // Act
        String result = service.applyChangeAcquirente(utente);

        // Assert
        assertEquals("{\"code\": 0, \"message\": \"success of update action ManagementAccount\"}", result);
        verify(mockDAO, times(1)).updateUser(utente);
    }

}