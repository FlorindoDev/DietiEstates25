package org.ma.applyChangeAcquirente;

import org.dao.Interfacce.AcquirenteDAO;
import org.dao.postgre.AcquirentePostgreDAO;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ma.MainApp.ManagementAccount;
import org.md.Utente.Acquirente;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class applyChangeAcquirenteTest {

    private ManagementAccount service;
    private AcquirenteDAO mockDAO;

    @BeforeEach
    void setUp() throws Exception {
        service = new ManagementAccount();
        AcquirenteDAO mockDAO = mock(AcquirentePostgreDAO.class);

        // Iniezione del mock nel ManagementAccount tramite reflection

        Field daoField = ManagementAccount.class.getDeclaredField("acquirenteDAO");
        daoField.setAccessible(true);
        daoField.set(service, mockDAO);

//        service.setDao(mockDAO);
    }

    @Test
    @DisplayName("TO1: Nome non valido")
    void testApplyChangeAcquirente_to1() throws DietiEstateException {

        // Arrange
//        Acquirente utente = new Acquirente();
//        utente.setNome("lownametest");
//        utente.setCognome("Namikaze");
//        utente.setEmail("mario@example.com");
//        utente.setPassword("password123");
//
//        // Act
//        String result = service.applyChangeAcquirente(utente);
//
//        // Assert
//        assertEquals("UserGeneralityNotValid", result);
//        verify(mockDAO, never()).updateUser(any(Acquirente.class));

    }

}

//import org.exc.DataBaseException.UserAlreadyExists;
//import org.exc.DietiEstateException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.md.Utente.Admin;
//import org.adm.MainApp.ManagementAdmin;
//
//import static org.mockito.Mockito.*;
//        import static org.mockito.ArgumentMatchers.eq;
//
//class TestAddAdmin {
//    AdminPostgreDAO adminPostgreDAO = null;
//
//    @BeforeEach
//    void setUp() {
//        adminPostgreDAO = mock(AdminPostgreDAO.class);
//    }
//
//    @Test
//    void TestPassCorrectWrongEmail_1() throws DietiEstateException {
//        Admin admin = new Admin();
//
//        ManagementAdmin  managementAdmin = new ManagementAdmin();
//
//        when(adminPostgreDAO.isUserAbsentOverAll(argThat(a ->
//                a.getEmail().equals("admin@dominio.com")
//        ))).thenThrow(new UserAlreadyExists());
//
//        managementAdmin.setAdminDAO(adminPostgreDAO);
//    }
//}