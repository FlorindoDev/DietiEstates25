package org.email.Interfacce;

import jakarta.mail.MessagingException;
import org.email.Email;
import org.exc.DietiEstateException;

import java.util.ArrayList;

public interface EmailFactory {

    Email createEmailNewAgency(ArrayList<String> contents, String nameFileHtml, String reciverEmail) throws DietiEstateException;

    Email  createEmailAppointment(ArrayList<String> contents, String nameFileHtml,String reciverEmail) throws DietiEstateException;

    Email createEmailEstate() throws MessagingException;
}
