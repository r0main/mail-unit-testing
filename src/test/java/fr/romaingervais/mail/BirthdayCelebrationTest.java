package fr.romaingervais.mail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.subethamail.wiser.Wiser;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Created by Romain on 09/02/15.
 */
public class BirthdayCelebrationTest {
    Wiser inMemorySmtpServer = new Wiser();
    SmtpConfig smtpConfig = SmtpConfig.of("localhost", 5555);
    BirthdayCelebration birthdayCelebration = new BirthdayCelebration(smtpConfig); //SUT

    @Before
    public void beforeTest(){
        inMemorySmtpServer.setPort(smtpConfig.getPort());
        inMemorySmtpServer.start();
    }

    @After
    public void afterTest(){
        inMemorySmtpServer.stop();
    }

    @Test
    public void testIfNoBodyIsBornTodayNoMailsShouldBeSent(){
        Stream<String> nobody = Stream.empty();
        LocalDate dateOfBirth = LocalDate.of(1987, Month.JULY, 18);
        LocalDate currentDate = LocalDate.of(2015, Month.FEBRUARY, 9);

        birthdayCelebration.wishBirthdays(nobody, dateOfBirth, currentDate);

        assertThat(inMemorySmtpServer.getMessages()).isEmpty();
    }

    @Test
    public void testMailAreSentToTheSpecifiedPersons(){
        Stream<String> birthdayOf = Stream.of("kevindu77@pimpmymail.com", "amandinedu42@pimpmymail.com");
        LocalDate dateOfBirth = LocalDate.of(1987, Month.JULY, 18);
        LocalDate currentDate = LocalDate.of(2015, Month.FEBRUARY, 9);

        birthdayCelebration.wishBirthdays(birthdayOf, dateOfBirth, currentDate);

        assertThat(inMemorySmtpServer.getMessages())
            .extracting("envelopeSender", "envelopeReceiver", "mimeMessage.content")
            .containsOnly(
                    tuple("no-reply@romain-gervais.fr", "kevindu77@pimpmymail.com", "Bonjour, pour tes 27 ans l'équipe te souhaite un très bon anniversaire !" + System.lineSeparator()),
                    tuple("no-reply@romain-gervais.fr", "amandinedu42@pimpmymail.com", "Bonjour, pour tes 27 ans l'équipe te souhaite un très bon anniversaire !" + System.lineSeparator())
            );
    }
}
