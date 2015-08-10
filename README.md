This project shows how to unit test some Java code sending e-mail. If you want a quick overview, just take a look at the simple test class `src/test/../BirthdayCelebrationTest.java`

For more details read through.

The test code uses Wiser as an SMTP in memory server. Add this dependency to your project :

    testCompile 'org.subethamail:subethasmtp:3.1.7'  
In the test setup, you can now instantiate the SMTP server :

    Wiser inMemorySmtpServer = new Wiser();
You can then read the emails send during the tests :

    inMemorySmtpServer.getMessages()
And write some assertions (this example uses AssertJ):

    assertThat(inMemorySmtpServer.getMessages())
            .extracting("envelopeSender", "envelopeReceiver", "mimeMessage.content")
            .containsOnly(
                    tuple("no-reply@romain-gervais.fr", "kevindu77@pimpmymail.com", "Bonjour, pour tes 28 ans l'équipe te souhaite un très bon anniversaire !" + System.lineSeparator()),
                    tuple("no-reply@romain-gervais.fr", "amandinedu42@pimpmymail.com", "Bonjour, pour tes 28 ans l'équipe te souhaite un très bon anniversaire !" + System.lineSeparator())
            );
Don't forget to cleanup the server between tests :

    @After
    public void afterTest(){
        inMemorySmtpServer.stop();
    }