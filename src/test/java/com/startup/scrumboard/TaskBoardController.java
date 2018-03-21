package com.startup.scrumboard;

import com.startup.scrumboard.model.entity.Project;
import com.startup.scrumboard.model.entity.TaskBoard;
import com.startup.scrumboard.repository.ProjectRepository;
import com.startup.scrumboard.repository.TaskBoardRepository;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class TaskBoardController extends AbstractApplicationTest {

    @Autowired
    private TaskBoardRepository boardRepository;
    @Autowired
    private ProjectRepository projectRepository;


    @Test
    public void create() throws Exception {
        Project project = new Project();
        project.setName("TestProject");
        project.setDescription("TestProject");

        post(project, "/api/v1/project/create").statusCode(HttpStatus.SC_OK);

        Page<Project> page = projectRepository.findByNameLike("TestProject", new PageRequest(0, 10));
        Assert.assertTrue(page.getContent().size() == 1);
        project = page.getContent().get(0);

        TaskBoard board = new TaskBoard();
        board.setName("TestBoard");
        board.setDescription("TestBoard");

        post(board, "/api/v1/board/create/" + project.getId()).statusCode(HttpStatus.SC_OK);
        Page<TaskBoard> pageBoard = boardRepository.findByNameLike("TestBoard", new PageRequest(0, 10));
        Assert.assertTrue(pageBoard.getContent().size() == 1);
        board = pageBoard.getContent().get(0);

        Page<Project> resultPage = projectRepository.findByNameLike("TestProject", new PageRequest(0, 10));
        Assert.assertTrue(resultPage.getContent().size() == 1);
        Project resultProject = resultPage.getContent().get(0);
        Assert.assertTrue(resultProject.getBoards().size() == 1 &&
                resultProject.getBoards().get(0).equals(board.getId()));


    }
}
