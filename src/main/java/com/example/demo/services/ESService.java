package com.example.demo.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.example.demo.config.Properties;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.SKUDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ESService {

    private final static String INDEX_NAME = "product";
    private final ProductService productService;
    private final ObjectMapper mapper = new ObjectMapper();

    private final ElasticsearchClient esClient;

    private final Properties properties;


    public String createAnIndex() throws IOException {
        try {
            esClient.indices().create(c -> c
                    .index("product")
                    .mappings(m -> m
                            .properties("name", p -> p
                                    .text(t -> t)
                            )
                            .properties("description", p -> p
                                    .text(t -> t)
                            )
                            .properties("price", p -> p
                                    .float_(f -> f)
                            )
                            .properties("date", p -> p
                                    .date(d -> d)
                            )
                            .properties("skuList", p -> p
                                    .nested(n -> n
                                            .properties("id", np -> np
                                                    .integer(i -> i)
                                            )
                                            .properties("code", np -> np
                                                    .keyword(k -> k)
                                            )
                                            .properties("color", np -> np
                                                    .keyword(k -> k)
                                            )
                                            .properties("access", np -> np
                                                    .boolean_(b -> b)
                                            )
                                    )
                            )
                    )
            );
        } catch (ElasticsearchException e) {
            log.error("Index tried to create again");
            return "Index tried to create again";
        }
        indexingDB();
        return "Successful";
    }


    public void indexingProduct(ProductDto productDto) throws IOException {
        IndexResponse response = esClient.index(i -> i
                .index(INDEX_NAME)
                .id(productDto.id().toString())
                .document(productDto)

        );
        log.info("Indexed with version " + response.version());
    }

    public void indexingDB() throws IOException {
        List<ProductDto> productDtos = productService.getAll();
        for (ProductDto i : productDtos) {
            indexingProduct(i);
        }
        log.info("Database was indexed");
    }

    public List<ProductDto> search(String searchText, String color) throws IOException {
        if(color.equals("none")){
            color="blue";
        }

        String finalColor = color;
        NestedQuery nestedSkuFilter = new NestedQuery.Builder()
                .path("skuList")
                .query(q -> q.bool(b -> b
                        .must(m -> m.term(t -> t.field("skuList.color").value(finalColor))) // Фильтр по цвету
                        .must(m -> m.term(t -> t.field("skuList.access").value(true))) // Закомментировано, если нужно
                ))
                .build();

        SearchRequest request = null;

        if (properties.getEnabled()) {
            request = new SearchRequest.Builder()
                    .index(INDEX_NAME)
                    .query(q -> q.bool(b -> b
                            .must(m -> m
                                    .multiMatch(t -> t
                                            .fields("name", "description")
                                            .query(searchText)
                                    )
                            )
                            .filter(f -> f.nested(nestedSkuFilter)) // Nested фильтр
                    ))
                    .build();
        } else {
            // Без фильтра, если отключено через конфигурацию
            request = new SearchRequest.Builder()
                    .index(INDEX_NAME)
                    .query(q -> q.
                            multiMatch(t -> t
                                    .fields("name", "description")
                                    .query(searchText)
                            ))
                    .build();
        }

        SearchResponse<ProductDto> response = esClient.search(request, ProductDto.class);
        List<ProductDto> filteredProducts = new ArrayList<>();

        for (Hit<ProductDto> hit : response.hits().hits()) {
            ProductDto product = hit.source();

            if(properties.getEnabled()) {
                // Фильтрация SKU на уровне данных
                List<SKUDto> filteredSkuList = product.skuList().stream()
                        .filter(sku -> finalColor.equals(sku.color()) && sku.access()) // Применение фильтра
                        .collect(Collectors.toList());

                // Если есть подходящие SKU, создаём новый ProductDto с отфильтрованными SKU
                if (!filteredSkuList.isEmpty()) {
                    ProductDto filteredProduct = new ProductDto(
                            product.id(),                 // id продукта
                            product.name(),
                            product.price(),
                            product.date(),               // имя продукта
                            product.description(),        // описание продукта
                            filteredSkuList               // обновленный список sku
                    );
                    filteredProducts.add(filteredProduct);
                }
            }else{
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }

    public void addToES(Long id) throws IOException {
        ProductDto productDto = productService.getById(id);
        indexingProduct(productDto);
    }

}
