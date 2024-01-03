package online.epochsolutions.mafaro.repos;


import online.epochsolutions.mafaro.models.Organiser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganiserAccountRepository extends CrudRepository<Organiser,Long> {
    Optional<Organiser> findByEmail(String email);

    Optional<Organiser> findByEmailIgnoreCase(String email);
}
