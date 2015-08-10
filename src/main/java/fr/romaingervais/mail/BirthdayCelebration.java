package fr.romaingervais.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Stream;

/**
 * Created by Romain on 09/02/15.
 */
public class BirthdayCelebration {

    private SmtpConfig smtpConfig;

    public BirthdayCelebration(SmtpConfig smtpConfig) {
        this.smtpConfig = smtpConfig;
    }

    public void wishBirthdays(Stream<String> emails, LocalDate dateOfBirth, LocalDate currentDate){
        int age = Period.between(dateOfBirth,currentDate).getYears();
        emails.forEach(mail -> wishBirthday(mail, age));
    }

    protected void wishBirthday(String mail, int age){
        try {
            Email email = new SimpleEmail();
            email.setHostName(smtpConfig.getHost());
            email.setSmtpPort(smtpConfig.getPort());
            email.setFrom("no-reply@romain-gervais.fr");
            email.setSubject("Bon anniversaire");
            email.setMsg("Bonjour, pour tes " + age + " ans l'équipe te souhaite un très bon anniversaire !");
            email.setCharset("UTF-8");
            email.addTo(mail);
            email.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
