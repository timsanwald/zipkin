package zipkin2.storage.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zipkin2.storage.SpanConsumer;
import zipkin2.storage.SpanStore;
import zipkin2.storage.StorageComponent;

public class MongoStorage extends StorageComponent {

  private final MongoSpanStore mongoSpanStore;
  private final MongoSpanConsumer mongoSpanConsumer;

  @Autowired
  public MongoStorage(MongoSpanStore mongoSpanStore, MongoSpanConsumer mongoSpanConsumer) {
    this.mongoSpanStore = mongoSpanStore;
    this.mongoSpanConsumer = mongoSpanConsumer;
  }

  @Override
  public SpanStore spanStore() {
    return mongoSpanStore;
  }

  @Override
  public SpanConsumer spanConsumer() {
    return mongoSpanConsumer;
  }
}
