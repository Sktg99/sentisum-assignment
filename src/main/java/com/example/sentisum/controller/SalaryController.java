package com.example.sentisum.controller;

import com.example.sentisum.model.SalaryDetails;
import com.example.sentisum.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @GetMapping(value = "/compensation/details")
    public List<SalaryDetails> getSalaries(@RequestParam(required = false, defaultValue = "0") float minSalary,
                                           @RequestParam(required = false) String city,
                                           @RequestParam(required = false, defaultValue = "0") int pageNo,
                                           @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return salaryService.getSalaryDetails(minSalary, city, pageNo, pageSize);
    }

    @GetMapping(value = "/compensation/details/sorted")
    public List<SalaryDetails> getSortedSalaries(@RequestParam(required = false) String timestamp,
                                                 @RequestParam(required = false) String salary,
                                                 @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                 @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return salaryService.getSortedSalaryDetails(timestamp, salary, pageNo, pageSize);
    }

    @GetMapping("/compensation/details/{id}")
    public Map<String, Object> getCompensationDataFieldsById(
            @PathVariable String id,
            @RequestParam(name = "fields") List<String> fields
    ) {
        return salaryService.getCompensationDataFieldsById(id, fields);
    }
}
