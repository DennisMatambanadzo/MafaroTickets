package online.epochsolutions.mafaro.repos;

import online.epochsolutions.mafaro.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event,String> {
    @Override
    Optional<Event> findById(String s);

    void deleteById(String id);
    }
