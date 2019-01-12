package zipkin2.storage.mongodb.repository;

import zipkin2.Call;
import zipkin2.Span;
import zipkin2.storage.QueryRequest;

import java.util.List;

public interface CustomizedSpanRepository {
  List<String> findAllServiceNames();

  List<String> findAllSpanNames(String serviceName);

  List<List<Span>> findByQueryRequest(QueryRequest request);

  List<Span> findByNormalizedId(String id);
}
