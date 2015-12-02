package net.mpopov.tests;

import generated.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Tests
{
    private static final Logger LOGGER = Logger.getLogger(Tests.class);

    private WebDriver driver;

    private String baseUrl;

    private String adminPanelUrl;

    private List<String> pages = new ArrayList<String>();

    private String titleAdminPanel;

    private String testArticleName;

    private String testEditedArticleName;

    private String testTextArticle;

    private String login;

    private String password;

    private String testGalleryName;

    private String testFileGallery;

    private String filePath;

    private Integer implicitlyWait;

    @Before
    public void setUp() throws Exception
    {
        driver = new FirefoxDriver();
        implicitlyWait = Configuration.getInstance().getImplicitlyWait();
        driver.manage().timeouts()
                .implicitlyWait(implicitlyWait, TimeUnit.SECONDS);

        baseUrl = Configuration.getInstance().getBaseUrl();
        adminPanelUrl = Configuration.getInstance().getAdminPanelUrl();
        titleAdminPanel = Configuration.getInstance().getTitleAdminPanel();
        testArticleName = Configuration.getInstance().getTestArticleName();
        testEditedArticleName = Configuration.getInstance()
                .getTestEditedArticleName();
        testTextArticle = Configuration.getInstance().getTestTextArticle();
        login = Configuration.getInstance().getLogin();
        password = Configuration.getInstance().getPassword();
        testGalleryName = Configuration.getInstance().getTestGalleryName();
        testFileGallery = Configuration.getInstance().getTestFileGallery();
        filePath = Configuration.getInstance().getFilePath();
        pages = Configuration.getInstance().getPages().getPage();
    }

    @Test
    public void testAddTestArticle()
    {
        driver.get(baseUrl + adminPanelUrl);

        Assert.assertFalse(driver.getTitle().equals("404"));

        driver.findElement(By.name("j_username")).clear();
        driver.findElement(By.name("j_username")).sendKeys(login);
        driver.findElement(By.name("j_password")).clear();
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertFalse(driver.getTitle().equals("404"));
        Assert.assertTrue(driver.getTitle().equals(titleAdminPanel));

        driver.findElement(By.linkText("Наполнение")).click();
        driver.findElement(By.linkText("Статья")).click();
        driver.findElement(By.linkText("Добавить элемент")).click();

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys(testArticleName);
        driver.findElement(By.id("ufl-link")).click();
        new Select(driver.findElement(By
                .id("articleCategory.articleCategoryId")))
                .selectByVisibleText("Статьи");

        driver.switchTo().frame("description_ifr");
        driver.findElement(By.id("tinymce")).sendKeys(testTextArticle);

        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        Assert.assertFalse(driver.findElements(By.className("alert-success"))
                .isEmpty());
    }

    @Test
    public void testEditTestArticle()
    {
        driver.get(baseUrl + adminPanelUrl);

        Assert.assertFalse(driver.getTitle().equals("404"));

        driver.findElement(By.name("j_username")).clear();
        driver.findElement(By.name("j_username")).sendKeys(login);
        driver.findElement(By.name("j_password")).clear();
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertFalse(driver.getTitle().equals("404"));
        Assert.assertTrue(driver.getTitle().equals(titleAdminPanel));

        driver.findElement(By.linkText("Наполнение")).click();
        driver.findElement(By.linkText("Статья")).click();

        WebElement table = driver.findElement(By.id("articleList"));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));
        for (WebElement row : allRows)
        {
            if (row.getText().contains(testArticleName))
            {
                row.findElement(By.cssSelector("button")).click();
                driver.findElement(By.linkText("Редактировать")).click();

                driver.findElement(By.linkText("Добавить элемент")).click();
                driver.findElement(By.id("name")).clear();
                driver.findElement(By.id("name")).sendKeys(testGalleryName);
                driver.findElement(By.name("save")).click();

                Assert.assertFalse(driver.findElements(
                        By.className("alert-success")).isEmpty());

                driver.findElement(By.linkText("Добавить элемент")).click();
                driver.findElement(By.id("name")).clear();
                driver.findElement(By.id("name")).sendKeys(testFileGallery);
                driver.findElement(By.id("file")).sendKeys(filePath);
                driver.findElement(By.cssSelector("button.btn.btn-primary"))
                        .click();

                Assert.assertFalse(driver.findElements(
                        By.className("alert-success")).isEmpty());

                driver.findElement(By.name("saveAndClose")).click();

                Assert.assertFalse(driver.findElements(
                        By.className("alert-success")).isEmpty());

                driver.findElement(By.id("name")).clear();
                driver.findElement(By.id("name")).sendKeys(
                        testEditedArticleName);
                driver.findElement(By.id("ufl-link")).click();

                driver.findElement(By.cssSelector("button.btn.btn-primary"))
                        .click();
                break;
            }
        }
        Assert.assertFalse(driver.findElements(By.className("alert-success"))
                .isEmpty());
    }

    @Test
    public void testDeleteTestArticle()
    {
        driver.get(baseUrl + adminPanelUrl);

        Assert.assertFalse(driver.getTitle().equals("404"));

        driver.findElement(By.name("j_username")).clear();
        driver.findElement(By.name("j_username")).sendKeys(login);
        driver.findElement(By.name("j_password")).clear();
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertFalse(driver.getTitle().equals("404"));
        Assert.assertTrue(driver.getTitle().equals(titleAdminPanel));

        driver.findElement(By.linkText("Наполнение")).click();
        driver.findElement(By.linkText("Статья")).click();

        WebElement table = driver.findElement(By.id("articleList"));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));
        for (WebElement row : allRows)
        {
            if (row.getText().contains(testEditedArticleName))
            {
                row.findElement(By.cssSelector("button")).click();
                driver.findElement(By.linkText("Удалить")).click();
                WebElement dialog = driver.findElement(By
                        .className("modal-dialog"));
                dialog.findElement(By.className("btn-primary")).click();
                break;
            }
        }
        Assert.assertFalse(driver.findElements(By.className("alert-success"))
                .isEmpty());
    }

    @Test
    public void testLoadPages()
    {
        for (String page : pages)
        {
            driver.get(baseUrl + page);

            LOGGER.info("TITLE: " + driver.getTitle());

            Assert.assertFalse(driver.findElements(By.className("footer"))
                    .isEmpty());
            Assert.assertFalse(driver.getTitle().contains("404"));
        }
    }

    @After
    public void tearDown() throws Exception
    {
        driver.quit();
    }
}
