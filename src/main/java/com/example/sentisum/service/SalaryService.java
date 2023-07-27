package com.example.sentisum.service;

import com.example.sentisum.dataservice.SalaryDataService;
import com.example.sentisum.model.SalaryDetails;
import com.example.sentisum.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    private SalaryDataService salaryDataService;
    @Autowired
    private SalaryRepository salaryRepository;

    public List<SalaryDetails> getSalaryDetails(float minSalary, String city, int pageNo, int pageSize) {
        return salaryDataService.getFilteredSalaryData(minSalary, city, PageRequest.of(pageNo, pageSize));
    }

    public List<SalaryDetails> getSortedSalaryDetails(String timestamp, String salary, int pageNo, int pageSize) {
        return salaryDataService.getSortedSalaryData(timestamp, salary, PageRequest.of(pageNo, pageSize));
    }

    public Map<String, Object> getCompensationDataFieldsById(String id, List<String> fields) {
        Optional<SalaryDetails> optionalData = salaryRepository.findById(id);
        if (optionalData.isPresent()) {
            SalaryDetails data = optionalData.get();
            return createSparseFieldset(data, fields);
        }
        return null;
    }

    private Map<String, Object> createSparseFieldset(SalaryDetails data, List<String> fields) {
        Map<String, Object> sparseFieldset = new HashMap<>();
        for (String field : fields) {
            switch (field) {
                case "id" -> sparseFieldset.put("id", data.getId());
                case "city" -> sparseFieldset.put("city", data.getCity());
                case "salary" -> sparseFieldset.put("salary", data.getSalary());
            }
        }
        return sparseFieldset;
    }
}

