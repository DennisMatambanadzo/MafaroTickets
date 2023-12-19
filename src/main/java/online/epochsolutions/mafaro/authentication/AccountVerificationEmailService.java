package online.epochsolutions.mafaro.authentication;

import online.epochsolutions.mafaro.contracts.GenerateSimpleMailMessage;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.models.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class AccountVerificationEmailService implements GenerateSimpleMailMessage {

    @Value("${email.from}")
    private String fromMessage;

    @Value("${app.frontend.url}")
    private String url;

    private final JavaMailSender javaMailSender;

    public AccountVerificationEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getEmail());
        message.setSubject("Verify your mail to activate your account,");
        message.setText("Please follow the link below to verify your email to activate your account.\n" +
                url + "/auth/verify?token=" + verificationToken.getToken());
        try{
            javaMailSender.send(message);
        }catch(MailException exception){
            throw new EmailFailureException();
        }
    }

}
