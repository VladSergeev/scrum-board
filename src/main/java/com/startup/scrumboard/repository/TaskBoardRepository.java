package com.startup.scrumboard.repository;

import com.startup.scrumboard.model.entity.Project;
import com.startup.scrumboard.model.entity.TaskBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by User on 06.11.2016.
 */
@Repository
public interface TaskBoardRepository extends MongoRepository<TaskBoard, String> {

    Page<TaskBoard> findByNameLike(String name, Pageable pageable);
}
