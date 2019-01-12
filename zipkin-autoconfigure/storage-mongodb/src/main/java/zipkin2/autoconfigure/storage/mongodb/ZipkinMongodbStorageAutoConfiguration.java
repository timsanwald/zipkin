package zipkin2.autoconfigure.storage.mongodb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.storage.StorageComponent;
import zipkin2.storage.mongodb.MongoSpanConsumer;
import zipkin2.storage.mongodb.MongoSpanStore;
import zipkin2.storage.mongodb.MongoStorage;
import zipkin2.storage.mongodb.repository.MongoSpanRepository;

@Configuration
@EnableConfigurationProperties(ZipkinMongodbStorageProperties.class)
@ConditionalOnProperty(name = "zipkin.storage.type", havingValue = "mongodb")
@ConditionalOnMissingBean(StorageComponent.class)
public class ZipkinMongodbStorageAutoConfiguration {

  @Bean
  StorageComponent storage() {
    MongoSpanRepository repository = factory.getRepository(MongoSpanRepository.class);
    return new MongoStorage(new MongoSpanStore(repository), new MongoSpanConsumer(repository));
  }
}
