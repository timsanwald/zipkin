package zipkin2.storage.mongodb;

import zipkin2.Call;
import zipkin2.Span;
import zipkin2.storage.SpanConsumer;
import zipkin2.storage.mongodb.repository.MongoSpanRepository;

import java.util.List;

public class MongoSpanConsumer implements SpanConsumer {

  private final MongoSpanRepository mongoSpanRepository;

  public MongoSpanConsumer(MongoSpanRepository mongoSpanRepository) {
    this.mongoSpanRepository = mongoSpanRepository;
  }

  @Override
  public Call<Void> accept(List<Span> spans) {
    // TODO revisit if this is correct ? oO
    mongoSpanRepository.saveAll(spans);
    return Call.create(null);
  }
}
