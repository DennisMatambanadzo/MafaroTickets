package online.epochsolutions.mafaro.contracts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public interface GenerateSimpleMailMessage {

    @Value("${email.from}")
    String fromMessage ="noreply@mafaro.com";

    default SimpleMailMessage makeMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMessage);
        return simpleMailMessage;
    }
}
