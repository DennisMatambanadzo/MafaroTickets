package online.epochsolutions.mafaro.repos;

import online.epochsolutions.mafaro.models.Patron;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatronRepository extends CrudRepository<Patron,String> {
    Optional<Patron> findByEmailIgnoreCase(String email);
}
