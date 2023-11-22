package online.epochsolutions.mafaro.repos;

import online.epochsolutions.mafaro.models.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket,String> {

    @Override
    Optional<Ticket> findById(String s);

    List<Ticket> findAll();
}
