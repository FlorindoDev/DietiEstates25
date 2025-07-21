package org.agm.AddAgent;
import org.dao.postgre.AgentPostgreDAO;
import org.exc.DataBaseException.UserAlreadyExists;
import org.exc.DietiEstateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.md.Agency.Agency;
import org.md.Utente.Agent;
import org.agm.MainApp.ManagementAgent;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class TestAddAgent {
    AgentPostgreDAO agentPostgreDAO = null;

    @BeforeEach
    void setUp() {
        agentPostgreDAO = mock(AgentPostgreDAO.class);
    }

    // T01: Successo
    @Test
    void testT01_successo() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 0, \"message\": \"success of action agent create\"}", managementAgent.addAgent(agent));
    }

    // T02: Errore nome vuoto
    @Test
    void testT02_nomeVuoto() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("")  // nome vuoto
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAgent.addAgent(agent));
    }

    // T03: Errore nome minuscolo
    @Test
    void testT03_nomeMinuscolo() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("marco")  // nome minuscolo
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAgent.addAgent(agent));
    }

    // T04: Errore nome con numero
    @Test
    void testT04_nomeConNumero() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("Mar1o")  // nome con numero
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAgent.addAgent(agent));
    }

    // T05: Errore cognome vuoto
    @Test
    void testT05_cognomeVuoto() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("")  // cognome vuoto
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 103 , \"message\": \" User Generality not valid\"}", managementAgent.addAgent(agent));
    }

    // T06: Password troppo corta
    @Test
    void testT06_passwordCorta() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcd")  // password corta
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 102 , \"message\": \" User Password not valid\"}", managementAgent.addAgent(agent));
    }

    // T07: Password vuota
    @Test
    void testT07_passwordVuota() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("")  // password vuota
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 102 , \"message\": \" User Password not valid\"}", managementAgent.addAgent(agent));
    }

    // T08: Email senza '@'
    @Test
    void testT08_emailSenzaChiocciola() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marcodominio.com")  // email senza '@'
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAgent.addAgent(agent));
    }

    // T09: Email senza suffisso (es. .com)
    @Test
    void testT09_emailSenzaSuffisso() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio")  // email senza suffisso
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAgent.addAgent(agent));
    }

    // T10: Email vuota
    @Test
    void testT10_emailVuota() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "")  // email vuota
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAgent.addAgent(agent));
    }

    // T11: Partita IVA vuota
    @Test
    void testT11_pIvaVuota() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("").build())  // P.IVA vuota
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 104 , \"message\": \" PartitaIVA of agency not valid: is to short or is to long\"}", managementAgent.addAgent(agent));
    }

    // T12: Partita IVA troppo corta
    @Test
    void testT12_pIvaCorta() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "marco@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("1234567").build())  // P.IVA corta
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        when(agentPostgreDAO.isUserAbsentOverAll(any()))
                .thenReturn(true);
        managementAgent.setAgentDAO(agentPostgreDAO);

        assertEquals("{\"code\": 104 , \"message\": \" PartitaIVA of agency not valid: is to short or is to long\"}", managementAgent.addAgent(agent));
    }

    // T13: Email già esistente
    @Test
    void testT13_emailGiaEsistente() throws DietiEstateException {
        Agent agent = new Agent.Builder(13, "agent@dominio.com")
                .setName("Marco")
                .setCognome("Rossi")
                .setPassword("abcdefgh")
                .setAgency(new Agency.Builder<>("12345678901").build())
                .build();

        ManagementAgent managementAgent = new ManagementAgent();
        managementAgent.setAgentDAO(agentPostgreDAO);

        when(agentPostgreDAO.isUserAbsentOverAll(argThat(a -> a.getEmail().equals("agent@dominio.com"))))
                .thenThrow(new UserAlreadyExists());

        assertEquals("{\"code\": 3 , \"message\": \" User Already Exists\"}", managementAgent.addAgent(agent));
    }

}
