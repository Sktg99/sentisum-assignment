package com.example.sentisum.repository;

import com.example.sentisum.model.SalaryDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface SalaryRepository extends ElasticsearchRepository<SalaryDetails, String> {

    Optional<SalaryDetails> findById(String id);
}
