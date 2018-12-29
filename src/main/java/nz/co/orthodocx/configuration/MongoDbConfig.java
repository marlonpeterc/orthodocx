package nz.co.orthodocx.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(value = "nz.co.orthodocx.repository.mongodb.reactive")
public class MongoDbConfig extends AbstractReactiveMongoConfiguration {

    @Value("${mongodb.connection.host:localhost}")
    private String host;

    @Value("${mongodb.connection.string:mongodb://localhost}")
    private String connection;

    @Value("${mongodb.databaseName:database}")
    private String databaseName;

    /**
     * Factory bean that creates the com.mongodb.reactivestreams.client.MongoClient instance.
     * The FactoryBean approach has the added advantage of also providing the container with an
     * ExceptionTranslator implementation that translates MongoDB exceptions to exceptions
     * in Spring’s portable DataAccessException hierarchy for data access classes annotated
     * with the @Repository annotation.
     *
     * @return - ReactiveMongoClientFactoryBean
     *
     */
    @Bean
    public ReactiveMongoClientFactoryBean mongoClientFactory() {
        ReactiveMongoClientFactoryBean clientFactory = new ReactiveMongoClientFactoryBean();
        clientFactory.setHost(host);
        return clientFactory;
    }

    /**
     * Register a MongoClient instance with the container.
     *
     * @return - MongoClient
     *
     */
    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(connection);
    }

    /**
     * Register a ReactiveMongoDatabaseFactory instance with the container.
     * To define the username and password, create a MongoDB connection string
     * and pass it into the factory method. Example connection string format:
     *
     * mongodb://username:password@localhost
     *
     * @return - ReactiveMongoDatabaseFactory
     *
     */
    @Bean
    public ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory() {
        return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), databaseName);
    }

    /**
     * Register an instance of ReactiveMongoTemplate with the container using
     * the ReactiveMongoDatabaseFactory.
     *
     * ReactiveMongoTemplate is the central class of the Spring’s Reactive MongoDB
     * support and provides a rich feature set to interact with the database.
     * The template offers convenience operations to create, update, delete, and query
     * for MongoDB documents and provides a mapping between the domain objects
     * and MongoDB documents.
     *
     * Note that once configured, ReactiveMongoTemplate is thread-safe and
     * can be reused across multiple instances.
     *
     * @return - ReactiveMongoTemplate
     *
     */
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        ReactiveMongoTemplate reactiveMongoTemplate = new ReactiveMongoTemplate(reactiveMongoDatabaseFactory());
        reactiveMongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return reactiveMongoTemplate;
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

}
