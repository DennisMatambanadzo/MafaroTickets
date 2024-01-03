package online.epochsolutions.mafaro.repos;

import online.epochsolutions.mafaro.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event,String> {
//    @Override
    Optional<Event> findById(String s);

//    Optional<Event> findByIdAndSections(String id, String section);
    List<Event> findAll();
    void deleteById(String id);

    }
