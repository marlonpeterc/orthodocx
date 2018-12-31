package nz.co.orthodocx.repository.mongodb.reactive;

import nz.co.orthodocx.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class ProfileMongoTemplateRepository {

    @Autowired
    private ReactiveMongoTemplate mongoOps;

    public Flux<Profile> findByFirstName(String firstName) {
        return mongoOps.find(new Query(), Profile.class);
    }

}
