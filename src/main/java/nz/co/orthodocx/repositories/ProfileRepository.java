package nz.co.orthodocx.repositories;

import nz.co.orthodocx.models.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfileRepository extends MongoRepository<Profile, String> {

    Profile findByFirstName(String firstName);

    List<Profile> findByLastName(String lastName);

}
