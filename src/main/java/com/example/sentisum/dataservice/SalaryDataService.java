package com.example.sentisum.dataservice;

import com.example.sentisum.config.AppConfig;
import com.example.sentisum.model.SalaryDetails;
import io.micrometer.common.util.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;

@Service
public class SalaryDataService {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<SalaryDetails> getFilteredSalaryData(float minSalary, String city, PageRequest pageRequest) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder = queryBuilder.withFilter(QueryBuilders.rangeQuery(AppConfig.salaryFieldName).gte(minSalary));
        if (city != null) {
            queryBuilder = queryBuilder.withQuery(fuzzyQuery(AppConfig.cityFieldName, city));
        }
        queryBuilder = queryBuilder.withPageable(pageRequest);
        return elasticsearchOperations.queryForList(queryBuilder.build(), SalaryDetails.class,
                IndexCoordinates.of(AppConfig.salaryIndex));
    }

    public List<SalaryDetails> getSortedSalaryData(String timestamp, String salary, PageRequest pageRequest) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder = queryBuilder.withQuery(QueryBuilders.matchAllQuery());
        if (StringUtils.isNotBlank(timestamp)) {
            queryBuilder = getFilteredQueryBuilder(queryBuilder, timestamp, AppConfig.timestampFieldName);
        }
        if (StringUtils.isNotBlank(salary)) {
            queryBuilder = getFilteredQueryBuilder(queryBuilder, salary, AppConfig.salaryFieldName);

        }
        queryBuilder = queryBuilder.withPageable(pageRequest);

        return elasticsearchOperations.queryForList(queryBuilder.build(), SalaryDetails.class,
                IndexCoordinates.of(AppConfig.salaryIndex));

    }

    private NativeSearchQueryBuilder getFilteredQueryBuilder(
            NativeSearchQueryBuilder queryBuilder, String attribute, String field) {
        if (StringUtils.isNotBlank(attribute)) {
            if ("desc".equalsIgnoreCase(attribute)) {
                return queryBuilder.withSort(SortBuilders.fieldSort(field).order(SortOrder.DESC));
            } else if ("asc".equalsIgnoreCase(attribute)) {
                return queryBuilder.withSort(SortBuilders.fieldSort(field).order(SortOrder.ASC));
            }
        }
        return queryBuilder;
    }
}
