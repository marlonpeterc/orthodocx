package nz.co.orthodocx;

import com.mongodb.reactivestreams.client.MongoClients;
import nz.co.orthodocx.models.Profile;
import nz.co.orthodocx.repositories.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@SpringBootApplication
public class OrthodocxApplication implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(OrthodocxApplication.class.getName());

    @Autowired
    private ProfileRepository repository;

    public static void main(String[] args) {
        wordCount();
        SpringApplication.run(OrthodocxApplication.class, args);
        try {
            testReactiveMongo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void wordCount() {
        String text = "The quick brown fox jumps over the lazy dog";
        final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
        Stream<String> stream = Stream.of(pattern.split(text.toLowerCase()));
        Map<String, Long> collect = stream.collect(groupingBy(word -> word, counting()));
        logger.info(collect.toString());
    }

    private static void testReactiveMongo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
        mongoOps.insert(new Profile("Dark", "Knight"))
                .flatMap(p -> mongoOps.findOne(new Query(where("firstName").is("Dark")), Profile.class))
                .doOnNext(profile -> logger.info(profile.toString()))
                .flatMap(profile -> mongoOps.dropCollection("profile"))
                .doOnSuccess(profile -> latch.countDown())
                .subscribe();
        latch.await();
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        // save some profiles
        repository.save(new Profile("Jack", "Sparrow"));
        repository.save(new Profile("Kinig", "Tot"));
        repository.save(new Profile("Ken", "Tot"));

        // fetch all customers
        logger.info("Customers found with findAll():");
        logger.info("-------------------------------");
        List<Profile> all = repository.findAll();
        all.stream().forEach(profile -> logger.info(profile.toString()));

        // fetch an individual customer
        logger.info("Customer found with findByFirstName('Jack'):");
        logger.info("--------------------------------");
        Profile jack = repository.findByFirstName("Jack");
        logger.info(jack.toString());

        logger.info("Customers found with findByLastName('Tot'):");
        logger.info("--------------------------------");
        List<Profile> tot = repository.findByLastName("Tot");
        tot.stream().forEach(profile -> logger.info(profile.toString()));
    }
}
