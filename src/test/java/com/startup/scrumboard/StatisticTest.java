package com.startup.scrumboard;

import com.startup.scrumboard.model.dto.StatusStatistic;
import com.startup.scrumboard.model.entity.Task;
import com.startup.scrumboard.model.enums.TaskStatus;
import com.startup.scrumboard.repository.StatisticRepository;
import com.startup.scrumboard.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

/**
 * Created by vsergeev on 10.01.2017.
 */
public class StatisticTest extends AbstractApplicationTest {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    StatisticRepository statisticRepository;

    @Test
    public void simpleStatTest() throws Exception {
        for (int i = 0; i < 2; i++) {
            taskRepository.save(create(-i, TaskStatus.OPEN));
        }
        List<StatusStatistic> test1 = statisticRepository.getStatusStatistic();
        Assert.assertTrue(test1.size() == 2);

        taskRepository.save(create(-1, TaskStatus.OPEN));
        List<StatusStatistic> test2 = statisticRepository.getStatusStatistic();
        Assert.assertTrue(test2.size() == 2);
        Assert.assertTrue(new Long(1).equals(test2.get(0).getCount()) && new Long(2).equals(test2.get(1).getCount()) ||
                new Long(1).equals(test2.get(1).getCount()) && new Long(2).equals(test2.get(0).getCount()));
    }

    private Task create(int day, TaskStatus status) {
        Task task = new Task();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        task.setUpdateTime(cal.getTime());
        task.setStatus(status);
        return task;
    }

}
