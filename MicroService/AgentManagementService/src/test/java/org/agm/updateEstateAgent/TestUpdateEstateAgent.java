package org.agm.updateEstateAgent;


import org.dao.postgre.AgentPostgreDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

import static org.mockito.Mockito.*;


class TestUpdateEstateAgent {
    AgentPostgreDAO agentPostgreDAO = null;

    // Valid data
    final Agent validAgent = new Agent.Builder(1,"utente@esempio.com").build();
    final Estate validEstate = new Estate.Builder(0).build();
    // Invalid data
    final Agent emptyAgente = new Agent.Builder(1,"").build();



    @BeforeEach
    void setUp() {
        agentPostgreDAO = mock(AgentPostgreDAO.class);
    }

    @Test
    void test_IdEstateNegativo() {



    }

    @Test
    void test_EmailAgentVuota() {



    }

    @Test
    void test_EmailAgentInvalida() {



    }

    @Test
    void test_EmailNonCorrispndente() {



    }

    @Test
    void test_IdEstateNonCorrispndente() {



    }





}







