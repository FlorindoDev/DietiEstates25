package org.agm.updateEstateAgent;


import org.agm.MainApp.ManagementAgent;
import org.dao.postgre.AgentPostgreDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class TestUpdateEstateAgent {
    AgentPostgreDAO agentDAO = null;
    EstatePostgreDAO estateDAO = null;
    ManagementAgent managementAgent;

    final Agent validAgent = new Agent.Builder(0,"utente@esempio.com").build();
    final Estate validEstate = new Estate.Builder(0).build();
    final Agent notExistAgent = new Agent.Builder(1,"nonesiste@esempio.com").build();
    final Estate notExistEstate = new Estate.Builder(1).build();
    final Agent emptyAgente = new Agent.Builder(1,"").build();
    final Agent wrongEmailAgente = new Agent.Builder(1,"utenteemailsbagliata").build();
    final Estate negativeEstate = new Estate.Builder(-1).build();


    @BeforeEach
    void setUp() {
        agentDAO = mock(AgentPostgreDAO.class);
        estateDAO = mock(EstatePostgreDAO.class);
        managementAgent = new ManagementAgent();

        try {
            when(agentDAO.isUserPresent(argThat(agent -> !agent.getEmail().equals(validAgent.getEmail()))))
                    .thenThrow(new UserNotExists());

            when(estateDAO.isEstatePresent(argThat(estate -> estate.getIdEstate()!=validEstate.getIdEstate())))
                    .thenThrow(new ErrorExecutingQuery());

            when(agentDAO.estraiIdFromEmailAgent(any()))
                    .thenAnswer(invocation -> {
                        Agent inputAgent = invocation.getArgument(0, Agent.class);

                        if (inputAgent.getEmail().equals(validAgent.getEmail())) {
                            return validAgent;
                        } else {
                            throw new ErrorCreateStatment();
                        }
                    });

            doAnswer(invocation -> {
                Estate inputEstate = invocation.getArgument(0, Estate.class);
                Agent inputAgent = invocation.getArgument(1, Agent.class);

                // Se estate o agent non sono validi → lancia eccezione
                if (inputEstate.getIdEstate() != validEstate.getIdEstate() ||
                        !inputAgent.getEmail().equals(validAgent.getEmail())) {
                    throw new ErrorCreateStatment();
                }

                return null;
            }).when(estateDAO).updateEstateAgent(any(), any());

            managementAgent.setAgentDAO(agentDAO);
            managementAgent.setEstateDAO(estateDAO);

        } catch (DietiEstateException e) {
            throw new RuntimeException(e);
        }



    }

    @Test
    void test_IdEstateNegativo() {
        assertEquals("{\"code\": 2 , \"message\": \" Error execute query\"}", managementAgent.updateEstateAgent(negativeEstate,validAgent));

    }

    @Test
    void test_EmailAgentVuota() {
        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAgent.updateEstateAgent(negativeEstate,emptyAgente));


    }

    @Test
    void test_EmailAgentInvalida() {
        assertEquals("{\"code\": 101 , \"message\": \" User email not valid\"}", managementAgent.updateEstateAgent(negativeEstate,wrongEmailAgente));


    }

    @Test
    void test_EmailNonCorrispndente() {
        assertEquals("{\"code\": 6 , \"message\": \" User Not Exists\"}", managementAgent.updateEstateAgent(validEstate,notExistAgent));



    }

    @Test
    void test_IdEstateNonCorrispndente() {

        assertEquals("{\"code\": 2 , \"message\": \" Error execute query\"}", managementAgent.updateEstateAgent(notExistEstate,validAgent));


    }





}



