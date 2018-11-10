package com.recruitment.task.repoinfo.Unit;

import com.recruitment.task.repoinfo.aop.ApplicationErrorHandler;
import com.recruitment.task.repoinfo.controller.RepositoryController;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepoInfoApplicationTests {

    @Autowired
    private RepositoryController repositoryController;

    private MockMvc mockMvc;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(repositoryController).setControllerAdvice(new ApplicationErrorHandler()).build();
    }

    @Test
    public void should_return_repository() throws Exception {
        mockMvc
                .perform(get("/api/v1/repo/patrykidkowiak/currency-manager/-10").accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.fullName").value("patrykidkowiak/currency-manager"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_repo_not_found() throws Exception {
        mockMvc
                .perform(get("/api/v1/repo/X/X"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void pageLoadedSuccessfully() {
        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("hhttp://localhost:4200/");
        String dataOnPage = driver.getPageSource();
        assertTrue(dataOnPage.contains("info"));
        driver.quit();
    }

    @Test
    public void repositoryInfoLoadedSuccessfully() {
        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("hhttp://localhost:4200/");
        String dataOnPage = driver.getPageSource();
        assertTrue(dataOnPage.contains("info"));
        driver.quit();
    }
}
