package org.ma.API.Interface;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

public interface ManagementAccountAPI {

    String applyChangeAcquirente(Acquirente utente);

    String applyChangeAgent(Agent utente);

    String applyChangeAdmin(Admin utente);

    String getAccountAcquirente(@QueryParam("email") String email);

    String getAccountAdmin(@QueryParam("email") String email);

    String getAccountAgent(@QueryParam("email") String email);
}