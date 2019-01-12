package zipkin2.storage.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zipkin2.Call;
import zipkin2.DependencyLink;
import zipkin2.Span;
import zipkin2.storage.QueryRequest;
import zipkin2.storage.SpanStore;
import zipkin2.storage.mongodb.repository.MongoSpanRepository;

import java.util.List;

@Repository
public class MongoSpanStore implements SpanStore {

  private final MongoSpanRepository mongoSpanRepository;

  @Autowired
  public MongoSpanStore(MongoSpanRepository mongoSpanRepository) {
    this.mongoSpanRepository = mongoSpanRepository;
  }

  @Override
  public Call<List<List<Span>>> getTraces(QueryRequest request) {
    return Call.create(this.mongoSpanRepository.findByQueryRequest(request));
  }

  @Override
  public Call<List<Span>> getTrace(String traceId) {
    String normalizedTraceId = Span.normalizeTraceId(traceId);
    return Call.create(mongoSpanRepository.findByNormalizedId(normalizedTraceId));
  }

  @Override
  public Call<List<String>> getServiceNames() {
    return Call.create(mongoSpanRepository.findAllServiceNames());
  }

  @Override
  public Call<List<String>> getSpanNames(String serviceName) {
    return Call.create(mongoSpanRepository.findAllSpanNames(serviceName));
  }

  @Override
  public Call<List<DependencyLink>> getDependencies(long endTs, long lookback) {
    return null;
  }
}
