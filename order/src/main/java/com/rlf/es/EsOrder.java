package com.rlf.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "user_order")
@Data
public class EsOrder {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String orderNo; //订单号
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String orderName;// 订单名称

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(type = FieldType.Date)
    private Date startTime;
    @Field(type = FieldType.Date)
    private Date endTime;

}