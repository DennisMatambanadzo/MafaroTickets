package online.epochsolutions.mafaro.repos;

import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Host;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event,String> {
    @Override
    Optional<Event> findById(String s);

    Optional<Event> findByIdAndSections(String id, String section);

    void deleteById(String id);
    void deleteEventByUserAndId(Host user, String id);
    }
