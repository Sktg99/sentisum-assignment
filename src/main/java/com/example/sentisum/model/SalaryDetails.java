package com.example.sentisum.model;

import com.example.sentisum.config.AppConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "new_salary_data_final_1")
public class SalaryDetails {
    @Id
    private String id;
    @Field(type = FieldType.Text, name = AppConfig.jobFieldName)
    private String jobTitle;
    @Field(type = FieldType.Text, name = AppConfig.cityFieldName)
    private String city;
    @Field(type = FieldType.Float, name = AppConfig.salaryFieldName)
    private float salary;
}
