package online.epochsolutions.mafaro.repos;


import online.epochsolutions.mafaro.models.Host;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostAccountRepository extends CrudRepository<Host,Long> {
    Optional<Host> findByEmail(String email);

    Optional<Host> findByEmailIgnoreCase(String email);
}
