package com.startup.scrumboard.repository;

import com.startup.scrumboard.model.dto.StatusStatistic;
import com.startup.scrumboard.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.fields;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import java.util.List;

/**
 * Created by vsergeev on 10.01.2017.
 */
@Repository
public class StatisticRepository {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<StatusStatistic> getStatusStatistic() {
        //TODO:Простая статистика по всем таскам
        Aggregation agg = newAggregation(
                project().andExpression("dayOfMonth(updateTime)").as("day")
                        .andExpression("month(updateTime)").as("month")
                        .andExpression("year(updateTime)").as("year")
                        .andExpression("status").as("status"),
                group(fields().and("day").and("month").and("year").and("status")).count().as("count"),
                sort(Sort.Direction.ASC, "year", "month", "day")
        );

        AggregationResults<StatusStatistic> groupResults
                = mongoTemplate.aggregate(agg, Task.class, StatusStatistic.class);
        return groupResults.getMappedResults();
    }
}
