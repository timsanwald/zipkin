package zipkin2.storage.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zipkin2.Span;

public interface MongoSpanRepository extends MongoRepository<Span, String>, CustomizedSpanRepository {


}
