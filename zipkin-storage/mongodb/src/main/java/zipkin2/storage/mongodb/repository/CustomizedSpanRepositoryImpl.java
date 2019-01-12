package zipkin2.storage.mongodb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import zipkin2.Span;
import zipkin2.storage.QueryRequest;
import zipkin2.storage.mongodb.model.MongoSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class CustomizedSpanRepositoryImpl implements CustomizedSpanRepository {

  @Autowired
  private MongoOperations operations;

  @Override
  public List<String> findAllServiceNames() {
    List<String> localServiceNames = operations.findDistinct("localServiceName", MongoSpan.class, String.class);
    List<String> remoteServiceNames = operations.findDistinct("remoteServiceName", MongoSpan.class, String.class);
    return Stream.concat(localServiceNames.stream(), remoteServiceNames.stream()).distinct().collect(Collectors.toList());
  }

  @Override
  public List<String> findAllSpanNames(String serviceName) {
    return operations.findDistinct(new Query(where("localServiceName").is(serviceName).orOperator(where("remoteServiceName").is(serviceName))),
      "spanName", MongoSpan.class, String.class);
  }

  @Override
  public List<List<Span>> findByQueryRequest(QueryRequest request) {
    // TODO implement filtering :D
    return groupByTraceId(operations.findAll(Span.class));
  }

  @Override
  public List<Span> findByNormalizedId(String id) {
    // TODO make normalize specific
    return operations.find(new Query(where("traceId").is(id)), Span.class);
  }

  private List<List<Span>> groupByTraceId(List<Span> spans) {
    HashMap<String, List<Span>> hashMap = new HashMap<>();
    for (Span span : spans) {
      if (!hashMap.containsKey(span.traceId())) {
        List<Span> list = new ArrayList<>();
        list.add(span);
        hashMap.put(span.traceId(), list);
      } else {
        hashMap.get(span.traceId()).add(span);
      }
    }
    return new ArrayList<>(hashMap.values());
  }
}
