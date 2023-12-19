package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.contracts.GenerateSimpleMailMessage;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.models.Ticket;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketPurchaseNotificationEmailService implements GenerateSimpleMailMessage {

    private final JavaMailSender javaMailSender;

    public void sendTicketPurchaseEmail(List<Ticket> ticketList, String patronEmail) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(patronEmail);
        message.setSubject("Ticket Purchase Successful");
        message.setText("Your ticket purchase was successful. You have bought, " + ticketList.size() + " tickets!");
        try{
            javaMailSender.send(message);
        }catch(MailException exception){
            throw new EmailFailureException();
        }
    }
}
