package org.email.Interfacce;

import org.email.Email;

public interface EmailSender {

    void sendEmail(Email email);

    EmailFactory getFacotryEmail();

}
