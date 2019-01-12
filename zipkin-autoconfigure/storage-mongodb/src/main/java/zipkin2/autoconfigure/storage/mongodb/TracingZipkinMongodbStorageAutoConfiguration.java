package zipkin2.autoconfigure.storage.mongodb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;


@ConditionalOnProperty(name = "zipkin.storage.type", havingValue = "cassandra")
@Configuration
public class TracingZipkinMongodbStorageAutoConfiguration {
}
