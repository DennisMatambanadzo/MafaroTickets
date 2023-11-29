package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.patron.CreatePatronRequest;
import online.epochsolutions.mafaro.dtos.patron.UpdatePatronRequest;
import online.epochsolutions.mafaro.models.Patron;
import online.epochsolutions.mafaro.repos.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatronRepository patronRepository;
    private final PasswordEncryptionService encryptionService;

    public Boolean patronRegistration(CreatePatronRequest request){
        Optional<Patron> byEmailIgnoreCase = patronRepository.findByEmailIgnoreCase(request.getEmail());

        if (byEmailIgnoreCase.isEmpty()){
            var patron = new Patron();

            patron.setEmail(request.getEmail());
            patron.setFirstName(request.getFirstName());
            patron.setLastName(request.getLastName());
            patron.setPassword(encryptionService.encryptPassword(request.getPassword()));
            patronRepository.save(patron);
            return true;
        }

        return false;
    }

    public Boolean updatePatron(UpdatePatronRequest request){
        return false;
    }
}
