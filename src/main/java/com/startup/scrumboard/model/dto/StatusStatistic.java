package com.startup.scrumboard.model.dto;

import com.startup.scrumboard.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vsergeev on 10.01.2017.
 */
@Getter
@Setter
public class StatusStatistic {
    Long year;
    Long month;
    Long day;
    TaskStatus status;
    Long count;
}
