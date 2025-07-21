package org.adm.addAdmin;

import org.dao.postgre.AdminPostgreDAO;
import org.exc.DataBaseException.UserAlreadyExists;
import org.exc.DietiEstateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.adm.MainApp.ManagementAdmin;
import static org.mockito.Mockito.*;


class TestAddAdmin {
    AdminPostgreDAO adminPostgreDAO = null;

    @BeforeEach
    void setUp() {
        adminPostgreDAO = mock(AdminPostgreDAO.class);
    }

    // T01: Successo
    @Test
    void testT01_successo() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 0, \"message\": \"success of action admin create\"}", managementAdmin.addAdmin(admin));
    }

    // T02: Errore nome vuoto
    @Test
    void testT02_nomeVuoto() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("")  // nome vuoto
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T03: Errore nome minuscolo
    @Test
    void testT03_nomeMinuscolo() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("marco")  // nome minuscolo
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T04: Errore nome con numero
    @Test
    void testT04_nomeConNumero() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("Mar1o")  // nome con numero
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T05: Errore cognome vuoto
    @Test
    void testT05_cognomeVuoto() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("")  // cognome vuoto
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T06: Password troppo corta
    @Test
    void testT06_passwordCorta() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcd")  // password corta
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 102 , \"message\": \" User Password not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T07: Password vuota
    @Test
    void testT07_passwordVuota() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("")  // password vuota
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 102 , \"message\": \" User Password not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T08: Email senza '@'
    @Test
    void testT08_emailSenzaChiocciola() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marcodominio.com")  // email senza '@'
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T09: Email senza suffisso (es. .com)
    @Test
    void testT09_emailSenzaSuffisso() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio")  // email senza suffisso
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T10: Email vuota
    @Test
    void testT10_emailVuota() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "")  // email vuota
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAdmin.addAdmin(admin));
    }

    // T11: Partita IVA vuota
    @Test
    void testT11_pIvaVuota() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("").build())  // P.IVA vuota
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 104 , \"message\": \" PartitaIVA of agency not valid: is to short or is to long\"}", managementAdmin.addAdmin(admin));
    }

    // T12: Partita IVA troppo corta
    @Test
    void testT12_pIvaCorta() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("1234567").build())  // P.IVA corta
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        when(adminPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAdmin.setAdminDAO(adminPostgreDAO);

        assertEquals("{\"code\": 104 , \"message\": \" PartitaIVA of agency not valid: is to short or is to long\"}", managementAdmin.addAdmin(admin));
    }

    // T13: Email già esistente
    @Test
    void testT13_emailGiaEsistente() throws DietiEstateException {
        Admin admin = new Admin.Builder(13, "admin@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAdmin managementAdmin = new ManagementAdmin();
        managementAdmin.setAdminDAO(adminPostgreDAO);

        when(adminPostgreDAO.isUserAbsentOverAll(argThat(a -> a.getEmail().equals("admin@dominio.com"))))
                .thenThrow(new UserAlreadyExists());

        assertEquals("{\"code\": 3 , \"message\": \" User Already Exists\"}", managementAdmin.addAdmin(admin));
    }

}
