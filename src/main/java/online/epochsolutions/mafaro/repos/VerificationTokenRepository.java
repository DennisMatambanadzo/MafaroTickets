package online.epochsolutions.mafaro.repos;

import online.epochsolutions.mafaro.authentication.VerificationToken;
import online.epochsolutions.mafaro.models.Organiser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken,String> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUserEmail(String email);

    void deleteVerificationTokenByUser(Organiser user);

    void deleteVerificationTokenByUserEmail(String userEmail);
}
