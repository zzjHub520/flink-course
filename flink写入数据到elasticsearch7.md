## Flink写入数据到Elasticsearch示例



```java
import org.apache.http.HttpHost;

import java.util.ArrayList;
import java.util.List;

public class AA {
    public static void main(String[] args) {

        List<HttpHost> elsearchHosts = new ArrayList<>();

        elsearchHosts.add(new HttpHost("192.168.32.36", 9200, "http"));

        elsearchHosts.add(new HttpHost("192.168.32.37", 9200, "http"));

        elsearchHosts.add(new HttpHost("192.168.32.38", 9200, "http"));

        ObjectMapper mapper = newObjectMapper(); // jaskson ObjectMapper

        ElasticsearchSink.Builder esSinkBuilder = new ElasticsearchSink.Builder<>(
                // ResultCollector 是你要保存的对象类型，替换即可
                elsearchHosts,
                new ElasticsearchSinkFunction() {
                    private static final long serialVersionUID = -6797861015704600807L;

                    public IndexRequest createIndexRequest(ResultCollector collector) throwsException {
                        returnRequests.indexRequest()
                                .index("flink-test-index") // 设置Index
                                .id(collector.getId()) // 设置ID
                                // 这里要特别注意需要传map
                                .source(mapper.readValue(mapper.writeValueAsString(collector), Map.class));
                    }

                    @SneakyThrows
                    @Overridepublic
                    void process(ResultCollector collector, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
                        requestIndexer.add(createIndexRequest(collector));
                    }
            }
        );

        esSinkBuilder.setBulkFlushMaxActions(1);
        esSinkBuilder.setFailureHandler(newRetryRejectedExecutionFailureHandler());
        esSinkBuilder.setRestClientFactory((RestClientFactory) restClientBuilder -> {
            Header[] headers = new BasicHeader[]{new BasicHeader("Content-Type", "application/json")};
            restClientBuilder.setDefaultHeaders(headers);
        });
    }
}

```

