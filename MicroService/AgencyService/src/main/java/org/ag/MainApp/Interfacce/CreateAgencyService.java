package org.ag.MainApp.Interfacce;

import org.md.Agency.Agency;

public interface CreateAgencyService {

    String makeAgency(Agency agency);

    void close();
}
