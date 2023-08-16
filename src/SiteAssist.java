//package portalAutomation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class SiteAssist {
    private static String username = "shuklaoffice";
    private static String sponsor = "Test Sponsor";

    private static String loginId = "shuklaoffice4@gmail.com";
    private static String loginPassword = "Testing1!";

    private static ArrayList < String > errorLabels;
    private static WebDriver driver;

    private static int totalShortlistCount;
    private static int shortlistCount;
    private static int activeCount;
    private static int totalSearchCount;

    private static int initialSiteCount;

    private static void sop(String msg) {
        System.out.println("[CUSTOM MSG********] " + msg);
    }

    private static String generateSearchName(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    private static void checkErrorPage() throws InterruptedException {
        if (driver.getCurrentUrl().equalsIgnoreCase(
                "https://testinsightsportal.intrinsiq.com/Error")) {
            sop("Reached Error Page. Restarting program execution...");
            runAutomation();
            System.exit(0);
        }
    }

    private static void runAutomation() throws InterruptedException {
        String searchName = null;
        String shortlistName = null;
        errorLabels = new ArrayList < > ();

        try {

            driver.get("https://testinsightsportal.intrinsiq.com/Login");
            // Login
            loginLogoutNav();
            loginNav();

            // Insight Portal
            insightPortalNav();

            // verifySearch();

            // Dashboard Page
            dashboardNav();
            
            // Verify Search options 
            verifySearchOptions();
            
            // Create Search
            searchName = createSearch();

            // Search Results Page
            siteResultsNav();

            checkErrorPage();
            
            searchName = checkSiteSummaryData(searchName);
            verifySearchInDashboard(searchName);
            checkDetailedProfileData(searchName);
            
            shortlistName = addToShortList();
            addAdvancedFiltersToShortlist();
            String shortlistRefNum = checkSiteShortlistPage(searchName,
                shortlistName);
            verifySearchBox(shortlistRefNum);
            createSearchCecalCancer();
            
        } catch (Exception e) {
            sop("ERROR OCCURED : " + e.getMessage());
        } finally {
            printErrors();
        }
    }

    private static void loginNav() throws InterruptedException {
        Actions action = new Actions(driver);
        sop("loading login page");
        // driver.get("https://testinsightsportal.intrinsiq.com/Login");

        // Thread.sleep(3000);
        sop("providing user credentials");
        driver.findElement(By.className("k-input-inner")).sendKeys(loginId);
        // driver.findElement(By.cssSelector("k-textbox.k-input.k-input-md.k-input-solid.k-rounded-md.k-invalid
        // k-required"));
        driver.findElement(By.cssSelector("input[type='password']"))
            .sendKeys(loginPassword);
        Thread.sleep(500);
        action.moveToElement(driver.findElement(By.className("button-text")));
        Thread.sleep(500);
        driver.findElement(By.className("button-text")).click();
        Thread.sleep(5000);
        checkErrorPage();
        sop("Clicked login, going to dashboard");
    }

    private static void loginLogoutNav() throws InterruptedException {
    	String jsStyle = "'3px solid red'";
        String jsStyleRemove = "'0px solid red'";

        sop("loading login page");
        // driver.get("https://testinsightsportal.intrinsiq.com/Login");

        // Thread.sleep(3000);
        sop("providing user credentials");
        driver.findElement(By.className("k-input-inner")).sendKeys(loginId);
        // driver.findElement(By.cssSelector("k-textbox.k-input.k-input-md.k-input-solid.k-rounded-md.k-invalid
        // k-required"));
        driver.findElement(By.cssSelector("input[type='password']"))
            .sendKeys(loginPassword);
        Thread.sleep(500);
        // action.moveToElement(driver.findElement(By.className("button-text")));
        // Thread.sleep(500);
        driver.findElement(By.className("button-text")).click();
        Thread.sleep(5000);
        checkErrorPage();
        sop("Clicked login, going to dashboard");
        // Thread.sleep(2000);

        // Change Password button
        WebElement usernameHvrBtn = driver.findElement(By.xpath(
            "/html/body/div/div/article/nav/section[2]/div/ul/li/span"));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].style.border=" + jsStyle, usernameHvrBtn);
        Thread.sleep(1500);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].style.border=" + jsStyleRemove, usernameHvrBtn);

        Actions builder = new Actions(driver);
        builder.moveToElement(usernameHvrBtn).click().build().perform();
        Thread.sleep(500);

        WebElement element = driver
            .findElement(By.xpath("/html/body/div[2]/div/ul/li[1]/span"));
        highlight(element);
        element.click();
        Thread.sleep(2000);

        sop("URL Received: " + driver.getCurrentUrl());
        if (driver.getCurrentUrl().trim().equals(
                "https://testinsightsportal.intrinsiq.com/ChangePassword")) {
            sop("Change Password URL matched.");
        } else {
            sop("Change Password URL not matching");
        }

        // Cancel Button
        element = driver.findElement(By
            .xpath("/html/body/div/div/article/section/section/button[1]"));
        highlight(element);
        element.click();

        // Going back to last page
        // driver.navigate().back();
        Thread.sleep(1000);
        checkErrorPage();

        // Log Out button
        usernameHvrBtn = driver.findElement(By.xpath(
            "/html/body/div/div/article/nav/section[2]/div/ul/li/span"));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].style.border=" + jsStyle, usernameHvrBtn);
        Thread.sleep(1500);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].style.border=" + jsStyleRemove, usernameHvrBtn);

        builder = new Actions(driver);
        builder.moveToElement(usernameHvrBtn).click().build().perform();
        Thread.sleep(500);

        element = driver
            .findElement(By.xpath("/html/body/div[2]/div/ul/li[2]/span"));
        highlight(element);
        element.click();
        Thread.sleep(2000);

        // Login Again button
        element = driver.findElement(
            By.xpath("/html/body/div/div/article/section/button"));
        highlight(element);
        element.click();
        Thread.sleep(2000);
        checkErrorPage();
    }

    private static void insightPortalNav() throws InterruptedException {
        String jsStyle = "'3px solid red'";
        String jsStyleRemove = "'0px solid red'";

        // Verify HomePage URL
        if (driver.getCurrentUrl()
            .equals("https://testinsightsportal.intrinsiq.com/Home")) {
            sop("Home Page URL is correct");
        } else {
            sop("Home Page URL is not correct");
            errorLabels.add("Home Page URL incorrect.");
        }

        // Welcome message
        WebElement element = driver.findElement(By.xpath(
            "/html/body/div/div/article/section/section/section[1]"));
        highlight(element);

        // Page Title - Site Assist
        element = driver.findElement(By.xpath(
            "/html/body/div/div/article/section/section/article/header/p"));
        highlight(element);

        // Clicking on Explore button
        element = driver.findElement(By.xpath(
            "/html/body/div/div/article/section/section/article/section/button"));
        highlight(element);
        element.click();
        Thread.sleep(2000);
        checkErrorPage();
    }

    // private static void verifySearch() throws InterruptedException {
    // String searchName = generateSearchName(7);
    // WebElement element;
    // Actions builder = new Actions(driver);
    //
    // sop("Viewing search page");
    //
    // String jsStyle = "'3px solid red'";
    // String jsStyleRemove = "'0px solid red'";
    //
    // sop("Creating New Search");
    // element =
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[2]/header/button"));
    //
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyle, element);
    // Thread.sleep(3000);
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyleRemove, element);
    // element.click();
    //
    // // Create New Search
    // Boolean firstAttempt = true;
    // while (true) {
    // sop("Entering search details");
    // //
    // driver.findElement(By.xpath("/html/body/div/div/article/section/div/div[2]/div[1]/span/span/input")).sendKeys("stu123");
    //
    // searchName = generateSearchName(7);
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/span/span/input"))
    // .sendKeys(searchName); // Search Name
    // WebElement diagnosesDropDown = driver
    // .findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/article[1]/span/span/input"));
    //
    // while (diagnosesDropDown.getAttribute("class").contains("disabled")) {
    // sop("Waiting to enable dropdowns...");
    // Thread.sleep(2000);
    // checkErrorPage();
    // }
    // sop("Drop downs enabled. Proceeding now.");
    // Thread.sleep(1000);
    //
    // if (firstAttempt) {
    // diagnosesDropDown.click(); // Drop down click
    // Thread.sleep(1500);
    // // Choose breast cancer option
    // driver.findElement(By.xpath("/html/body/overlay-container/article[1]/div[2]/article[3]/section/span/input")).click();
    // Thread.sleep(1500);
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/div[1]"))
    // .click(); // Click on search name to close drop down
    //
    // sop("Starting to choose options...");
    //
    // // Open dropdown for Biomarkers
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/article[2]/span/span/input")).click();
    // Thread.sleep(1000);
    //
    // boolean i29 = false;
    //
    // // Filtering through text box
    // try {
    // i29 = true;
    // driver.findElement(By.xpath("/html/body/overlay-container/article[29]/div[1]/span/input")).click();
    // driver.findElement(By.xpath("/html/body/overlay-container/article[29]/div[1]/span/input")).sendKeys("her");
    // }
    // catch (NoSuchElementException e) {
    // i29 = false;
    // driver.findElement(By.xpath("/html/body/overlay-container/article[2]/div[1]/span/input")).click();
    // driver.findElement(By.xpath("/html/body/overlay-container/article[2]/div[1]/span/input")).sendKeys("her");
    // }
    // Thread.sleep(1000);
    // if(i29) {
    // // Checkbox for HER-2 neu receptor assay
    // driver.findElement(By.xpath("/html/body/overlay-container/article[3]/div[2]/article/span/input")).click();
    // Thread.sleep(1000);
    // // Open dropdown for option
    // driver.findElement(By.xpath("/html/body/overlay-container/article[3]/div[2]/article/article/span/input")).click();
    // Thread.sleep(1000);
    // // Choose positive from dropdown
    // driver.findElement(By.xpath("/html/body/overlay-container/article[2]/ul/li[3]")).click();
    // }
    // else {
    // // Checkbox for HER-2 neu receptor assay
    // driver.findElement(By.xpath("/html/body/overlay-container/article[2]/div[2]/article/span/input")).click();
    // Thread.sleep(1000);
    // // Open dropdown for option
    // driver.findElement(By.xpath("/html/body/overlay-container/article[2]/div[2]/article/article/span/input")).click();
    // Thread.sleep(1000);
    // // Choose positive from dropdown
    // driver.findElement(By.xpath("/html/body/overlay-container/article[4]/ul/li[3]")).click();
    // Thread.sleep(1000);
    // }
    //
    // // Click on 'Create New Search' label to close drop down
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/div[1]")).click();
    //
    // Thread.sleep(1000);
    //
    // // Open dropdown for Treatments
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/article[3]/span/span/input")).click();
    // Thread.sleep(1000);
    // // Radiobutton for Drug Inclusion
    // driver.findElement(By.xpath("/html/body/overlay-container/article[4]/section/div[1]/input")).click();
    // Thread.sleep(1000);
    //
    // driver.findElement(By.xpath("/html/body/overlay-container/article[4]/div/div/span/input")).sendKeys("hercep");
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("/html/body/overlay-container/article[4]/div/article/span[1]/input")).click();
    //
    //// // Expand "chemotherapy - subcutaneous" option
    //// driver.findElement(By.xpath("/html/body/div[2]/div[30]/div/article[7]/section/span[2]")).click();
    //// Thread.sleep(1000);
    //// // Choose checkbox for "Herceptin - trastuzumab"
    //// driver.findElement(By.xpath("/html/body/div[2]/div[30]/div/article[7]/span[8]/input")).click();
    // // Click on 'Create New Search' label to close drop down
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/div[1]")).click();
    //
    // Thread.sleep(1500);
    // }
    //
    // // Search Button
    // element =
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[2]/button[2]"));
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyle, element);
    // Thread.sleep(3000);
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyleRemove, element);
    // element.click();
    //
    // Thread.sleep(1000);
    // checkErrorPage();
    //
    // // Check to see if search name already exists
    // if
    // (driver.findElements(By.xpath("/html/body/div/div/article/section/div/article/div[2]/div[3]/button"))
    // .size() > 0) {
    // driver.findElement(By.xpath("/html/body/div/div/article/section/div/article/div[2]/div[3]/button")).click();
    // sop("Search Name exists... Retrying with new search name");
    // driver.findElement(By.xpath("/html/body/div/div/article/section/div/div[2]/div[1]/span/span/input")).clear();
    // firstAttempt = false;
    // continue;
    // }
    // break;
    // }
    // siteResultsNav();
    //
    // // Clicking on Dashboard
    // element =
    // driver.findElement(By.xpath("/html/body/div/div/article/article/div[2]/ul/li[2]/button"));
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyle, element);
    // Thread.sleep(1500);
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyleRemove, element);
    // element.click();
    //
    // Thread.sleep(3000);
    //
    // if(driver.getCurrentUrl().equals("https://testinsightsportal.intrinsiq.com/SiteAssist/Dashboard"))
    // {
    // sop("Dashboard button working as expected");
    // }
    // else {
    // sop("Dashboard button not working as expected");
    // errorLabels.add("Dashboard button not getting back to Dashboard page.");
    // }
    // WebElement scrollSection =
    // driver.findElement(By.xpath("/html/body/div/div/article/section"));
    // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0,
    // 800)", scrollSection);
    //
    // // Last Page button
    // element =
    // driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[2]/div/div/div[5]/a[4]"));
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyle, element);
    // Thread.sleep(1500);
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyleRemove, element);
    //
    // if (!element.getAttribute("class").contains("disabled")) {
    // sop("Going to last page...");
    // Thread.sleep(2000);
    // element.click();
    // } else {
    // sop("Found shortlist in first page");
    // }
    //
    // int i = 1;
    // while (!driver.findElement(
    // By.xpath("/html/body/div/div/article/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr["
    // + String.valueOf(i) + "]/td[2]"))
    // .getText().contains(searchName)) {
    // i++;
    // if(i>5) {
    // sop("Search name not found in current page");
    // errorLabels.add("Search Name not found in search results");
    // return;
    // }
    // }
    //
    // String rowIndex = "";
    // if (i != 1) {
    // rowIndex = "[" + String.valueOf(i) + "]";
    // }
    //
    // element = driver.findElement(
    // By.xpath("/html/body/div/div/article/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr["
    // + String.valueOf(i) + "]/td[2]"));
    //
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyle, element);
    // Thread.sleep(1500);
    // ((JavascriptExecutor) driver).executeScript("arguments[0].style.border="
    // + jsStyleRemove, element);
    // sop("Search record found");
    //
    // }

    private static void dashboardNav() throws InterruptedException {
        WebElement element;
        Actions builder = new Actions(driver);

        sop("Viewing Dashboard page");

        // Verify Dashboard page URL
        if (driver.getCurrentUrl().equals(
                "https://testinsightsportal.intrinsiq.com/SiteAssist/Dashboard")) {
            sop("Dashboard Page URL is correct");
        } else {
            sop("Dashboard Page URL is not correct");
            errorLabels.add("Dashboard Page URL incorrect.");
        }

        String jsStyle = "'3px solid red'";
        String jsStyleRemove = "'0px solid red'";

        // Verifying Home button
        element = driver.findElement(By.xpath(
            "/html/body/div/div/article/article/div[2]/ul/li[1]/button/span"));
        highlight(element);
        element.click();
        Thread.sleep(2000);

        if (driver.getCurrentUrl()
            .equals("https://testinsightsportal.intrinsiq.com/Home")) {
            sop("Home button working as expected");
        } else {
            sop("Home button not working as expected");
            errorLabels.add("Home button not getting back to Home page.");
        }

        // Going back to Dashboard - Clicking on Explore button
        element = driver.findElement(By.xpath(
            "/html/body/div/div/article/section/section/article/section/button/span"));
        highlight(element);
        element.click();
        Thread.sleep(2000);

        // // Verifying Dashboard button
        // // Clicking on New Search button to move to new page
        // element =
        // driver.findElement(By.xpath("/html/body/div/div/article/section/div/article[2]/header/button/span[1]"));
        // ((JavascriptExecutor)
        // driver).executeScript("arguments[0].style.border=" + jsStyle,
        // element);
        // Thread.sleep(1500);
        // ((JavascriptExecutor)
        // driver).executeScript("arguments[0].style.border=" + jsStyleRemove,
        // element);
        // element.click();
        // Thread.sleep(2000);
        //
        // // Clicking on Dashboard button now
        // element =
        // driver.findElement(By.xpath("/html/body/div/div/article/article/div[2]/ul/li[2]/button/span"));
        // ((JavascriptExecutor)
        // driver).executeScript("arguments[0].style.border=" + jsStyle,
        // element);
        // Thread.sleep(1500);
        // ((JavascriptExecutor)
        // driver).executeScript("arguments[0].style.border=" + jsStyleRemove,
        // element);
        // element.click();
        // Thread.sleep(2000);
        //
        // if(driver.getCurrentUrl().equals("https://testinsightsportal.intrinsiq.com/SiteAssist/Dashboard"))
        // {
        // sop("Dashboard button working as expected");
        // }
        // else {
        // sop("Dashboard button not working as expected");
        // errorLabels.add("Dashboard button not getting back to Dashboard
        // page.");
        // }

        /**
         * Temporary commenting
         */
        // Header Image
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/nav/section[1]"));
        highlight(element);

        // Header Title
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/nav/section[1]/div/h4"));
        highlight(element);
        if (element.getText().trim().contains("Site Assist"))
            sop("'Site Assist' title matched.");
        else {
            sop("'Site Assist' title not matching");
            errorLabels.add("'Site Assist' title not matching");
        }

        // Page Title - Site Assist - Version
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/nav/section[1]/div/h4/span"));
        highlight(element);
        if (element.getText().trim().equals("v2.2"))
            sop("'Site Assist' title version matched.");
        else {
            sop("'Site Assist' title version not matching");
            errorLabels.add("'Site Assist' title version not matching");
        }

        // Title
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/article/div[1]/div/div"));
        highlight(element);

        if (element.getText().equals("Dashboard")) {
            sop("'Dashboard' title matched");
        } else {
            sop("'Dashboard' title mismatched.");
            errorLabels.add("'Dashboard' title incorrect in dashboard page");
        }

        // Sponsor Label
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/nav/section[2]/div[1]/span[1]"));
        highlight(element);

        if (element.getText().contains("Sponsor")) {
            sop("'Sponsor' label matched.");
        } else {
            sop("'Sponsor' label mismatched.");
            errorLabels.add("'Sponsor' label incorrect in dashboard page");
        }

        // Sponsor Value
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/nav/section[2]/div[1]/span[2]"));
        highlight(element);

        if (element.getText().equals(sponsor)) {
            sop("Sponsor value matched.");
        } else {
            sop("Sponsor value mismatched.");
            errorLabels.add("Sponsor value incorrect in dashboard page");
        }

        // Username Value
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/nav/section[2]/div[2]/ul/li/span/span[1]"));
        highlight(element);

        if (element.getText().equals(username)) {
            sop("Username value matched.");
        } else {
            sop("Username value mismatched.");
            errorLabels.add("Username value incorrect in dashboard page");
        }

        // Help Button
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/article/div[2]/button[2]"));
        highlight(element);
        element.click();

        // User Guide
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/article/article/button[1]"));
        builder.moveToElement(element).perform();
        Thread.sleep(1500);
        builder.click().build().perform();

        Thread.sleep(2000);
        ArrayList < String > browserTabs = new
        ArrayList < String > (driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        Thread.sleep(1500);
        if (driver.getCurrentUrl().equals("https://testinsightsportal.intrinsiq.com/Userguide.pdf")) {
            sop("Userguide PDF url matched");
        } else {
            sop("Userguide PDF url mismatch");
            errorLabels.add("Userguide PDF url mismatch");
        }
        driver.close();
        driver.switchTo().window(browserTabs.get(0));

        // FAQ
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/article/article/button[2]"));
        builder.moveToElement(element).perform();
        Thread.sleep(1500);
        builder.click().build().perform();

        Thread.sleep(2000);
        browserTabs = new ArrayList < String > (driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        Thread.sleep(1500);
        if (driver.getCurrentUrl().equals("https://testinsightsportal.intrinsiq.com/FAQ.pdf")) {
            sop("FAQ PDF url matched");
        } else {
            sop("FAQ PDF url mismatch");
            errorLabels.add("FAQ PDF url mismatch");
        }
        driver.close();
        driver.switchTo().window(browserTabs.get(0));

        // Terms & Conditions
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/article/article/button[3]"));
        builder.moveToElement(element).perform();
        Thread.sleep(1500);
        builder.click().build().perform();

        Thread.sleep(2000);
        browserTabs = new ArrayList < String > (driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        Thread.sleep(1500);
        if (driver.getCurrentUrl().equals("https://www.amerisourcebergen.com/global-terms-and-conditions")) {
            sop("Terms & Conditions url matched");
        } else {
            sop("Terms & Conditions url mismatch");
            errorLabels.add("Terms & Conditions url mismatch");
        }
        driver.close();
        driver.switchTo().window(browserTabs.get(0));

        // Privacy Policy
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/article/article/button[4]"));
        builder.moveToElement(element).perform();
        Thread.sleep(1500);
        builder.click().build().perform();

        Thread.sleep(2000);
        browserTabs = new ArrayList < String > (driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        Thread.sleep(1500);
        if (driver.getCurrentUrl().equals("https://www.amerisourcebergen.com/global-privacy-statement-overview")) {
            sop("Privacy Policy url matched");
        } else {
            sop("Privacy Policy url mismatch");
            errorLabels.add("Privacy Policy url mismatch");
        }
        driver.close();
        driver.switchTo().window(browserTabs.get(0));

        // Help Button
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/article/div[2]/button[2]"));
        highlight(element);
        element.click();

        // Total Shortlist count
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/header/article[1]/span/span"));
        highlight(element);
        totalShortlistCount = Integer.parseInt(element.getText());
        sop("Total Shortlist count : " +
            String.valueOf(totalShortlistCount));

        // Active count
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/header/article[2]/button[1]/span"));
        highlight(element);
        activeCount = Integer.parseInt(element.getText().split(" ")[0]);
        sop("Active count : " + String.valueOf(activeCount));
        element.click();
        verifyTableStatus("Active");


        // Shortlist Submitted count
        element =
            driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[1]/header/article[2]/button[2]/span"));
        highlight(element);
        shortlistCount = Integer.parseInt(element.getText().split(" ")[0]);
        sop("Shortlist count : " + String.valueOf(shortlistCount));
        element.click();
        verifyTableStatus("Submitted");

        // AIQ Review
        element = driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[1]/header/article[2]/button[3]/span"));
        highlight(element);
        element.click();
        verifyTableStatus("AIQ Review");

        // Sponsor Review
        element = driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[1]/header/article[2]/button[4]/span"));
        highlight(element);
        element.click();
        verifyTableStatus("Sponsor Review");

        // Approved
        element = driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[1]/header/article[2]/button[5]/span"));
        highlight(element);
        element.click();
        verifyTableStatus("Approved");

        // Withdrawn
        element = driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[1]/header/article[2]/button[6]/span"));
        highlight(element);
        element.click();
        verifyTableStatus("Withdrawn");

        // Site Recruitment
        element = driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[1]/header/article[2]/button[7]/span"));
        highlight(element);
        element.click();
        verifyTableStatus("Site Recruitment");

        // Completed
        element = driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[1]/header/article[2]/button[8]/span"));
        highlight(element);
        element.click();
        verifyTableStatus("Completed");
        

        // Returning to SHOW ALL option
        element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/header/article[2]/button[9]/span"));
        highlight(element);
        element.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        /**
         * Table Headers Verification
         * **************************
         */

        // Shortlist Summary Table Title
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/header/div/span"));
        highlight(element);
        if (element.getText().equals("Shortlist Summary")) {
            sop("Table name 'Shortlist Summary' matched.");
        } else {
            sop("Table name 'Shortlist Summary' matched.");
            errorLabels.add("'Shortlist Summary' table name mismatch");
        }

        // Shortlist Summary Table Headers

        // Ref #
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[1]/span[1]/span/span[1]"));
        highlight(element);

        if (element.getText().equals("Ref #"))
            sop("'Ref #' header matched in Shortlist Summary Table.");
        else {
            sop("'Ref #' not matching in Shortlist Summary Table.");
            errorLabels.add("'Ref #' header mismatch in Shortlist Summary Table");
        }

        // Shortlist Name
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[2]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Shortlist Name"))
            sop("'Shortlist Name' header matched in Shortlist Summary Table.");
        else {
            sop("'Shortlist Name' not matching in Shortlist Summary Table.");
            errorLabels.add("'Shortlist Name' header mismatch in Shortlist Summary Table");
        }

        // Protocol Name
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[3]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Protocol Name"))
            sop("'Protocol Name' header matched in Shortlist Summary Table.");
        else {
            sop("'Protocol Name' not matching in Shortlist Summary Table.");
            errorLabels.add("'Protocol Name' header mismatch in Shortlist Summary Table");
        }

        // Shortlisted Sites
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[4]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Shortlisted Sites"))
            sop("'Shortlisted Sites' header matched in Shortlist Summary Table.");
        else {
            sop("'Shortlisted Sites' not matching in Shortlist Summary Table.");
            errorLabels.add("'Shortlisted Sites' header mismatch in Shortlist Summary Table");
        }

        // Date Created
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[5]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Date Created"))
            sop("'Date Created' header matched in Shortlist Summary Table.");
        else {
            sop("'Date Created' not matching in Shortlist Summary Table.");
            errorLabels.add("'Date Created' header mismatch in Shortlist Summary Table");
        }

        // Date Modified
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[6]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Date Modified"))
            sop("'Date Modified' header matched in Shortlist Summary Table.");
        else {
            sop("'Date Modified' not matching in Shortlist Summary Table.");
            errorLabels.add("'Date Modified' header mismatch in Shortlist Summary Table");
        }

        // Status
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[7]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Status"))
            sop("'Status' header matched in Shortlist Summary Table.");
        else {
            sop("'Status' not matching in Shortlist Summary Table.");
            errorLabels.add("'Status' header mismatch in Shortlist Summary Table");
        }

        // Action
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[3]/div/table/thead/tr/th[8]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Action"))
            sop("'Action' header matched in Shortlist Summary Table.");
        else {
            sop("'Action' not matching in Shortlist Summary Table.");
            errorLabels.add("'Action' header mismatch in Shortlist Summary Table");
        }

        // Scrolling down to bring Search Table into view
        WebElement scrollSection =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0,350)", scrollSection);


        // Search Summary Table Title
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/header/div/span"));
        highlight(element);
        if (element.getText().equals("Search Summary")) {
            sop("Table name 'Search Summary' matched.");
        } else {
            sop("Table name 'Search Summary' matched.");
            errorLabels.add("'Search Summary' table name mismatch");
        }

        // Total Search count
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/header/article/span/span"));
        highlight(element);
        totalSearchCount = Integer.parseInt(element.getText());
        sop("Total Search count : " + String.valueOf(totalSearchCount));

        // Search Summary Table Headers

        // Ref #
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[3]/div/table/thead/tr/th[1]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Ref #"))
            sop("'Ref #' header matched in Search Summary Table.");
        else {
            sop("'Ref #' not matching in Search Summary Table.");
            errorLabels.add("'Ref #' header mismatch in Search Summary Table");
        }

        // Search Name
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[3]/div/table/thead/tr/th[2]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Search Name"))
            sop("'Search Name' header matched in Search Summary Table.");
        else {
            sop("'Search Name' not matching in Search Summary Table.");
            errorLabels.add("'Search Name' header mismatch in Search Summary Table");
        }

        // Applied Filter
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[3]/div/table/thead/tr/th[3]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Applied Filter"))
            sop("'Applied Filter' header matched in Search Summary Table.");
        else {
            sop("'Applied Filter' not matching in Search Summary Table.");
            errorLabels.add("'Applied Filter' header mismatch in Search Summary Table");
        }

        // Current Shortlist
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[3]/div/table/thead/tr/th[4]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Current Shortlist"))
            sop("'Current Shortlist' header matched in Search Summary Table.");
        else {
            sop("'Current Shortlist' not matching in Search Summary Table.");
            errorLabels.add("'Current Shortlist' header mismatch in Search Summary Table");
        }

        // Date Created
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[3]/div/table/thead/tr/th[5]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Date Created"))
            sop("'Date Created' header matched in Search Summary Table.");
        else {
            sop("'Date Created' not matching in Search Summary Table.");
            errorLabels.add("'Date Created' header mismatch in Search Summary Table");
        }

        // Date Modified
        element =
            driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[3]/div/table/thead/tr/th[6]/span[1]/span/span"));
        highlight(element);

        if (element.getText().equals("Date Modified"))
            sop("'Date Modified' header matched in Search Summary Table.");
        else {
            sop("'Date Modified' not matching in Search Summary Table.");
            errorLabels.add("'Date Modified' header mismatch in Search Summary Table ");
            }

            // Action
            element =
                driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[3]/div/table/thead/tr/th[7]/span[1]/span/span"));
            highlight(element);

            if (element.getText().equals("Action"))
                sop("'Action' header matched in Search Summary Table.");
            else {
                sop("'Action' not matching in Search Summary Table.");
                errorLabels.add("'Action' header mismatch in Search Summary Table");
            }
            // End of table headers verification

            // Create New Search button
            sop("Creating New Search");
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[2]/header/button"));
            highlight(element);
            element.click();
            Thread.sleep(2000);
            checkErrorPage();

            // Cancel on Search page
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/div[2]/div[2]/button[1]"));
            highlight(element);
            element.click();
            Thread.sleep(2000);
            checkErrorPage();

            // Verify Dashboard page URL
            if (driver.getCurrentUrl().equals(
                    "https://testinsightsportal.intrinsiq.com/SiteAssist/Dashboard")) {
                sop("Search cancel button is working");
            } else {
                sop("Search cancel button is not working");
                errorLabels.add("Search cancel button is not working");
            }
        }
    
    	private static void verifySearchOptions() throws InterruptedException {
    		String searchName = createSearch();
    		
    		// Going back to Dashboard
    		WebElement element = driver.findElement(By.xpath("/html/body/div/div/article[1]/article/div[2]/ul/li[2]/button"));
    		highlight(element);
    		element.click();
    		Thread.sleep(3000);
    		
    		// Typing search name in search box
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[1]/div[2]/span/input"));
    		highlight(element);
    		element.click();
    		element.sendKeys(searchName);
    		Thread.sleep(500);
    		
    		/**
    		 * EDIT option
    		 * ***********
    		 */
    		// Clicking on Option Button
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr/td[7]/div/ul/li/span/button"));
    		highlight(element);
    		element.click();
    		
    		// Clicking on EDIT option
    		element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[1]/span/span[2]"));
    		highlight(element);
    		element.click();
    		
    		Thread.sleep(2000);    		    		    		    		
    		
    		if(driver.getCurrentUrl().contains("https://testinsightsportal.intrinsiq.com/SiteAssist/SiteSearch/"))
    			sop("'Edit' option in Site Search table is working and directing to Site Summary page");
    		else {
    			sop("'Edit' option in Site Search table is not working as expected");
    			errorLabels.add("'Edit' option in Site Search table");
    		}
    		
    		WebElement loadingMask = driver.findElement(
                    By.xpath("/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading results...");
                    Thread.sleep(2000);
                    checkErrorPage();
                }    		    		
    		
    		// Going back to Search page
    		driver.navigate().back();
    		Thread.sleep(3000);
    		
    		/**
    		 * DUPLICATE option
    		 * ****************
    		 */
    		// Typing search name in search box
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[1]/div[2]/span/input"));
    		highlight(element);
    		element.click();
    		element.sendKeys(searchName);
    		
    		// Clicking on Option Button
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr/td[7]/div/ul/li/span/button"));
    		highlight(element);
    		element.click();
    		
    		// Clicking on DUPLICATE option
    		element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[3]/span/span[2]"));
    		highlight(element);
    		element.click();
    		
    		// Clicking on Duplicate Button
			element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/article[2]/div[2]/div[3]/button[2]"));
    		highlight(element);
    		element.click();
    		
    		Thread.sleep(5000);
    		
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr[2]/td[2]"));
    		highlight(element);
    		if(element.getText().equalsIgnoreCase(searchName + " - copy"))
    			sop("'Duplicate' option is Site Search table is working and creating a duplicate record");
    		else {
    			sop("'Duplicate' option is Site Search table is not working as expected");
    			errorLabels.add("'Duplicate' option is Site Search table");
    		}
    		
    		/**
    		 * DELETE option
    		 * *************
    		 */
    		// Clicking on Option Button
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr[2]/td[7]/div/ul/li/span/button"));
    		highlight(element);
    		element.click();
    		
    		// Clicking on DELETE option
    		element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[2]/span/span[2]"));
    		highlight(element);
    		element.click();
    		
    		// Clicking on Delete Button
			element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/article[1]/div[2]/div[3]/button[2]"));
    		highlight(element);
    		element.click();
    		
    		Thread.sleep(5000);
    		
    		// Typing search name in search box
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[1]/div[2]/span/input"));
    		highlight(element);
    		element.click();
    		element.clear();
    		element.sendKeys(searchName + " - copy");
    		
    		element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr/td"));
    		highlight(element);
    		
    		if(element.getText().equalsIgnoreCase("No records available"))
    			sop("'Delete' option in Site Search table is working and deleting record");
    		else {
    			sop("'Delete' option in Site Search table is not working as expected");
    			errorLabels.add("'Delete' option in Site Search table ");
    		}    		
    	}

        private static String createSearch() throws InterruptedException {
            String searchName = null;
            WebElement element;
            // Actions builder = new Actions(driver);

            String jsStyle = "'3px solid red'";
            String jsStyleRemove = "'0px solid red'";
            
            // Go to search page 
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/header/button"));
            highlight(element);
            element.click();
            Thread.sleep(2000);
            checkErrorPage();

            // Create New Search
            Boolean firstAttempt = true;
            while (true) {
                sop("Entering search details");
                // driver.findElement(By.xpath("/html/body/div/div/article/section/div/div[2]/div[1]/span/span/input")).sendKeys("stu123");

                searchName = generateSearchName(7);
                driver.findElement(By.xpath(
                        "/html/body/div/div/article/section/article/div[2]/div[1]/span/span/input"))
                    .sendKeys(searchName); // Search Name
                WebElement diagnosesDropDown = driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/div[2]/div[1]/div[5]/article[1]/span/span"));

                while (diagnosesDropDown.getAttribute("class")
                    .contains("disabled")) {
                    sop("Waiting to enable dropdowns...");
                    Thread.sleep(2000);
                    checkErrorPage();
                }
                sop("Drop downs enabled. Proceeding now.");

                if (firstAttempt) {
                    // Choosing Breast Cancer 
                    diagnosesDropDown.click(); // Drop down click
                    Thread.sleep(1500);
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[1]/div[2]/article[3]/section/span/input"))
                        .click();
                    Thread.sleep(1500);
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/div[2]/div[1]/div[1]"))
                        .click(); // Click on search name to close drop down

                    sop("Starting to choose options...");

                    // Open dropdown for Biomarkers
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/div[2]/div[1]/div[5]/article[2]/span/span/input"))
                        .click();
                    Thread.sleep(1000);

                    // // Click on Biomarkers text box
                    // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/div[2]/div[1]/div[5]/article[2]/span/span/input")).click();
                    // Thread.sleep(500);

                    // Click on checkbox for HER-2 option
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[29]/div/article[10]/span/input"))
                        .click();
                    Thread.sleep(500);

                    // Open dropdown
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[29]/div/article[10]/article/span/input"))
                        .click();
                    Thread.sleep(500);

                    // Choose positive
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[11]/article/ul/li[3]"))
                        .click();
                    Thread.sleep(500);
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/div[2]/div[1]/div[1]"))
                        .click(); // Click on search name to close drop down

                    // Click on treatment dropdown
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/div[2]/div[1]/div[5]/article[3]/span/span/input"))
                        .click();
                    Thread.sleep(500);

                    // Click on drug inclusion dropdown
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[30]/section/div[1]/div[1]/input"))
                        .click();
                    Thread.sleep(500);

                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[30]/div/div/span/input"))
                        .sendKeys("hercep");
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[30]/div/article/span[1]/input"))
                        .click();

                    /**
                     * 
                     * 
                     * boolean i29 = false;
                     * 
                     * // Filtering through text box try { i29 = true;
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[29]/div[1]/span/input")).click();
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[29]/div[1]/span/input")).sendKeys("her");
                     * } catch (NoSuchElementException e) { i29 = false;
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[29]/div/article[10]/span/input")).click();
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[2]/div[1]/span/input")).sendKeys("her");
                     * } Thread.sleep(1000); if (i29) { // Checkbox for HER-2 neu
                     * receptor assay
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[3]/div[2]/article/span/input")).click();
                     * Thread.sleep(1000); // Open dropdown for option
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[3]/div[2]/article/article/span/input")).click();
                     * Thread.sleep(1000); // Choose positive from dropdown
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[2]/ul/li[3]")).click();
                     * Thread.sleep(1000); } else { // Checkbox for HER-2 neu
                     * receptor assay
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[2]/div[2]/article/span/input")).click();
                     * Thread.sleep(1000); // Open dropdown for option
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[2]/div[2]/article/article/span/input")).click();
                     * Thread.sleep(1000); // Choose positive from dropdown
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[4]/ul/li[3]")).click();
                     * Thread.sleep(1000); }
                     * 
                     * // Click on 'Create New Search' label to close drop down
                     * driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/div[1]")).click();
                     * Thread.sleep(1000);
                     * 
                     * // Open dropdown for Treatments
                     * driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/article[3]/span/span/input")).click();
                     * Thread.sleep(1000); // Radiobutton for Drug Inclusion try {
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[4]/section/div[1]/input")).click();
                     * Thread.sleep(1000);
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[4]/div/div/span/input")).sendKeys("hercep");
                     * Thread.sleep(1000);
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[4]/div/article/span[1]/input")).click();
                     * } catch (NoSuchElementException e) {
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[3]/section/div[1]/input")).click();
                     * Thread.sleep(1000);
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[4]/div/div/span/input")).sendKeys("hercep");
                     * Thread.sleep(1000);
                     * driver.findElement(By.xpath("/html/body/overlay-container/article[4]/div/article/span[1]/input")).click();
                     * }
                     * 
                     * // // Expand "chemotherapy - subcutaneous" option //
                     * driver.findElement(By.xpath("/html/body/div[2]/div[30]/div/article[7]/section/span[2]")).click();
                     * // Thread.sleep(1000); // // Choose checkbox for "Herceptin -
                     * trastuzumab" //
                     * driver.findElement(By.xpath("/html/body/div[2]/div[30]/div/article[7]/span[8]/input")).click();
                     * // Click on 'Create New Search' label to close drop down
                     * driver.findElement(By.xpath("/html/body/div/div/article/section/article/div[2]/div[1]/div[1]")).click();
                     * 
                     */

                    Thread.sleep(1500);
                }

                // Search Button
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/div[2]/div[2]/button[2]"));
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border=" + jsStyle, element);
                Thread.sleep(3000);
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border=" + jsStyleRemove, element);
                element.click();

                Thread.sleep(1000);
                checkErrorPage();

                // Check to see if search name already exists
                if (driver.findElements(By.xpath(
                        "/html/body/div/div/article/section/div/article/div[2]/div[3]/button"))
                    .size() > 0) {
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/div/article/div[2]/div[3]/button"))
                        .click();
                    sop("Search Name exists... Retrying with new search name");
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/div/div[2]/div[1]/span/span/input"))
                        .clear();
                    firstAttempt = false;
                    continue;
                }
                break;
            }
            return searchName;
        }
        
        private static void createSearchCecalCancer() throws InterruptedException {
        	String searchName = null;
            WebElement element;
            // Actions builder = new Actions(driver);

            String jsStyle = "'3px solid red'";
            String jsStyleRemove = "'0px solid red'";
            
            // Go to search page 
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/header/button"));
            highlight(element);
            element.click();
            Thread.sleep(2000);
            checkErrorPage();

            // Create New Search
            Boolean firstAttempt = true;
            while (true) {
                sop("Entering search details");
                // driver.findElement(By.xpath("/html/body/div/div/article/section/div/div[2]/div[1]/span/span/input")).sendKeys("stu123");

                searchName = generateSearchName(7);
                driver.findElement(By.xpath(
                        "/html/body/div/div/article/section/article/div[2]/div[1]/span/span/input"))
                    .sendKeys(searchName); // Search Name
                WebElement diagnosesDropDown = driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/div[2]/div[1]/div[5]/article[1]/span/span"));

                while (diagnosesDropDown.getAttribute("class")
                    .contains("disabled")) {
                    sop("Waiting to enable dropdowns...");
                    Thread.sleep(2000);
                    checkErrorPage();
                }
                sop("Drop downs enabled. Proceeding now.");

                if (firstAttempt) {
                    // Choosing Breast Cancer 
                    diagnosesDropDown.click(); // Drop down click
                    Thread.sleep(1500);
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[1]/div[2]/article[4]/section/span/input"))
                        .click();
                    Thread.sleep(1500);
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/div[2]/div[1]/div[1]"))
                        .click(); // Click on search name to close drop down

                    sop("Starting to choose options...");

                    // Open dropdown for Biomarkers
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/div[2]/div[1]/div[5]/article[2]/span/span/input"))
                        .click();
                    Thread.sleep(1000);

//                     // Click on Biomarkers text box 
//                     element = driver.findElement(By.xpath("/html/body/overlay-container/article[3]/div[1]/span/input"));
//                     element.click();
//                     element.sendKeys("ntrk");
//                     Thread.sleep(500);

                    // Click on checkbox for NTRK-Fusion option
                    driver.findElement(By.xpath( 
                            "/html/body/overlay-container/article[29]/div[2]/article[19]/span/input"))
                        .click();
                    Thread.sleep(500);

                    // Open dropdown
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[29]/div[2]/article[19]/article/span/input"))
                        .click();
                    Thread.sleep(500);

                    // Choose negative
                    driver.findElement(By.xpath(
                            "/html/body/overlay-container/article[20]/article/ul/li[1]"))
                        .click();
                    Thread.sleep(500);
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/div[2]/div[1]/div[1]"))
                        .click(); // Click on search name to close drop down

//                    // Click on treatment dropdown
//                    driver.findElement(By.xpath(
//                            "/html/body/div/div/article[1]/section/article/div[2]/div[1]/div[5]/article[3]/span/span/input"))
//                        .click();
//                    Thread.sleep(500);
//
//                    // Click on drug inclusion dropdown
//                    driver.findElement(By.xpath(
//                            "/html/body/overlay-container/article[30]/section/div[1]/div[1]/input"))
//                        .click();
//                    Thread.sleep(500);
//
//                    driver.findElement(By.xpath(
//                            "/html/body/overlay-container/article[30]/div/div/span/input"))
//                        .sendKeys("hercep");
//                    driver.findElement(By.xpath(
//                            "/html/body/overlay-container/article[30]/div/article/span[1]/input"))
//                        .click();                 
//                    Thread.sleep(1500);
                }

                // Search Button
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/div[2]/div[2]/button[2]"));
                highlight(element);
                element.click();

                Thread.sleep(1000);
                checkErrorPage();

                // Check to see if search name already exists
                if (driver.findElements(By.xpath(
                        "/html/body/div/div/article/section/div/article/div[2]/div[3]/button"))
                    .size() > 0) {
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/div/article/div[2]/div[3]/button"))
                        .click();
                    sop("Search Name exists... Retrying with new search name");
                    driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/div/div[2]/div[1]/span/span/input"))
                        .clear();
                    firstAttempt = false;
                    continue;
                }
                break;
            }
            
            Thread.sleep(5000);
            
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[1]/div/article[2]"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("1 Cecal Cancer")) 
            	sop("Cecal Cancer search created");
            else {
            	sop("Cecal Cancer search creation failed");
            	errorLabels.add("Cecal Cancer search creation");
            }
        }

        private static void siteResultsNav() throws InterruptedException {
            if (!driver.getCurrentUrl().contains("SiteSearch")) {
                sop("Site Search page not loaded. Restarting program...");
                runAutomation();
                System.exit(0);
            } else
                sop("In Site Search page");

            // Verify if search results are loaded
            WebElement loadingMask;
            try {
                loadingMask = driver.findElement(By.xpath( 
                    "/html/body/div/div/article[1]/section/article/article[1]/svg"));
            } catch (NoSuchElementException e) {
                loadingMask = null;
            }
            while (loadingMask != null && !loadingMask.getCssValue("display")
                .equalsIgnoreCase("none")) {
                sop("Search results loading...");
                Thread.sleep(1000);
            }
            sop("Search results loaded");
        }

        private static String checkSiteSummaryData(String searchName)
        throws InterruptedException {
            WebElement element;
            Actions action = new Actions(driver);

            // Verify Site Search page URL
            if (driver.getCurrentUrl().contains(
                    "https://testinsightsportal.intrinsiq.com/SiteAssist/SiteSearch")) {
                sop("Site Search Page URL is correct");
            } else {
                sop("Site Search Page URL is not correct");
                errorLabels.add("Site Search Page URL incorrect.");
            }

            // Edit Search Name 
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/header/section[1]/div[2]/div/button"));
            highlight(element);
            element.click();

            searchName = searchName + "_test";
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/header/article/div[2]/div[2]/span/span/input"));
            highlight(element);
            element.clear();
            element.sendKeys(searchName);

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/header/article/div[2]/div[3]/button[2]"));
            highlight(element);
            element.click();

            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article/section/article/main/header"));
            // js.executeScript("arguments[0].scrollIntoView(true);", element);

            // Site Title
            element = driver.findElement(
                By.xpath("/html/body/div/div/article/article/div[1]/div/div"));
            highlight(element);
            sop(element.getText());
            if (element.getText().equals("Site Search"))
                sop("Page Title 'Site Search' matched");
            else {
                sop("Page Title 'Site Search' mismatch!");
                errorLabels.add("'Site Search' page title in Site Search page");
            }

            // Search Summary
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/header/span"));
            highlight(element);
            if (element.getText().equals("Search Summary"))
                sop("Heading 'Search Summary' matched in Site Search page");
            else {
                sop("Heading 'Search Summary' mismatch in Site Search page!");
                errorLabels.add("'Search Summary' heading in Site Search page");
            }

            // Search Name Value
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/header/section[1]/div[2]/div/span"));
            highlight(element);
            if (element.getText().equals(searchName))
                sop("Label Search Name value matched in Site Search page");
            else {
                sop("Label Search Name value mismatch in Site Search page!");
                errorLabels.add("Search Name value label in Site Search page");
            }

            // Search Name
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/header/section[1]/div[2]/span"));
            // js.executeScript("arguments[0].scrollIntoView(true);", element);
            highlight(element);
            if (element.getText().equalsIgnoreCase("SEARCH NAME"))
                sop("Label 'Search Name' matched in Site Search page");
            else {
                sop("Label 'Search Name' mismatch in Site Search page!");
                errorLabels.add("'Search Name' label in Site Search page");
            }

            // Matching Site count
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/header/section[2]/div[2]/span[1]"));
            highlight(element);
            initialSiteCount = Integer.parseInt(element.getText());

            // Matching Sites
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/header/section[2]/div[2]/span[2]"));
            highlight(element);
            if (element.getText().equals("MATCHING SITES"))
                sop("Label 'Matching Sites' matched in Site Search page");
            else {
                sop("Label 'Matching Sites' mismatch in Site Search page!");
                errorLabels.add("'Matching Sites' label in Site Search page");
            }

            // Matching Patients
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/header/section[3]/div[2]/span[2]"));
            highlight(element);
            if (element.getText().equals("MATCHING PATIENTS"))
                sop("Label 'Matching Patients' matched in Site Search page");
            else {
                sop("Label 'Matching Patients' mismatch in Site Search page!");
                errorLabels.add("'Matching Patients' label in Site Search page");
            }

            /**
             * Relevancy Filters 
             * *****************
             */
            // Relevancy Label
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/header/div/span"));
            highlight(element);
            if (element.getText().equals("Relevancy Filter"))
                sop("Label 'Relevancy Filter' matched in Site Search page");
            else {
                sop("Label 'Relevancy Filter' mismatch in Site Search page!");
                errorLabels.add("'Relevancy Filter' label in Site Search page");
            }

            // Diagnosis label
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[1]/span/label"));
            highlight(element);
            if (element.getText().equals("Diagnosis"))
                sop("Label 'Diagnosis' matched in Site Search page");
            else {
                sop("Label 'Diagnosis' mismatch in Site Search page!");
                errorLabels.add("'Diagnosis' label in Site Search page");
            }

            // Biomarkers
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[2]/span/label"));
            highlight(element);
            if (element.getText().equals("Biomarkers"))
                sop("Label 'Biomarkers' matched in Site Search page");
            else {
                sop("Label 'Biomarkers' mismatch in Site Search page!");
                errorLabels.add("'Biomarkers' label in Site Search page");
            }

            // Treatments
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[3]/span/label"));
            highlight(element);
            if (element.getText().equals("Treatments"))
                sop("Label 'Treatments' matched in Site Search page");
            else {
                sop("Label 'Treatments' mismatch in Site Search page!");
                errorLabels.add("'Treatments' label in Site Search page");
            }

            // Advanced Filter label
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/header/div/div/span[1]"));
            highlight(element);
            if (element.getText().equals("Advanced Filter"))
                sop("Label 'Advanced Filter' matched in Site Search page");
            else {
                sop("Label 'Advanced Filter' mismatch in Site Search page!");
                errorLabels.add("'Advanced Filter' label in Site Search page");
            }

            // Apply Advance Filters to refine your search
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/header/div/div/span[2]"));
            highlight(element);
            if (element.getText()
                .equals("Apply Advance Filters to refine your search"))
                sop("Label 'Apply Advance Filters to refine your search' matched in Site Search page");
            else {
                sop("Label 'Apply Advance Filters to refine your search' mismatch in Site Search page!");
                errorLabels.add(
                    "'Apply Advance Filters to refine your search' label in Site Search page");
            }

            // Click on Advanced Filter expand button 
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/header/button"));
            highlight(element);
            element.click();

            /**
             * Advanced Filters List 
             * *********************
             */
            // Patient Criteria
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/p[1]"));
            highlight(element);
            if (element.getText().trim().equals("Patient Criteria"))
                sop("Label 'Patient Criteria' matched in Site Search page");
            else {
                sop("Label 'Patient Criteria' mismatch in Site Search page!");
                errorLabels.add("'Patient Criteria' label in Site Search page");
            }

            // Last Active (months)
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            highlight(element);
            if (element.getText().equals("Last Active (months):"))
                sop("Label 'Last Active (months):' matched in Site Search page");
            else {
                sop("Label 'Last Active (months):' mismatch in Site Search page!");
                errorLabels
                    .add("'Last Active (months):' label in Site Search page");
            }

            // Stage at Diagnosis
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[1]/span/label"));
            highlight(element);
            if (element.getText().equals("Stage at Diagnosis"))
                sop("Label 'Stage at Diagnosis' matched in Site Search page");
            else {
                sop("Label 'Stage at Diagnosis' mismatch in Site Search page!");
                errorLabels.add("'Stage at Diagnosis' label in Site Search page");
            }

            // Line of Therapy
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[2]/span/label"));
            highlight(element);
            if (element.getText().equals("Line of Therapy"))
                sop("Label 'Line of Therapy' matched in Site Search page");
            else {
                sop("Label 'Line of Therapy' mismatch in Site Search page!");
                errorLabels.add("'Line of Therapy' label in Site Search page");
            }

            // ECOGs
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[3]/span/label"));
            highlight(element);
            if (element.getText().equals("ECOGs"))
                sop("Label 'ECOGs' matched in Site Search page");
            else {
                sop("Label 'ECOGs' mismatch in Site Search page!");
                errorLabels.add("'ECOGs' label in Site Search page");
            }

            // Comorbidities
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[4]/span/label"));
            highlight(element);
            if (element.getText().equals("Comorbidities"))
                sop("Label 'Comorbidities' matched in Site Search page");
            else {
                sop("Label 'Comorbidities' mismatch in Site Search page!");
                errorLabels.add("'Comorbidities' label in Site Search page");
            }

            // Race
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[5]/span/label"));
            highlight(element);
            if (element.getText().equals("Race"))
                sop("Label 'Race' matched in Site Search page");
            else {
                sop("Label 'Race' mismatch in Site Search page!");
                errorLabels.add("'Race' label in Site Search page");
            }

            // Ethnicity
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[6]/span/label"));
            highlight(element);
            if (element.getText().equals("Ethnicity"))
                sop("Label 'Ethnicity' matched in Site Search page");
            else {
                sop("Label 'Ethnicity' mismatch in Site Search page!");
                errorLabels.add("'Ethnicity' label in Site Search page");
            }

            // Scroll Advanced filters
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollBy(0, 500)", driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/article[2]")));

            // Gender
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/span"));
            highlight(element);
            if (element.getText().equals("Gender:"))
                sop("Label 'Gender:' matched in Site Search page");
            else {
                sop("Label 'Gender:' mismatch in Site Search page!");
                errorLabels.add("'Gender:' label in Site Search page");
            }

            // Gender option - All
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/section/div[1]/label"));
            highlight(element);
            if (element.getText().equals("All"))
                sop("Gender option Label 'All' matched in Site Search page");
            else {
                sop("Gender option Label 'All' mismatch in Site Search page!");
                errorLabels.add("Gender option 'All' label in Site Search page");
            }

            // Gender option - Male
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/section/div[2]/label"));
            highlight(element);
            if (element.getText().equals("Male"))
                sop("Gender option Label 'Male' matched in Site Search page");
            else {
                sop("Gender option Label 'Male' mismatch in Site Search page!");
                errorLabels.add("Gender option 'Male' label in Site Search page");
            }

            // Gender option - Female
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/section/div[3]/label"));
            highlight(element);
            if (element.getText().equals("Female"))
                sop("Gender option Label 'Female' matched in Site Search page");
            else {
                sop("Gender option Label 'Female' mismatch in Site Search page!");
                errorLabels.add("Gender option 'Female' label in Site Search page");
            }

            // Gender option - Other
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/section/div[4]/label"));
            highlight(element);
            if (element.getText().equals("Other"))
                sop("Gender option Label 'Other' matched in Site Search page");
            else {
                sop("Gender option Label 'Other' mismatch in Site Search page!");
                errorLabels.add("Gender option 'Other' label in Site Search page");
            }

            // Age
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[2]/label	"));
            highlight(element);
            if (element.getText().equals("Age:"))
                sop("Label 'Age:' matched in Site Search page");
            else {
                sop("Label 'Age:' mismatch in Site Search page!");
                errorLabels.add("'Age:' label in Site Search page");
            }

            // Site Criteria
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/p[2]"));
            highlight(element);
            if (element.getText().trim().equals("Site Criteria"))
                sop("Label 'Site Criteria' matched in Site Search page");
            else {
                sop("Label 'Site Criteria' mismatch in Site Search page!");
                errorLabels.add("'Site Criteria' label in Site Search page");
            }

            // State
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[8]/span/label"));
            highlight(element);
            if (element.getText().trim().equals("State"))
                sop("Label 'State' matched in Site Search page");
            else {
                sop("Label 'State' mismatch in Site Search page!");
                errorLabels.add("'State' label in Site Search page");
            }

            // Cancel button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/footer/button[1]/span"));
            highlight(element);
            if (element.getText().trim().equals("Cancel"))
                sop("Button Label 'Cancel' matched in Site Search page");
            else {
                sop("Button Label 'Cancel' mismatch in Site Search page!");
                errorLabels.add("'Cancel' button label in Site Search page");
            }

            // Reset All button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/footer/button[2]/span"));
            highlight(element);
            if (element.getText().trim().equals("Reset All"))
                sop("Button Label 'Reset All' matched in Site Search page");
            else {
                sop("Button Label 'Reset All' mismatch in Site Search page!");
                errorLabels.add("'Reset All' button label in Site Search page");
            }

            // Apply Filters button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/footer/button[3]/span"));
            highlight(element);
            if (element.getText().trim().equals("Apply Filters"))
                sop("Button Label 'Apply Filters' matched in Site Search page");
            else {
                sop("Button Label 'Apply Filters' mismatch in Site Search page!");
                errorLabels.add("'Apply Filters' button label in Site Search page");
            }

            // Scroll Advanced filters
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollBy(0, -500)", driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/article[2]")));
            
            /**
             *  Verifying SHOW ALL FILTERS button
             *  *********************************
             */
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/header/div[1]/span[2]"));
            highlight(element);
            element.click();
            
            Thread.sleep(1000);
            
            // Relevancy Filters
            
            // Diagnosis label
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[1]/span/label"));
            highlight(element);
            if (element.getText().equals("Diagnosis"))
                sop("Label 'Diagnosis' matched in Site Search page");
            else {
                sop("Label 'Diagnosis' mismatch in Site Search page!");
                errorLabels.add("'Diagnosis' label in Site Search page");
            }

            // Biomarkers
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[2]/span/label"));
            highlight(element);
            if (element.getText().equals("Biomarkers"))
                sop("Label 'Biomarkers' matched in Site Search page");
            else {
                sop("Label 'Biomarkers' mismatch in Site Search page!");
                errorLabels.add("'Biomarkers' label in Site Search page");
            }

            // Treatments
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[3]/span/label"));
            highlight(element);
            if (element.getText().equals("Treatments"))
                sop("Label 'Treatments' matched in Site Search page");
            else {
                sop("Label 'Treatments' mismatch in Site Search page!");
                errorLabels.add("'Treatments' label in Site Search page");
            }
            
            // Advanced filters
            // Patient Criteria
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/p[1]"));
            highlight(element);
            if (element.getText().trim().equals("Patient Criteria"))
                sop("Label 'Patient Criteria' matched in Site Search page");
            else {
                sop("Label 'Patient Criteria' mismatch in Site Search page!");
                errorLabels.add("'Patient Criteria' label in Site Search page");
            }

            // Last Active (months)
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            highlight(element);
            if (element.getText().equals("Last Active (months):"))
                sop("Label 'Last Active (months):' matched in Site Search page");
            else {
                sop("Label 'Last Active (months):' mismatch in Site Search page!");
                errorLabels
                    .add("'Last Active (months):' label in Site Search page");
            }

            // Stage at Diagnosis
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[1]/span/label"));
            highlight(element);
            if (element.getText().equals("Stage at Diagnosis"))
                sop("Label 'Stage at Diagnosis' matched in Site Search page");
            else {
                sop("Label 'Stage at Diagnosis' mismatch in Site Search page!");
                errorLabels.add("'Stage at Diagnosis' label in Site Search page");
            }

            // Line of Therapy
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[2]/span/label"));
            highlight(element);
            if (element.getText().equals("Line of Therapy"))
                sop("Label 'Line of Therapy' matched in Site Search page");
            else {
                sop("Label 'Line of Therapy' mismatch in Site Search page!");
                errorLabels.add("'Line of Therapy' label in Site Search page");
            }
            
            // Returning to original state
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/header/button"));
            highlight(element);
            element.click();
                       	

            // Verifying Advanced Filters
            verifyAdvancedFilters();

            /**
             * Options Filters 
             * ***************
             */

            // Biomarker filter
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/div[1]/div/article[1]"));
            highlight(element);
            if (element.getText().contains("1 Biomarker"))
                sop("Filter Label '1 Biomarker' matched in Site Search page");
            else {
                sop("Filter Label '1 Biomarker' mismatch in Site Search page!");
                errorLabels.add("'1 Biomarker' filter label in Site Search page");
            }

            // Breast Cancer Filter
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/div[1]/div/article[2]"));
            highlight(element);
            if (element.getText().contains("101 Breast Cancer"))
                sop("Filter Label '101 Breast Cancer' matched in Site Search page");
            else {
                sop("Filter Label '101 Breast Cancer' mismatch in Site Search page!");
                errorLabels.add(
                    "'101 Breast Cancer' filter label in Site Search page");
            }

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/div[1]/div/article[3]"));
            highlight(element);
            if (element.getText().contains("1 Treatment Drug Inclusion"))
                sop("Filter Label '1 Treatment Drug Inclusion' matched in Site Search page");
            else {
                sop("Filter Label '1 Treatment Drug Inclusion' mismatch in Site Search page!");
                errorLabels.add(
                    "'1 Treatment Drug Inclusion' filter label in Site Search page");
            }

            /**
             * Clear & Show buttons 
             * ********************
             */

            // Clear All button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/div[1]/button[2]/span"));
            highlight(element);
            if (element.getText().equals("Clear All"))
                sop("Button Label 'Clear All' matched in Site Search page");
            else {
                sop("Button Label 'Clear All' mismatch in Site Search page!");
                errorLabels.add("'Clear All' button label in Site Search page");
            }
            element.click();

            Thread.sleep(2000);

            // Cancel button on pop-up
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/article[4]/div[2]/div[3]/button[1]"));
            highlight(element);
            element.click();

            // Show All button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/div[1]/button[3]/span"));
            highlight(element);
            if (element.getText().equals("Show All"))
                sop("Button Label 'Show All' matched in Site Search page");
            else {
                sop("Button Label 'Show All' mismatch in Site Search page!");
                errorLabels.add("'Show All' button label in Site Search page");
            }
            element.click();

            ArrayList < String > diagnosesFilters = getDiagnosesFilters();

            Boolean flag = false;
            WebElement scrollSection = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[3]/div[2]"));

            // Breast Cancer filter
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[3]/div[2]/section[1]/div[2]/article[1]"));
            highlight(element);
            if (element.getText().equals("Breast Cancer")) {
                sop("'Breast Cancer filter matched");
            } else {
                sop("Breast Cancer filter mismatch in filters in Site Search Page");
                errorLabels.add(
                    "Breast Cancer filter in filters pop-up in Site Search Page");
            }

            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 5000)", scrollSection);

            // // Diagnoses filters
            // for (int i = 2; i <= 102; i++) {
            // if (i % 15 == 0) {
            // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0,
            // 650)", scrollSection);
            // }
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article/section/article/article[3]/div[2]/section[1]/div[2]/article["
            // + String.valueOf(i) + "]"));
            // highlight(element, 400);
            // String filter = element.getText();
            // if (!diagnosesFilters.contains(filter)) {
            // flag = true;
            // sop(filter + "not found in original list");
            // errorLabels.add(filter + "not found in original list of Diagnoses
            // filters");
            // }
            // }
            // if (!flag) {
            // sop("All diagnoses filters matched");
            // }

            // Biomarker filters
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[3]/div[2]/section[2]/article"));

            highlight(element);

            if (element.getText().equals("HER-2/neu receptor assay - Positive")) {
                sop("'HER-2/neu receptor assay - Positive' filter matched");
            } else {
                sop("'HER-2/neu receptor assay - Positive' filter mismatch in filters in Site Search Page");
                errorLabels.add(
                    "'HER-2/neu receptor assay - Positive' filter in filters pop-up in Site Search Page");
            }

            // Treatment Drug Inclusion filters
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[3]/div[2]/section[3]/article"));

            highlight(element);

            if (element.getText().equals("Herceptin - trastuzumab")) {
                sop("'Herceptin - trastuzumab' filter matched");
            } else {
                sop("'Herceptin - trastuzumab' filter mismatch in filters in Site Search Page");
                errorLabels.add(
                    "'Herceptin - trastuzumab' filter in filters pop-up in Site Search Page. Value found: " + element.getText());
            }

            // Closing 'Show All' pop-up
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[3]/div[1]/button"));
            highlight(element);
            element.click();

            // Re-applying advanced filters
            reapplyAdvancedFilters();
            reVerifyAdvancedFilters();

            /**
             * Grid buttons 
             * ************
             */
            WebElement listViewElement = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[1]/article[1]/button[1]"));

            // Grid button is selected
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[1]/article[1]/button[1]"));

            highlight(element);

            if (listViewElement.isSelected()) {
                sop("Grid button is selected by default");
            } else {
                sop("Grid button is not selected by default");
                errorLabels.add(
                    "Grid button is not selected in Site Search Results page.");
            }

            /**
             * Table View Check 
             * ****************
             */

            // Table view button is clicked
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[1]/article[1]/button[2]"));
            highlight(element);
            element.click();
            Thread.sleep(2000);

            WebElement tableViewElement = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/div[2]"));

            if (tableViewElement.isDisplayed()) {
                sop("Tabular view showing when table button is clicked");
            } else {
                sop("Tabular view not showing when table button is clicked");
                errorLabels.add(
                    "Tabular view not showing when button clicked in Site Search Results page.");
            }

            // Verify 'Add to Shortlist' button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]"));
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]"))
                .size() >= 0) {
                highlight(driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]")));
                sop("'Add to Shortlist' button showing in tabular view.");
            } else {
                sop("'Add to Shortlist' button not showing in tabular view.");
                errorLabels.add(
                    "'Add to Shortlist' button not showing in tabular view");
            }
            // Verify 'Remove from Shortlist' button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]"));
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]"))
                .size() >= 0) {
                highlight(driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]")));
                sop("'Remove from Shortlist' button showing in tabular view.");
            } else {
                sop("'Remove from Shortlist' button not showing in tabular view.");
                errorLabels.add(
                    "'Remove from Shortlist' button not showing in tabular view");
            }
            
            // Check Table Headers
            for(int i=1; i<=10; i++) { 
            	element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[2]/div/div[1]/div/table/thead/tr/th[" + String.valueOf(i+1) + "]/span[1]/span/span/article/span"));
            	highlight(element);
            	switch(i) {
            		case 1:
            			if(element.getText().equals("Site Number")) {
            				sop("Search Summary Table header 'Site Number' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Site Number' not matching.");
            				errorLabels.add("Search Summary Table header 'Site Number' not matched");
            			}
            			break;
            		case 2:
            			if(element.getText().equals("Location")) {
            				sop("Search Summary Table header 'Location' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Location' not matching.");
            				errorLabels.add("Search Summary Table header 'Location' not matched");
            			}
            			break;
            		case 3:
            			if(element.getText().equals("Est. Patient Match")) {
            				sop("Search Summary Table header 'Est. Patient Match' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Est. Patient Match' not matching.");
            				errorLabels.add("Search Summary Table header 'Est. Patient Match' not matched");
            			}
            			break;
            		case 4:
            			if(element.getText().equals("Gender Distribution")) {
            				sop("Search Summary Table header 'Gender Distribution' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Gender Distribution' not matching.");
            				errorLabels.add("Search Summary Table header 'Gender Distribution' not matched");
            			}
            			break;
            		case 5:
            			if(element.getText().equals("TRE")) {
            				sop("Search Summary Table header 'TRE' matched");
            			}
            			else {
            				sop("Search Summary Table header 'TRE' not matching.");
            				errorLabels.add("Search Summary Table header 'TRE' not matched");
            			}
            			break;
            		case 6:
            			if(element.getText().equals("Patient / Physician")) {
            				sop("Search Summary Table header 'Patient / Physician' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Patient / Physician' not matching.");
            				errorLabels.add("Search Summary Table header 'Patient / Physician' not matched");
            			}
            			break;
            		case 7:
            			if(element.getText().equals("Physician Count")) {
            				sop("Search Summary Table header 'Physician Count' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Physician Count' not matching.");
            				errorLabels.add("Search Summary Table header 'Physician Count' not matched");
            			}
            			break;
            		case 8:
            			if(element.getText().equals("Satellite Sites")) {
            				sop("Search Summary Table header 'Satellite Sites' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Satellite Sites' not matching.");
            				errorLabels.add("Search Summary Table header 'Satellite Sites' not matched");
            			}
            			break;
            		case 9:
            			if(element.getText().equals("Last Updated")) {
            				sop("Search Summary Table header 'Last Updated' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Last Updated' not matching.");
            				errorLabels.add("Search Summary Table header 'Last Updated' not matched");
            			}
            			break;
            		case 10:
            			if(element.getText().equals("Applied Filters")) {
            				sop("Search Summary Table header 'Applied Filters' matched");
            			}
            			else {
            				sop("Search Summary Table header 'Applied Filters' not matching.");
            				errorLabels.add("Search Summary Table header 'Applied Filters' not matched");
            			}
            			break;
            	}
            	
            }
            
            // Check sorting - Ascending 
            // Clicking on Est Patient Match 
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[2]/div/div[1]/div/table/thead/tr/th[4]/span[1]/span/span[1]/article/span"));
            highlight(element);
            element.click();
            
            int val1 = 0;
            int val2 = 0;
            boolean isErr = false;
            
            for(int i=1; i<=5; i++) {
            	element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[2]/div/div[2]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[4]"));
            	highlight(element);
            	
            	if(i==1) {
            		val1 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            		continue;
            	}
            	val2 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            	
            	if(val1 > val2) {
            		isErr = true;
            		sop("Ascending order not working on 'Est Patient Match' in table view");
            		errorLabels.add("Ascending order on 'Est Patient Match' in table view");
            		break;
            	}
            	
            	val1 = val2;
            }
            
            if(!isErr)
            	sop("Ascending order working on 'Est Patient Match' in table view");
            
            // Check sorting - Descending 
            // Clicking on Est Patient Match
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[2]/div/div[1]/div/table/thead/tr/th[4]/span[1]/span/span[1]/article/span"));
            highlight(element);
            element.click();
            
            val1 = 0;
            val2 = 0;
            isErr = false;
            
            for(int i=1; i<=5; i++) {
            	element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[2]/div/div[2]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[4]"));
            	highlight(element);
            	
            	if(i==1) {
            		val1 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            		continue;
            	}
            	val2 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            	
            	if(val1 < val2) {
            		isErr = true;
            		sop("Descending order not working on 'Est Patient Match' in table view");
            		errorLabels.add("Descending order on 'Est Patient Match' in table view");
            		break;
            	}
            	
            	val1 = val2;
            }
            
            if(!isErr)
            	sop("Descending order working on 'Est Patient Match' in table view");
            
            // Clicking on Est Patient Match to return to original order
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[2]/div/div[1]/div/table/thead/tr/th[4]/span[1]/span/span[1]/article/span"));
            element.click();

            /**
             * Map View Check 
             * **************
             */

            // Map view button is clicked
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[1]/article[1]/button[3]"));
            highlight(element);
            element.click();

            // Verify 'Add to Shortlist' button            
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]"))
                .size() == 0) {
//                highlight(driver.findElement(By.xpath(
//                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]")));
                sop("'Add to Shortlist' button not showing in map view.");
            } else {
                sop("'Add to Shortlist' button showing in map view.");
                errorLabels.add("'Add to Shortlist' button showing in map view");
            }
            // Verify 'Remove from Shortlist' button            
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]")).size() == 0) {
//                highlight(driver.findElement(By.xpath(
//                    "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]")));
                sop("'Remove from Shortlist' button not showing in map view.");
            } else {
                sop("'Remove from Shortlist' button showing in map view.");
                errorLabels
                    .add("'Remove from Shortlist' button showing in map view");
            }
            
            // TOTAL STATES REPRESENTED label
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[1]/article[1]/div/span[2]"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Total States Represented"))
            	sop("'Total States Represented' label matched.");
            else {
            	sop("'Total States Represented' label not matching in Map View");
            	errorLabels.add("'Total States Represented' label mismatch in Map View");
            }
            
            // STATE WITH MOST SITES label
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[1]/article[2]/div/span[2]"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("State with Most Sites"))
            	sop("'State with Most Sites' label matched.");
            else {
            	sop("'State with Most Sites' label not matching in Map View");
            	errorLabels.add("'State with Most Sites' label mismatch in Map View");
            }
            	
    		// STATE WITH MOST PATIENTS label
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[1]/article[3]/div/span[2]"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("State with Most Patients"))
            	sop("'State with Most Patients' label matched.");
            else {
            	sop("'State with Most Patients' label not matching in Map View");
            	errorLabels.add("'State with Most Patients' label mismatch in Map View");
            }	
            
            // STATE WITH MOST SITES value
            highlight(driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[1]/article[2]/div/span[1]")));
//            String stateOnHeader = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[1]/article[2]/div/span[1]")).getText();
            
//            highlight(driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[2]/div[2]/div/div/svg/g[8]/text[1]")));
//            String stateOnMap = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[2]/div[2]/div/div/svg/g[8]/text[1]")).getText();
//            
//            if(stateOnHeader.equalsIgnoreCase(stateOnMap))
//            	sop("State Value matching on header and map.");
//            else {
//            	sop("State Value not matching on header and map.");
//            	errorLabels.add("State Value mismatch between value on header and map");
//            }
            
            // DISTRIBUTION BY box
            // Hamburger button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[1]/div[1]/div/ul/li/span/button"));
            highlight(element);            
            action.moveToElement(element).click().perform();  
            // Number of Patients
            element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[3]/span/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Number of Patients"))
            	sop("'Number of Patients' label matching in Hamburger options for 'Distribution of Sites'");
            else {
            	sop("'Number of Patients' label not matching in Hamburger options for 'Distribution of Sites'");
            	errorLabels.add("'Number of Patients' label in Hamburger options for 'Distribution of Sites'");
            }
            element.click();
            Thread.sleep(1000);
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[1]/div[1]/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Distribution of Patients per state"))
            	sop("'Distribution of Patients per state' label updated from hamburger option 'Number of Patients'");
            else {
            	sop("'Distribution of Patients per state' label not updated from hamburger option 'Number of Patients'");
            	errorLabels.add("'Distribution of Patients per state' label from hamburger option 'Number of Patients'");
            }
            
            // Number of Sites
            element = driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[1]/div[1]/div/ul/li/span/button"));
            highlight(element);            
            action.moveToElement(element).click().perform();  
            // Clicking on Number of Sites
            element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[2]/span/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Number of Sites"))
            	sop("'Number of Sites' label matching in Hamburger options for 'Distribution of'");
            else {
            	sop("'Number of Sites' label not matching in Hamburger options for 'Distribution of'");
            	errorLabels.add("'Number of Sites' label in Hamburger options for 'Distribution of'");
            }
            element.click();
            Thread.sleep(1000);
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[1]/div[1]/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Distribution of Sites per state"))
            	sop("'Distribution of Sites per state' label updated from hamburger option 'Number of Patients'");
            else {
            	sop("'Distribution of Sites per state' label not updated from hamburger option 'Number of Patients'");
            	errorLabels.add("'Distribution of Sites per state' label from hamburger option 'Number of Patients'");
            }
            
            // RANKING BY box
            // Hamburger button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[2]/div[1]/div/ul/li/span/button"));
            highlight(element);            
            action.moveToElement(element).click().perform();  
            // Number of Patients
            element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[3]/span/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Number of Patients"))
            	sop("'Number of Patients' label matching in Hamburger options for 'Ranking By'");
            else {
            	sop("'Number of Patients' label not matching in Hamburger options for 'Ranking By'");
            	errorLabels.add("'Number of Patients' label in Hamburger options for 'Ranking By'");
            }
            element.click();
            Thread.sleep(1000);
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[2]/div[1]/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Ranking of states by number of Patients"))
            	sop("'Ranking of states by number of Patients' label updated from hamburger option 'Number of Patients'");
            else {
            	sop("'Ranking of states by number of Patients' label not updated from hamburger option 'Number of Patients'");
            	errorLabels.add("'Ranking of states by number of Patients' label from hamburger option 'Number of Patients'");
            }
            
            // Number of Sites
            element = driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[2]/div[1]/div/ul/li/span/button"));
            highlight(element);            
            action.moveToElement(element).click().perform();  
            // Clicking on Number of Sites
            element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[2]/span/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Number of Sites"))
            	sop("'Number of Sites' label matching in Hamburger options for 'Ranking By'");
            else {
            	sop("'Number of Sites' label not matching in Hamburger options for 'Ranking By'");
            	errorLabels.add("'Number of Sites' label in Hamburger options for 'Ranking By'");
            }
            element.click();
            Thread.sleep(1000);
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[3]/section[2]/article[2]/div[1]/span"));
            highlight(element);
            if(element.getText().equalsIgnoreCase("Ranking of states by number of Sites"))
            	sop("'Ranking of states by number of Sites' label updated from hamburger option 'Number of Patients'");
            else {
            	sop("'Ranking of states by number of Sites' label not updated from hamburger option 'Number of Patients'");
            	errorLabels.add("'Ranking of states by number of Sites' label from hamburger option 'Number of Patients'");
            }
            

            // Switching back to Grid view
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[1]/article[1]/button[1]"));
            highlight(element);
            element.click();

            /**
             * Shortlisted buttons 
             * *******************
             */

            // All button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[1]/article[2]/button[1]"));

            highlight(element);

            if (element.isEnabled())
            	sop("Button 'All' is enabled in Site Search page!");
                
            else {
            	sop("Button 'All' is disabled in Site Search page");
                errorLabels.add("'All' button label disabled in Site Search page");
            }

            // Check if element is pressed/selected
            if (element.getAttribute("class").contains("pressed")) {
                sop("'All' button is pressed.");
            } else {
                sop("'All' button is not pressed");
                errorLabels.add(
                    "'All' button is not pressed in Site Search results page");
            }

            // Shortlisted button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[1]/article[2]/button[2]"));

            highlight(element);

            if (!element.isEnabled())
                sop("Button 'Shortlisted' is disabled in Site Search page");
            else {
                sop("Button 'Shortlisted' is enabled in Site Search page!");
                errorLabels.add(
                    "'Shortlisted' button label enabled in Site Search page");
            }
            // Check if element is pressed/selected
            if (!element.getAttribute("class").contains("pressed")) {
                sop("'Shortlisted' button is not pressed.");
            } else {
                sop("'Shortlisted' button is pressed");
                errorLabels.add(
                    "'Shortlisted' button is pressed in Site Search results page");
            }

            // Not-Shortlisted button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[1]/article[2]/button[3]"));

            highlight(element);

            if (!element.isEnabled())
                sop("Button 'Not Shortlisted' is disabled in Site Search page");
            else {
                sop("Button 'Not Shortlisted' is enabled in Site Search page!");
                errorLabels.add(
                    "'Not Shortlisted' button label enabled in Site Search page");
            }

            // Check if element is pressed/selected
            if (!element.getAttribute("class").contains("pressed")) {
                sop("'Not Shortlisted' button is not pressed.");
            } else {
                sop("'Not Shortlisted' button is pressed");
                errorLabels.add(
                    "'Not Shortlisted' button is pressed in Site Search results page");
            }

            return searchName;
        }

        /**
         * Verify Search Name in Dashboard 
         * *******************************
         */

        private static void verifySearchInDashboard(String searchName)
        throws InterruptedException {
            WebElement element;
            WebElement scrollSection;
            Actions action = new Actions(driver);

            // Redirect to Dashboard
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/article/div[2]/ul/li[2]/button"));
            highlight(element);
            element.click();

            Thread.sleep(3000);

            if (driver.getCurrentUrl().equals(
                    "https://testinsightsportal.intrinsiq.com/SiteAssist/Dashboard")) {
                sop("Dashboard button working as expected");
            } else {
                sop("Dashboard button not working as expected");
                errorLabels.add(
                    "Dashboard button not getting back to Dashboard page.");
            }

            scrollSection = driver
                .findElement(By.xpath("/html/body/div/div/article/section"));
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 800)", scrollSection);

            // Highlight table name - Search Summary
            highlight(driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/header/div/span")));

            // Verify search box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/div/div/div[1]/div[2]/span/input"));
            highlight(element);
            element.click();
            element.sendKeys(searchName);
            Thread.sleep(500);
            // Verifying result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr/td[2]"));
            highlight(element);
            if (element.getText().trim().equalsIgnoreCase(searchName)) {
                sop("Search Summary search box working as expected.");
            } else {
                sop("Search Summary search box not working as expected.");
                errorLabels
                    .add("Search Summary search box not working as expected.");
            }

            // Clearing search box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/div/div/div[1]/div[2]/span/input"));
            highlight(element);
            element.clear();

            // Last Page button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[2]/div/div/div[5]/a[4]"));
            highlight(element);

            if (!element.getAttribute("class").contains("disabled")) {
                sop("Going to last page...");
                Thread.sleep(2000);
                element.click();
            } else {
                sop("Found shortlist in first page");
            }

            int i = 1;
            while (!driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                    String.valueOf(i) + "]/td[2]"))
                .getText().contains(searchName)) {
                i++;
                if (i > 5) {
                    sop("Search name not found in current page");
                    errorLabels.add("Search Name not found in search results");
                    return;
                }
            }

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                String.valueOf(i) + "]/td[2]"));

            highlight(element);
            sop("Search record found");

            // Redirecting to Search result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                String.valueOf(i) + "]/td[1]/a"));
            highlight(element);
            element.click();
            Thread.sleep(3000);

        }

        private static void checkDetailedProfileData(String searchName)
        throws InterruptedException {
            WebElement element;
            Actions action = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String jsStyle = "'3px solid red'";
            String jsStyleRemove = "'0px solid red'";
            
            WebElement loadingMask = driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").contains("none")) {
                    sop("Search results loading...");
                    checkErrorPage();
                    Thread.sleep(2000);
                }

            // Click on first result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[2]/div/div[1]/article/section/button"));
            js.executeScript("arguments[0].style.border=" + jsStyle, element);
            Thread.sleep(2000);
            js.executeScript("arguments[0].style.border=" + jsStyleRemove, element);
            element.click();
            // /html/body/div/div/article/section/article/main/section/article[3]/article[1]/span/svg
            // *[@id=\"app\"]/div/section/article/main/section/article[2]/article[1]
            loadingMask = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[4]/article[1]"));
            while (!loadingMask.getCssValue("display").contains("none")) {
                sop("Detailed Profile loading...");
                checkErrorPage();
                Thread.sleep(3000);
            }

            Thread.sleep(1500);
            // // Search Summary
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article/section/article/main/header/span"));
            // js.executeScript("arguments[0].style.border=" + jsStyle, element);
            // Thread.sleep(1500);
            // if (element.getText().equals("Search Summary"))
            // sop("Heading 'Search Summary' matched in Detailed Profile page");
            // else {
            // sop("Heading 'Search Summary' mismatch in Detailed Profile page!");
            // errorLabels.add("'Search Summary' heading in Detailed Profile page");
            // }
            // js.executeScript("arguments[0].style.border=" + jsStyleRemove,
            // element);
            //
            // // Search Name
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article/section/article/main/header/section[1]/div[2]/span"));
            // js.executeScript("arguments[0].style.border=" + jsStyle, element);
            // Thread.sleep(1500);
            // if (element.getText().equals("SEARCH NAME"))
            // sop("Subheading 'Search Name' matched in Detailed Profile page");
            // else {
            // sop("Subheading 'Search Name' mismatch in Detailed Profile page!");
            // errorLabels.add("'Search Name' label in Detailed Profile page");
            // }
            // js.executeScript("arguments[0].style.border=" + jsStyleRemove,
            // element);
            //
            // // Search Name Value
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article/section/article/main/header/section[1]/div[2]/div/span"));
            // js.executeScript("arguments[0].style.border=" + jsStyle, element);
            // Thread.sleep(1500);
            // if (element.getText().equals(searchName))
            // sop("Search name matched in Detailed Profile page");
            // else {
            // sop("Search name mismatch in Detailed Profile page! Does not match
            // with initial value.");
            // errorLabels.add("'Search Name' value in Detailed Profile page");
            // }
            // js.executeScript("arguments[0].style.border=" + jsStyleRemove,
            // element);
            //
            // // Matching Sites
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article/section/article/main/header/section[2]/div[2]/span[2]"));
            // js.executeScript("arguments[0].style.border=" + jsStyle, element);
            // Thread.sleep(1500);
            // if (element.getText().equals("MATCHING SITES"))
            // sop("Subheading 'Matching Sites' matched in Detailed Profile page");
            // else {
            // sop("Subheading 'Matching Sites' mismatch in Detailed Profile
            // page!");
            // errorLabels.add("'Matching Sites' label in Detailed Profile page");
            // }
            // js.executeScript("arguments[0].style.border=" + jsStyleRemove,
            // element);
            //
            // // Matching Patients
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article/section/article/main/header/section[3]/div[2]/span[2]"));
            // js.executeScript("arguments[0].style.border=" + jsStyle, element);
            // Thread.sleep(1500);
            // if (element.getText().equals("MATCHING PATIENTS"))
            // sop("Subheading 'Matching Patients' matched in Detailed Profile
            // page");
            // else {
            // sop("Subheading 'Matching Patients' mismatch in Detailed Profile
            // page!");
            // errorLabels.add("'Matching Patients' label in Detailed Profile
            // page");
            // }
            // js.executeScript("arguments[0].style.border=" + jsStyleRemove,
            // element);

            // Detailed Profile
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[4]/div[1]/span"));
            js.executeScript("arguments[0].style.border=" + jsStyle, element);
            Thread.sleep(1500);
            if (element.getText().contains("Detailed Profile for Site"))
                sop("Heading for 'Detailed Profile' matched");
            else {
                sop("Heading for 'Detailed Profile' mismatch!");
                errorLabels.add("'Detailed Profile' heading");
            }
            js.executeScript("arguments[0].style.border=" + jsStyleRemove, element);

            // Site Summary
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[4]/article[2]/header/div/span"));
            js.executeScript("arguments[0].style.border=" + jsStyle, element);
            Thread.sleep(1500);
            if (element.getText().equals("Site Summary"))
                sop("Heading 'Site Summary' matched");
            else {
                sop("Heading 'Site Summary' mismatch!");
                errorLabels.add("'Site Summary' heading");
            }
            js.executeScript("arguments[0].style.border=" + jsStyleRemove, element);

            // Site Summary sub headings
            for (int i = 1; i <= 6; i++) {
                switch (i) {
                    case 1:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[2]/div/div/article[" +
                            i + "]/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Site Location"))
                            sop("Subheading 'Site Location' matched");
                        else {
                            sop("Subheading 'Site Location' mismatch!");
                            errorLabels.add("'Site Location' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 2:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[2]/div/div/article[" +
                            i + "]/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Estimated Patient Match"))
                            sop("Subheading 'Estimated Patient Match' matched");
                        else {
                            sop("Subheading 'Estimated Patient Match' mismatch!");
                            errorLabels.add("'Estimated Patient Match' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 3:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[2]/div/div/article[" +
                            i + "]/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText()
                            .equals("Patient per assigned Physician"))
                            sop("Subheading 'Patient per assigned Physician' matched");
                        else {
                            sop("Subheading 'Patient per assigned Physician' mismatch!");
                            errorLabels.add(
                                "'Patient per assigned Physician' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 4:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[2]/div/div/article[" +
                            i + "]/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Satellite Sites"))
                            sop("Subheading 'Satellite Sites' matched");
                        else {
                            sop("Subheading 'Satellite Sites' mismatch!");
                            errorLabels.add("'Satellite Sites' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 5:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[2]/div/div/article[" +
                            i + "]/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Last Updated"))
                            sop("Subheading 'Last " + "Updated' matched");
                        else {
                            sop("Subheading 'Last Updated' mismatch!");
                            errorLabels.add("'Last Updated' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 6:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[2]/div/div/article[" +
                            i + "]/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Applied Filters"))
                            sop("Subheading 'Applied Filters' matched");
                        else {
                            sop("Subheading 'Applied Filters' mismatch!");
                            errorLabels.add("'Applied Filters' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                }
            }

            // Patient Population
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[4]/article[3]/header/div[1]/span"));
            // ((JavascriptExecutor) driver)
            // .executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].style.border=" + jsStyle, element);
            Thread.sleep(1500);
            if (element.getText().equals("Patient Population"))
                sop("Heading 'Patient population' matched");
            else {
                sop("Heading 'Patient population' mismatch!");
                errorLabels.add("'Patient population' heading");
            }
            js.executeScript("arguments[0].style.border=" + jsStyleRemove, element);

            WebElement scrollSection = driver
                .findElement(By.className("site-view-container"));

            // Patient Population sub headings
            for (int i = 1; i <= 5; i++) {
                switch (i) {
                    case 1:
                        // Gender Distribution
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[1]/article[1]/span"));
                        ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollBy(0, 300)", scrollSection);
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Gender Distribution"))
                            sop("Subheading 'Gender Distribution' matched");
                        else {
                            sop("Subheading 'Gender Distribution' mismatch!");
                            errorLabels.add("'Gender Distribution' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 2:
                        // Race
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[1]/article[2]/article/div[1]/span"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText()
                            .equals("Patient count based on Race"))
                            sop("Subheading 'Patient count based on Race' matched");
                        else {
                            sop("Subheading 'Patient count based on Race' mismatch!");
                            errorLabels.add(
                                "'Patient count based on Race' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Hamburger button
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[1]/article[2]/article/div[1]/div/ul/li/span/button"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        action.moveToElement(element).click().perform();
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Default
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[1]/span/span",
                            "Default",
                            "Patient count based on Race | Ethnicity");
                        // Sort Ascending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[2]/span/span",
                            "Sort Ascending",
                            "Patient count based on Race | Ethnicity");
                        // Sort Descending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[3]/span/span",
                            "Sort Descending",
                            "Patient count based on Race | Ethnicity");

                        action.moveToElement(element).click().perform();

                        break;                                             
                        
                    case 3:
                        // Ethnicity
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[1]/article[2]/article[2]/div[1]/span"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText()
                            .equals("Patient count based on Ethnicity"))
                            sop("Subheading 'Patient count based on Ethnicity' matched");
                        else {
                            sop("Subheading 'Patient count based on Ethnicity' mismatch!");
                            errorLabels.add(
                                "'Patient count based on Ethnicity' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Hamburger button
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[1]/article[2]/article[2]/div[1]/div/ul/li/span/button"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        action.moveToElement(element).click().perform();
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Default
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[1]/span/span",
                            "Default",
                            "Patient count based on Race | Ethnicity");
                        // Sort Ascending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[2]/span/span",
                            "Sort Ascending",
                            "Patient count based on Race | Ethnicity");
                        // Sort Descending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[3]/span/span",
                            "Sort Descending",
                            "Patient count based on Race | Ethnicity");

                        action.moveToElement(element).click().perform();

                        break; 
                    case 4:
                        // Line of Therapy
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[1]/article[3]/div[1]/span"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText()
                            .equals("Patient count based on Line of Therapy"))
                            sop("Subheading 'Patient count based on Line of Therapy' matched");
                        else {
                            sop("Subheading 'Patient count based on Line of Therapy' mismatch!");
                            errorLabels.add(
                                "'Patient count based on Line of Therapy' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Hamburger button
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[1]/article[3]/div[1]/div/ul/li/span/button"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        action.moveToElement(element).click().perform();
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Default
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[1]/span/span",
                            "Default",
                            "Patient count based on Line of Therapy");
                        // Sort Ascending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[2]/span/span",
                            "Sort Ascending",
                            "Patient count based on Line of Therapy");
                        // Sort Descending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[3]/span/span",
                            "Sort Descending",
                            "Patient count based on Line of Therapy");

                        action.moveToElement(element).click().perform();

                        break;
                    case 5:
                        // Patient count based on Stage at Diagnosis
                        ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollBy(0, 350)", scrollSection);

                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[2]/article[1]/div[1]/span"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals(
                                "Patient count based on Stage at Diagnosis"))
                            sop("Subheading 'Patient count based on Stage at Diagnosis' matched");
                        else {
                            sop("Subheading 'Patient count based on Stage at Diagnosis' mismatch!");
                            errorLabels.add(
                                "'Patient count based on Stage at Diagnosis' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Hamburger button
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/article[3]/div/div/div[1]/div[2]/article[1]/div[1]/div/ul/li/span/button"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        action.moveToElement(element).click().perform();
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);

                        // Default
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[1]/span/span",
                            "Default",
                            "Patient count based on Stage at Diagnosis");
                        // Sort Ascending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[2]/span/span",
                            "Sort Ascending",
                            "Patient count based on Stage at Diagnosis");
                        // Sort Descending
                        verifyHamburgerOptions(
                            "/html/body/div[2]/div/ul/li[3]/span/span",
                            "Sort Descending",
                            "Patient count based on Stage at Diagnosis");

                        action.moveToElement(element).click().perform();

                        break;                 
                }
            }

            /**
             * Research Experience and Facility Overview
             * *****************************************
             */
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 350)", scrollSection);

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/header/div[1]/span"));
            highlight(element);
            if (element.getText().equals("Research Experience and Facility Overview"))
                sop("Heading 'Research Experience and Facility Overview' matched");
            else {
                sop("Heading 'Research Experience and Facility Overview' mismatch!");
                errorLabels.add("'Research Experience and Facility Overview' heading");
            }  
            
            // Patient count based on Therapeutic Research Experience
            element = driver.findElement(By.xpath( 
                "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[1]/div[1]/span"));
            highlight(element);
            if (element.getText().equals(
                    "Patient count based on Therapeutic Research Experience"))
                sop("Subheading 'Patient count based on Therapeutic Research Experience' matched");
            else {
                sop("Subheading 'Patient count based on Therapeutic Research Experience' mismatch!");
                errorLabels.add(
                    "'Patient count based on Therapeutic Research Experience' subheading");
            }            

            // Hamburger button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[1]/div[1]/div/ul/li/span/button"));
            js.executeScript("arguments[0].style.border=" + jsStyle,
                element);
            Thread.sleep(1500);
            action.moveToElement(element).click().perform();
            js.executeScript(
                "arguments[0].style.border=" + jsStyleRemove,
                element);

            // Default
            verifyHamburgerOptions(
                "/html/body/div[2]/div/ul/li[1]/span/span",
                "Default",
                "Patient count based on Stage at Diagnosis");
            // Sort Ascending
            verifyHamburgerOptions(
                "/html/body/div[2]/div/ul/li[2]/span/span",
                "Sort Ascending",
                "Patient count based on Stage at Diagnosis");
            // Sort Descending
            verifyHamburgerOptions(
                "/html/body/div[2	]/div/ul/li[3]/span/span",
                "Sort Descending",
                "Patient count based on Stage at Diagnosis");

            action.moveToElement(element).click().perform();
            
            // FACILITY OVERVIEW label
            element = driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[2]/span"));
                highlight(element);
                if (element.getText().equals("Facility Overview"))
                    sop("Subheading 'Facility Overview' matched");
                else {
                    sop("Subheading 'Facility Overview' mismatch!");
                    errorLabels.add("'Facility Overview' subheading");
                }  

            // Facility Overview sub headings
            for (int i = 1; i <= 6; i++) {
                switch (i) {
                	case 1:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[2]/div/span[1]/article/div/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText()
                            .equals("Started trial participation in"))
                            sop("Subheading 'Started trial participation in' matched");
                        else {
                            sop("Subheading 'Started trial participation in' mismatch!");
                            errorLabels.add(
                                "'Started trial participation in' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 2:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[2]/div/span[2]/article/div/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText()
                            .equals("Total Physician(s) on Site"))
                            sop("Subheading 'Total Physician(s) on Site' matched");
                        else {
                            sop("Subheading 'Total Physician(s) on Site' mismatch!");
                            errorLabels.add(
                                "'Total Physician(s) on Site' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 3:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[2]/div/span[3]/article/div/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Has Refrigeration"))
                            sop("Subheading 'Has Refrigeration' matched");
                        else {
                            sop("Subheading 'Has Refrigeration' mismatch!");
                            errorLabels.add("'Has Refrigeration' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 4:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[2]/div/span[4]/article/div/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Has Freezer"))
                            sop("Subheading 'Has Freezer' matched");
                        else {
                            sop("Subheading 'Has Freezer' mismatch!");
                            errorLabels.add("'Has Freezer' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 5:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[2]/div/span[5]/article/div/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Imaging Services"))
                            sop("Subheading 'Imaging Services' matched");
                        else {
                            sop("Subheading 'Imaging Services' mismatch!");
                            errorLabels.add("'Imaging Services' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                    case 6:
                        element = driver.findElement(By.xpath(
                            "/html/body/div/div/article[1]/section/article/main/section/article[4]/div[2]/article/div/div/div/div/article[2]/div/span[6]/article/div/span[1]"));
                        js.executeScript("arguments[0].style.border=" + jsStyle,
                            element);
                        Thread.sleep(1500);
                        if (element.getText().equals("Laboratory Services"))
                            sop("Subheading 'Laboratory Services' matched");
                        else {
                            sop("Subheading 'Laboratory Services' mismatch!");
                            errorLabels.add("'Laboratory Services' subheading");
                        }
                        js.executeScript(
                            "arguments[0].style.border=" + jsStyleRemove,
                            element);
                        break;
                }
            }
// Commented due to change in UI
//            
//            // Research Experience
//            element = driver.findElement(By.xpath(
//                "/html/body/div/div/article/section/article/main/section/article[4]/div[2]/article[2]/header/div/span"));
//            js.executeScript("arguments[0].style.border=" + jsStyle, element);
//            Thread.sleep(1500);
//            if (element.getText().equals("Research Experience"))
//                sop("Heading 'Research Experience' matched");
//            else {
//                sop("Heading 'Research Experience' mismatch!");
//                errorLabels.add("'Research Experience' heading");
//            }
//            js.executeScript("arguments[0].style.border=" + jsStyleRemove, element);
//
//            // Research Experience sub headings
//            for (int i = 1; i <= 2; i++) {
//                switch (i) {
//                    case 1:
//                        element = driver.findElement(By.xpath(
//                            "/html/body/div/div/article/section/article/main/section/article[4]/div[2]/article[2]/div/div/div[1]/span"));
//                        js.executeScript("arguments[0].style.border=" + jsStyle,
//                            element);
//                        Thread.sleep(1500);
//                        if (element.getText()
//                            .contains("Started trial participation"))
//                            sop("Contents 'Started trial participation' matched");
//                        else {
//                            sop("Contents 'Started trial participation' mismatch!");
//                            errorLabels.add(
//                                "'Started trial participation' subheading");
//                        }
//                        js.executeScript(
//                            "arguments[0].style.border=" + jsStyleRemove,
//                            element);
//                        break;
//                    case 2:
//                        element = driver.findElement(By.xpath(
//                            "/html/body/div/div/article/section/article/main/section/article[4]/div[2]/article[2]/div/div/div[2]/span"));
//                        js.executeScript("arguments[0].style.border=" + jsStyle,
//                            element);
//                        Thread.sleep(1500);
//                        if (element.getText().contains("Trials conducted in last"))
//                            sop("Contents 'Trials conducted in last x years' matched");
//                        else {
//                            sop("Contents 'Trials conducted in last x years' mismatch!");
//                            errorLabels.add(
//                                "'Trails conducted in last x years' subheading");
//                        }
//                        js.executeScript(
//                            "arguments[0].style.border=" + jsStyleRemove,
//                            element);
//                        break;
//                }
//            }
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollBy(0, -1000)", scrollSection); // Scrolling up
            Thread.sleep(1000);
            sop("Returning to search results page");
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[4]/div[1]/button"));
            js.executeScript("arguments[0].style.border=" + jsStyle, element);
            Thread.sleep(2000);
            js.executeScript("arguments[0].style.border=" + jsStyleRemove, element);
            element.click();

            // return errorLabels;
        }

        private static String addToShortList() throws InterruptedException {
            WebElement element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[2]/button[1]"));

            highlight(element);
            if (!element.isEnabled()) {
                sop("'Add to Shortlist' button is disabled");
            } else {
                sop("'Add to Shortlist' button is enabled without any selection");
                errorLabels.add(
                    "'Add to Shortlist' button is enabled without any selection");
            }

            // Selecting first result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[2]/div/div[1]/span/input"));
            highlight(element);
            element.click();
            // Selecting second result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[2]/div/div[2]/span/input"));
            highlight(element);
            element.click();

            // Scrolling down to bring third result into view
            WebElement scrollSection = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]"));
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 1000)", scrollSection);

            // Selecting third result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[2]/div/div[3]/span/input"));
            highlight(element);
            element.click();

            // Selecting fourth result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[2]/div/div[4]/span/input"));
            highlight(element);
            element.click();

            // Capturing the third site name for verification during removal later
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/section/button/span"));
            highlight(element);
            String siteName = element.getText();

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[2]/button[1]"));
            highlight(element);
            if (element.isEnabled()) {
                sop("'Add to Shortlist' button is enabled on selection");
            } else {
                sop("'Add to Shortlist' button is disabled with selection");
                errorLabels.add(
                    "'Add to Shortlist' button is disabled with selection");
            }

            element.click(); // Add to ShortList button
            Thread.sleep(2500);
            driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/article[1]/div[2]/div[2]/article/span"))
                .click(); // Enable Shortlist text box

            String shortlistName = generateSearchName(7);
            Thread.sleep(1000);
            driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/article[1]/div[2]/div[2]/article/span/input"))
                .sendKeys(shortlistName); // Enter generated shortlist name
            Thread.sleep(1500);

            driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/article[1]/div[2]/div[1]/span"))
                .click(); // Click on title to remove focus and dropdown of
            // suggestions
            Thread.sleep(1500);

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/article[1]/div[2]/div[3]/button[2]/span"));
            highlight(element);
            element.click(); // Click on Add New button
            Thread.sleep(1500);

            checkErrorPage();

            WebElement loadingMask = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]"));
            while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                sop("Adding to Shortlist...");
                Thread.sleep(2000);
            }

            Thread.sleep(1500);

            // Verify number of items added to shortlist message
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[1]/article[3]/span[1]"));
            highlight(element);
            if (element.getText().equals("4 sites added to shortlist")) {
                sop("4 elements added to shortlist");
            } else {
                sop("Elements added to shortlist message incorrect");
                errorLabels.add("Elements added to shortlist message incorrect");
            }

            /**
             * Verify Shortlist badges 
             * ***********************
             */

            // Add to Shortlist badge for 1st site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[1]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 1st record.");
                errorLabels.add(
                    "'Added to Shortlist' badge against 1st record not showing");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[1]/article/section/article"));
                highlight(element);

                sop("Site 1 added to shortlist successfully!");
                sop("'Added to shortlist' badge showing against 1st record.");
            }

            // Add to Shortlist badge for 2nd site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 2nd record.");
                errorLabels.add(
                    "'Added to Shortlist' badge against 2nd record not showing");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[2]/article/section/article"));
                highlight(element);

                sop("Site 2 added to shortlist successfully!");
                sop("'Added to shortlist' badge showing against 2nd record.");
            }

            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 500)", scrollSection);

            // Add to Shortlist badge for 3rd site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 3rd record.");
                errorLabels.add(
                    "'Added to Shortlist' badge against record against 3rd record not showing");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[3]/article/section/article"));
                highlight(element);

                sop("Site 3 added to shortlist successfully!");
                sop("'Added to shortlist' badge showing against 3rd record.");
            }

            // Add to Shortlist badge for 4th site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[4]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 3rd record.");
                errorLabels.add(
                    "'Added to Shortlist' badge against record against 4th record not showing");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[4]/article/section/article"));
                highlight(element);

                sop("Site 4 added to shortlist successfully!");
                sop("'Added to shortlist' badge showing against 4th record.");
            }

            /**
             * Going into EDIT SHORTLIST mode
             * ******************************
             */
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[1]/div/button"));
            highlight(element);
            element.click();
            
            loadingMask = driver.findElement(By.xpath( 
                    "/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading results...");
                    Thread.sleep(2000);
                }

            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/div/div[1]/div/button[1]"));
            highlight(element);
            element.click();
            
            loadingMask = driver.findElement(By.xpath( 
                    "/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading results...");
                    Thread.sleep(2000);
                }

            // Checking ADD TO SHORTLIST & REMOVE FROM SHORTLIST button
            checkAddnRemovebtn();

            // Checking sorting order on sites
            checkSitesOrder();

            /**
             * Remove Multiple Sites from Shortlist
             * ************************************
             */
            // Selecting Shortlist 1 
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/span/input"));
            highlight(element);
            element.click();
            // Getting shortlist name
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/article/section/button/span"));
            highlight(element);
            String n1 = element.getText();

            // Selecting Shortlist 2
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/span/input"));
            highlight(element);
            element.click();
            // Getting shortlist name 
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/section/button/span"));
            highlight(element);
            String n2 = element.getText();

            // Selecting Shortlist 3
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/span/input"));
            highlight(element);
            element.click();
            // Getting shortlist name
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/section/button/span"));
            highlight(element);
            String n3 = element.getText();

            // Clicking on remove button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]"));
            highlight(element);
            element.click();

            // Verifying site names in remove dialog box
            try {
                for (int i = 1; i <= 3; i++) {
                    element = driver.findElement(By.xpath(
                        "/html/body/div/div/article[1]/section/article/main/article[3]/div[2]/div[2]/div[2]/span[" + i + "]"));
                    highlight(element);

                    if (element.getText().equals(n1)) {
                        n1 = "";
                        sop(element.getText() +
                            " found in Remove Shortlist pop-up.");
                        continue;
                    } else if (element.getText().equals(n2)) {
                        sop(element.getText() +
                            " found in Remove Shortlist pop-up.");
                        n2 = "";
                        continue;
                    } else if (element.getText().equals(n3)) {
                        n3 = "";
                        continue;
                    } else {
                        sop(element.getText() +
                            " not found in Remove Shortlist pop-up.");
                        errorLabels.add(element.getText() +
                            " not found in Remove Shortlist pop-up.");
                    }
                }
            } catch (NoSuchElementException e) {
                sop("All elements selected for removal not loading in Remove Shortlist pop-up");
                errorLabels.add(
                    "All elements selected for removal not loading in Remove Shortlist pop-up");
            }

            // Close removal dialog box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/article[3]/div[2]/div[3]/button[1]"));
            highlight(element);
            element.click();

            // Unselecting all
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/span/input"));
            element.click();
            element.click();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 600)", driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div")));


            /**
             * Remove one site from shortlist
             */
            // Check Remove from Shortlist button disabled
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]"));
            if (!element.isEnabled()) {
                sop("'Remove from Shortlist' disabled without any site selection.");
            } else {
                sop("'Remove from Shortlist' enabled without any site selection.");
                errorLabels.add(
                    "'Remove from Shortlist' disabled without any site selection.");
            }

            // Remove one site from shortlist
            sop("Removing one site from shortlist...");
            // Select 4th element checkbox
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[4]/span/input"));
            highlight(element);
            element.click();

            // Click on Remove from Shortlist button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]"));
            highlight(element);
            element.click();

            // Verify Site name to be removed
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/article[3]/div[2]/div[2]/div[2]/span"));
            highlight(element);
            if (element.getText().equals(siteName)) {
                sop("Site Name matched with initial selection.");
            } else {
                sop("Site name not matching with initial selection.");
                errorLabels.add(
                    "Site name not matching with initial selection in 'Remove site from Shortlist' pop-up");
            }

            // Remove button in pop-up
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/article[3]/div[2]/div[3]/button[2]"));
            highlight(element);
            element.click();

            loadingMask = driver.findElement(By.xpath( 
                    "/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading results...");
                    Thread.sleep(2000);
                }

            // Verify number of items added to shortlist message after removal
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[1]/div[1]/article[3]/span[1]"));
            highlight(element);
            if (element.getText().equals("3 sites added to shortlist")) {
                sop("Site count reduced to 3 after removal of one site from shortlist.");
            } else {
                sop("Site count not matching after removal of one site from shortlist.");
                errorLabels.add(
                    "Site count not matching after removal of one site from shortlist");
            }

            /**
             * Verify Shortlist badges 
             * ***********************
             */

            // Add to Shortlist badge for 1st site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[1]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 1st record.");
                errorLabels.add(
                    "'Added to Shortlist' badge against 1st record not showing");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[1]/article/section/article"));
                highlight(element);
                sop("'Added to shortlist' badge showing against 1st record.");
            }

            // Add to Shortlist badge for 2nd site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 2nd record.");
                errorLabels.add(
                    "'Added to Shortlist' badge against 2nd record not showing");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[2]/article/section/article"));
                highlight(element);
                sop("'Added to shortlist' badge showing against 2nd record.");
            }

            // scrollSection =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]"));
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 300)", driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]")));

            // Add to Shortlist badge for 3rd site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 3rd record.");
                errorLabels.add(
                    "'Added to Shortlist' badge against 3rd record not showing");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[3]/article/section/article"));
                highlight(element);
                sop("'Added to shortlist' badge showing against 3rd record.");
            }

            // Add to Shortlist badge for 4th site
            if (driver.findElements(By.xpath(
                    "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[4]/article/section/article"))
                .size() == 0) {
                sop("'Added to Shortlist' badge not showing for 4th record after removal from shortlist.");
            } else {
                element = driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/main/section/article[2]/div/div[4]/article/section/article"));
                highlight(element);
                errorLabels.add(
                    "'Added to Shortlist' badge against 4th record showing after removal from shortlist");
                sop("'Added to shortlist' badge showing against 4th record after removal from shortlist.");
            }

            // View Shortlist
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/main/section/article[1]/div[1]/div/button"));
            highlight(element);
            element.click();

            loadingMask = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]"));
            while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                sop("Loading results...");
                Thread.sleep(2000);
            }
            return shortlistName;
        }

        /**
         * Apply Advanced filters to Shortlist
         * ***********************************
         */

        private static void addAdvancedFiltersToShortlist() throws InterruptedException {
            // Click on EDIT SHORTLIST button
            WebElement element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/div/div[1]/div/button[1]"));
            highlight(element);
            element.click();
            
            WebElement loadingMask = driver.findElement(By.xpath( 
                    "/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading results...");
                    Thread.sleep(2000);
                }

            // Expanding Advanced Filters
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/header/button"));
            highlight(element);
            element.click();

            // Adding Line of Therapy 
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[2]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath("/html/body/overlay-container/article[32]/div/article/ul/span[2]/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // Scrolling down
            WebElement scrollSection = driver.findElement(By.xpath("/html/body/div/div/article[1]/section"));
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 1000)", scrollSection);

            // Gender
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/section/div[2]/input"));
            highlight(element);
            element.click();

            // Apply filters  
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/footer/button[3]"));
            highlight(element);
            element.click();

            loadingMask = driver.findElement(
                By.xpath("/html/body/div/div/article[1]/section/article/article[1]"));
            while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                sop("Loading filters...");
                Thread.sleep(2000);
                checkErrorPage();
            }

            // Checking top bar filters
            for (int i = 1; i <= 6; i++) {
                element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[1]/div/article[" + i + "]"));
                highlight(element);
                switch (i) {
                    case 1:
                        if (!element.getText().equals("1 Biomarker")) {
                            sop("'1 Biomarker' filter label not found in Site Shortlist page top bar");
                            errorLabels.add("'1 Biomarker' filter label not found in Site Shortlist page top bar.");
                        } else
                            sop("'1 Biomarker' filter label found in Site Shortlist page top bar");
                        break;
                    case 2:
                        if (!element.getText().equals("101 Breast Cancer")) {
                            sop("'101 Breast Cancer' filter label not found in Site Shortlist page top bar");
                            errorLabels.add("'101 Breast Cancer' filter label not found in Site Shortlist page top bar.");
                        } else
                            sop("'101 Breast Cancer' filter label found in Site Shortlist page top bar");
                        break;
                    case 3:
                        if (!element.getText().equals("1 Treatment Drug Inclusion")) {
                            sop("'1 Treatment Drug Inclusion' filter label not found in Site Shortlist page top bar");
                            errorLabels.add("'1 Treatment Drug Inclusion' filter label not found in Site Shortlist page top bar.");
                        } else
                            sop("'1 Treatment Drug Inclusion' filter label found in Site Shortlist page top bar");
                        break;
                    case 4:
                        if (!element.getText().equals("Gender Male")) {
                            sop("'Gender Male' filter label not found in Site Shortlist page top bar");
                            errorLabels.add("'Gender Male' filter label not found in Site Shortlist page top bar.");
                        } else
                            sop("'Gender Male' filter label found in Site Shortlist page top bar");
                        break;
                    case 5:
                        if (!element.getText().equals("1 Line of Therapy")) {
                            sop("'1 Line of Therapy' filter label not found in Site Shortlist page top bar");
                            errorLabels.add("'1 Line of Therapy' filter label not found in Site Shortlist page top bar.");
                        } else
                            sop("'1 Line of Therapy' filter label found in Site Shortlist page top bar");
                        break;
                    case 6:
                        if (!element.getText().equals("11 Stages at Diagnosis")) {
                            sop("'11 Stages at Diagnosis' filter label not found in Site Shortlist page top bar");
                            errorLabels.add("'11 Stages at Diagnosis' filter label not found in Site Shortlist page top bar.");
                        } else
                            sop("'11 Stages at Diagnosis' filter label found in Site Shortlist page top bar");
                        break;
                }
            }

            // Checking applied filter on previous shortlist site 
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/article/div/span[7]/article"));
            highlight(element);
            if (element.getText().equals("1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender All, 11 Stages at Diagnosis"))
                sop("Existing filters intact on previously shortlisted sites");
            else {
                sop("Existing filters not matching on previously shortlisted sites");
                errorLabels.add("Existing filters not matching on previously shortlisted sites");
            }

            // Checking new filters on shortlist site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/div/span[7]/article"));
            highlight(element);
            if (element.getText().equals("1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender Male, 1 Line of Therapy, 11 Stages at Diagnosis"))
                sop("New filters matching on updated sites");
            else {
                sop("New filters not matching on updated sites");
                errorLabels.add("New filters not matching on updated sites in Shortlist Page");
            }

            // Adding new item to shortlist
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/span/input"));
            highlight(element);
            element.click();

            // Clicking on Shortlist button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]"));
            highlight(element);
            element.click();

            loadingMask = driver.findElement(
                    By.xpath("/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading filters...");
                    Thread.sleep(2000);
                    checkErrorPage();
                }

            // Check for ADDED TO SHORTLIST badge 
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/section/article/span"));
            highlight(element);
            if (element.getText().equals("Added to Shortlist"))
                sop("'Added to Shortlist' badge found on new site");
            else {
                sop("'Added to Shortlist' badge not found on new site");
                errorLabels.add("'Added to Shortlist' badge not found on new site");
            }

            // View Shortlist 
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[1]/div/button"));
            highlight(element);
            element.click();

            Thread.sleep(3000);

            // Verify filters in each shortlist 
            for (int i = 1; i <= 4; i++) { 
                element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/div/article[1]/div/div[" + i + "]/article/div/span[7]/article"));
                highlight(element);
                switch (i) {
                    case 1:
                        if (element.getText().equals("1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender All, 11 Stages at Diagnosis"))
                            sop("Filter matched for 1st site in Shortlist");
                        else {
                            sop("Filter not matching for 1st site in Shortlist");
                            errorLabels.add("Filter not matching for 1st site in Shortlist");
                        }
                        break;
                    case 2:
                        if (element.getText().equals("1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender All, 11 Stages at Diagnosis"))
                            sop("Filter matched for 2nd site in Shortlist");
                        else {
                            sop("Filter not matching for 2nd site in Shortlist");
                            errorLabels.add("Filter not matching for 2nd site in Shortlist");
                        }
                        break;
                    case 3:
                        if (element.getText().equals("1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender All, 11 Stages at Diagnosis"))
                            sop("Filter matched for 2nd site in Shortlist");
                        else {
                            sop("Filter not matching for 2nd site in Shortlist");
                            errorLabels.add("Filter not matching for 2nd site in Shortlist");
                        }
                        break;
                    case 4:
                        if (element.getText().equals("1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender Male, 1 Line of Therapy, 11 Stages at Diagnosis"))
                            sop("Filter matched for 2nd site in Shortlist");
                        else {
                            sop("Filter not matching for 2nd site in Shortlist");
                            errorLabels.add("Filter not matching for 2nd site in Shortlist");
                        }
                        break;
                }
            }
        }

        /**
         * Site Shortlist Page 
         * *******************
         */
        private static String checkSiteShortlistPage(String searchName,
            String shortlistName) throws InterruptedException {
            WebElement element;
            WebElement loadingMask;

            // Verify Shortlist Page URL
            if (driver.getCurrentUrl().contains(
                    "https://testinsightsportal.intrinsiq.com/SiteAssist/SiteShortlist")) {
                sop("Site Shortlist Page URL is correct");
            } else {
                sop("Site Shortlist Page URL is not correct");
                errorLabels.add("Site Shortlist Page URL incorrect.");
            }

            // Updating Shortlist name
            shortlistName = shortlistName + "_test";

            // Edit button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/section[1]/div[2]/div/button"));
            highlight(element);
            element.click();
            Thread.sleep(1000);
            // Rename textbox
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/article/div[2]/div[2]/span/span/input"));
            Thread.sleep(1000);
            highlight(element);
            element.click();
            element.clear();
            element.sendKeys(shortlistName);

            // Update button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/article/div[2]/div[3]/button[2]"));
            highlight(element);
            element.click();

            // Site Shortlist header
            element = driver.findElement(
                By.xpath("/html/body/div/div/article/article/div[1]/div/div"));
            highlight(element);
            if (element.getText().equals("Site Shortlist")) {
                sop("'Site Shortlist' header matched.");
            } else {
                sop("'Site Shortlist' header mismatch.");
                errorLabels.add("Site Shortlist header in Site Shortlist page");
            }

            // Shortlist Summary
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/span"));
            highlight(element);
            if (element.getText().equals("Shortlist Summary")) {
                sop("'Shortlist Summary' label matched.");
            } else {
                sop("'Shortlist Summary' label mismatch.");
                errorLabels.add("Shortlist Summary label in Site Shortlist page");
            }

            // Shortlist Name label
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/section[1]/div[2]/span"));
            highlight(element);
            if (element.getText().equalsIgnoreCase("Shortlist Name")) {
                sop("'Shortlist Name' label matched.");
            } else {
                sop("'Shortlist Name' label mismatch.");
                errorLabels.add("Shortlist Name label in Site Shortlist page");
            }

            // Shortlist Name value
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/section[1]/div[2]/div/span"));
            highlight(element);
            if (element.getText().equals(shortlistName)) {
                sop("'Shortlist Name' value matched.");
            } else {
                sop("'Shortlist Name' value mismatch.");
                errorLabels.add("Shortlist Name value in Site Shortlist page");
            }

            // Shortlisted Sites
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/section[2]/div[2]/span[2]"));
            highlight(element);
            if (element.getText().equalsIgnoreCase("Shortlisted Sites")) {
                sop("'Shortlisted Sites' label matched.");
            } else {
                sop("'Shortlisted Sites' label mismatch.");
                errorLabels.add("Shortlisted Sites label in Site Shortlist page");
            }

            // Shortlisted Sites count
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/header/section[2]/div[2]/span[1]"));
            highlight(element);
            if (element.getText().equalsIgnoreCase("3")) {
                sop("'Shortlisted Sites' count matched.");
            } else {
                sop("'Shortlisted Sites' count mismatch.");
                errorLabels.add("Shortlisted Sites count in Site Shortlist page");
            }

            // Matching Patients
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/section[3]/div[2]/span[2]"));
            highlight(element);
            if (element.getText().equalsIgnoreCase("Matching Patients")) {
                sop("'Matching Patients' label matched.");
            } else {
                sop("'Matching Patients' label mismatch.");
                errorLabels.add("Matching Patients label in Site Shortlist page");
            }

            // States Represented
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/section[4]/div[2]/span[2]"));
            highlight(element);
            if (element.getText().equalsIgnoreCase("States Represented")) {
                sop("'States Represented' label matched.");
            } else {
                sop("'States Represented' label mismatch.");
                errorLabels.add("States Represented label in Site Shortlist page");
            }

            // Shortlist Status
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/header/section[5]/div[2]/span[2]"));
            highlight(element);
            if (element.getText().equalsIgnoreCase("Shortlist Status")) {
                sop("'Shortlist Status' label matched.");
            } else {
                sop("'Shortlist Status' label mismatch.");
                errorLabels.add("Shortlist Status label in Site Shortlist page");
            }

            // Redirecting to Dashboard
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/article/div[2]/ul/li[2]/button"));
            highlight(element);
            element.click();

            Thread.sleep(3000);

            if (driver.getCurrentUrl().equals(
                    "https://testinsightsportal.intrinsiq.com/SiteAssist/Dashboard")) {
                sop("Dashboard button working as expected");
            } else {
                sop("Dashboard button not working as expected");
                errorLabels.add(
                    "Dashboard button not getting back to Dashboard page.");
            }

            /**
             * Verify Search Record 
             * ********************
             */

            WebElement scrollSection = driver
                .findElement(By.xpath("/html/body/div/div/article/section"));
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 800)", scrollSection);

            // Highlight table name - Search Summary
            highlight(driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/header/div/span")));

            // Last Page button on Search Table
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[2]/div/div/div[5]/a[4]"));
            highlight(element);

            if (!element.getAttribute("class").contains("disabled")) {
                sop("Going to last page...");
                Thread.sleep(2000);
                element.click();
            } else {
                sop("Found search record in first page");
            }

            int i = 1;
            while (!driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                    String.valueOf(i) + "]/td[2]"))
                .getText().contains(searchName)) {
                i++;
                if (i > 5) {
                    sop("Search name not found in current page");
                    errorLabels.add("Search Name not found in search results");
                    return null;
                }
            }

            String rowIndex = "";
            if (i != 1) {
                rowIndex = "[" + String.valueOf(i) + "]";
            }

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                String.valueOf(i) + "]/td[2]"));

            highlight(element);
            sop("Search record found");

            // Scroll Up
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0,-800)", scrollSection);

            /**
             * Verify Active Count 
             * *******************
             */

            // Active count
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/header/article[2]/button[1]/span"));
            highlight(element);
            int updatedActiveCount = Integer
                .parseInt(element.getText().split(" ")[0]);
            sop("Updated active count : " + String.valueOf(updatedActiveCount));

            if (updatedActiveCount == (activeCount + 1)) {
                sop("Active count matches.");
            } else {
                sop("Active count not updated properly");
                errorLabels.add("Active Count updating incorrectly");
            }

            // Clicking on Active Count button
            element.click();

            // Highlight table name - Shortlist Summary
            highlight(driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/header/div/span")));

            // Last Page button on Shortlist Table
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[5]/a[4]"));
            highlight(element);

            if (!element.getAttribute("class").contains("disabled")) {
                sop("Going to last page...");
                Thread.sleep(2000);
                element.click();
            } else {
                sop("Found shortlist record in first page");
            }

            i = 1;
            while (!driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                    String.valueOf(i) + "]/td[2]"))
                .getText().contains(shortlistName)) {
                i++;
                if (i > 5) {
                    sop("Shortlist name not found in current page");
                    errorLabels.add(
                        "Shortlist Name not found in Shortlist results table");
                    return null;
                }
            }

            rowIndex = "";
            if (i != 1) {
                rowIndex = "[" + String.valueOf(i) + "]";
            }

            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                String.valueOf(i) + "]/td[2]"));

            highlight(element);
            sop("Shortlist record found");

            // Verify Active status against record
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                String.valueOf(i) + "]/td[7]/div/article/span"));
            highlight(element);
            if (element.getText().equals("Active")) {
                sop("Shortlist status matched.");
            } else {
                sop("Shortlist Status not matching as 'Active'");
                errorLabels.add(
                    "Shortlist Status not matching as 'Active' in Shortlist Table");
            }

            // Clicking on result
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                String.valueOf(i) + "]/td[1]/a"));
            highlight(element);
            element.click();

            Thread.sleep(3000);

            // Submit Shortlist
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/div/div[1]/div/button[2]"));
            highlight(element);
            element.click();

            loadingMask = driver.findElement(
                By.xpath("/html/body/div/div/article/section/article/article"));
            while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                sop("Loading Submit Shortlist page...");
                Thread.sleep(2000);
                checkErrorPage();
            }

            String protocolName = generateSearchName(7);
            // Adding protocol name
            driver.findElement(By.xpath(
                    "/html/body/div/div/article/section/article/section/section/div/div[1]/span/span/input"))
                .sendKeys(protocolName);
            Thread.sleep(2000);

            // Submit for Review
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/section/section/div/div[2]/button[2]"));
            highlight(element);
            element.click();

            Thread.sleep(5000);

            // Report Submission message
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/section/p[1]"));
            highlight(element);

            if (element.getText()
                .equals("You have successfully submitted your shortlist")) {
                sop("Report submitted successfully");
            }
            
            // Verify Page Navigation & Labels
            // SUBMISSION COMPLETED label
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/article/div[1]/div/div"));
            highlight(element);
            if(element.getText().equals("Submission Completed"))
            	sop("'Submission Completed' label matched");
            else {
            	sop("'Submission Completed' not matching");
            	errorLabels.add("'Submission Completed' label not matched in Shortlist Submission page");
            }
            
            // HOME button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/article/div[2]/ul/li[1]/button/span"));
            highlight(element);
            if(element.getText().equals("Home")){
            	sop("'Home' navigation button found in Submission Complete page");
            }
            else {
            	sop("'Home' navigation button not found in Submission Complete page");
            	errorLabels.add("'Home' navigation button mismatch in Submission Complete page");
            }
            
            // DASHBOARD button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/article/div[2]/ul/li[2]/button/span"));
            highlight(element);
            if(element.getText().equals("Dashboard")){
            	sop("'Dashboard' navigation button found in Submission Complete page");
            }
            else {
            	sop("'Dashboard' navigation button not found in Submission Complete page");
            	errorLabels.add("'Dashboard' navigation button mismatch in Submission Complete page");
            }
            
            // SUBMISSION COMPLETED label
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/article/div[2]/ul/li[3]/span"));
            highlight(element);
            if(element.getText().equals("Submission Completed")){
            	sop("'Submission Completed' navigation label found in Submission Complete page");
            }
            else {
            	sop("'Submission Completed' navigation label not found in Submission Complete page");
            	errorLabels.add("'Home' navigation label mismatch in Submission Complete page");
            }
            
            // COMMUNICATION button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/header/section[6]/button"));
            highlight(element);
            element.click();
            
            Thread.sleep(3000);
            
            if (driver.getCurrentUrl().contains("https://testinsightsportal.intrinsiq.com/SiteAssist/CommunicationHub/"))
            	sop("'View Communication' button navigating to communication page");
            else {
            	sop("'View Communication' button not navigating to communication page as expected");
            	errorLabels.add("View Communication' button navigation to communication page as expected");
            }
            
            // Going back to previous page
            driver.navigate().back();
            Thread.sleep(2500);

            // Back to Dashboard button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/section/button"));

            highlight(element);

            element.click();

            sop("In Dashboard");

            Thread.sleep(5000);

            // Highlight table name - Shortlist Summary
            highlight(driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/header/div/span")));

            // Verify Submitted count
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]/header/article[2]/button[2]/span"));
            highlight(element);
            element.click();

            int updatedShortlistCount = Integer
                .parseInt(element.getText().split(" ")[0]);
            sop("Updated submit shortlist count: " +
                String.valueOf(updatedShortlistCount));

            if (updatedShortlistCount == (shortlistCount + 1)) {
                sop("Submitted shortlist count matching.");
            } else {
                sop("Submitted shortlist count not matching");
                errorLabels.add(
                    "Submitted shortlist count not matching after new shortlist submission in Dashboard");
            }

            // Last Page button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]/div/div/div[5]/a[4]"));
            highlight(element);

            if (!element.getAttribute("class").contains("disabled")) {
                sop("Going to last page...");
                Thread.sleep(2000);
                driver.findElement(By.xpath(
                        "/html/body/div/div/article/section/article/article[1]/div/div/div[5]/a[4]"))
                    .click();
                // driver.findElement(By.className("k-link.k-pager-nav.k-pager-last")).click();
            } else {
                sop("Found shortlist in first page");
            }

            i = 1;
            while (!driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" +
                    String.valueOf(i) + "]/td[2]"))
                .getText().contains(shortlistName)) {
                i++;
            }

            rowIndex = "";
            if (i != 1) {
                rowIndex = "[" + String.valueOf(i) + "]";
            }

            WebElement shortlistRefNum = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr" +
                rowIndex + "/td[1]/a"));
            WebElement shortlistNameVal = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr" +
                rowIndex + "/td[2]"));
            WebElement protocolNameVal = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr" +
                rowIndex + "/td[3]/div"));
            WebElement shortlistStatusVal = driver.findElement(By.xpath(
                "/html/body/div/div/article/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr" +
                rowIndex + "/td[7]/div/article/span"));

            element = shortlistNameVal;
            highlight(element);

            element = protocolNameVal;
            highlight(element);

            element = shortlistStatusVal;
            highlight(element);

            if (shortlistStatusVal.getText().equals("Submitted") &&
                shortlistNameVal.getText().equals(shortlistName) &&
                protocolNameVal.getText().equals(protocolName)) {
                sop("Shortlist submitted successfully");
            } else {
                sop("Error in shortlist submission!");
                errorLabels.add("Short list submission error");
            }

            return shortlistRefNum.getText();
        }

        private static void verifyTableStatus(String header) throws InterruptedException {
        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            WebElement element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[5]/div[2]"));
            highlight(element);
            int count = Integer.parseInt(element.getText().substring(4, 5));

            // Can skip if no records in table found
            if (count == 0) {
                sop("No records found in Shortlist table to verify " + header + " records");
                return;
            }
            // Loop for rows in shortlist table
            for (int i = 1; i <= count; i++) {
                element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[7]/div/article/span"));
                highlight(element);
                boolean isComm = true;
                try {
                    element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[1]/svg"));
                    highlight(element);
                } catch (NoSuchElementException e) {
                    isComm = false;
                }
                if (element.getText().trim().equals(header.trim())) {
                	if(isComm || i == count) {
                		int optionCount;
	                    switch (header) {
	                        case "Active":
	                            WebElement option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();
	                            
	                            optionCount = isComm ? 5 : 4;
	                            
	                            // Loop for options
	                            for (int j = 1; j <= optionCount; j++) {	                                
	                                element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
	                                    case 2:
	                                        if (element.getText().equals("Edit")) {
	                                            sop("Option 'Edit' matched for Active options");
	                                        } else {
	                                            sop("Option 'Edit' not matching for Active options");
	                                            errorLabels.add("Option 'Edit' not matched for Active options");
	                                        }
	                                        break;
	                                    case 3:
	                                        if (element.getText().equals("Delete")) {
	                                            sop("Option 'Delete' matched for Active options");
	                                        } else {
	                                            sop("Option 'Delete' not matching for Active options");
	                                            errorLabels.add("Option 'Delete' not matched for Active options");
	                                        }
	                                        break;
	                                    case 4:
	                                        if (element.getText().equals("Duplicate")) {
	                                            sop("Option 'Duplicate' matched for Active options");
	                                        } else {
	                                            sop("Option 'Duplicate' not matching for Active options");
	                                            errorLabels.add("Option 'Duplicate' not matched for Active options");
	                                        }
	                                        break;
	                                    case 5:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	
	                            }
	                            break;
	                        case "Submitted":
	                            option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();
	                            option.click();
	                            
	                            optionCount = isComm ? 4 : 3;
	                            
	                            for (int j = 1; j <= optionCount; j++) {	    
	                            	if(j > 2) 
	                            		element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span"));	                            	
	                            	else 
	                            		element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
	                                    case 2:
	                                        if (element.getText().equals("Duplicate")) {
	                                            sop("Option 'Duplicate' matched for Active options");
	                                        } else {
	                                            sop("Option 'Duplicate' not matching for Active options");
	                                            errorLabels.add("Option 'Duplicate' not matched for Active options");
	                                        }
	                                        break;
	                                    case 3:
	                                        if (element.getText().equals("Withdraw")) {
	                                            sop("Option 'Withdraw' matched for Active options");
	                                        } else {
	                                            sop("Option 'Withdraw' not matching for Active options");
	                                            errorLabels.add("Option 'Withdraw' not matched for Active options");
	                                        }
	                                        break;
	                                    case 4:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	                            }
	                            break;
	                        case "AIQ Review":
	                            option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();
	                            option.click();
	                            
	                            optionCount = isComm ? 3 : 2;
	                            
	                            for (int j = 1; j <= optionCount; j++) {	                            
	                                element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
	                                    case 2:
	                                        if (element.getText().equals("Duplicate")) {
	                                            sop("Option 'Duplicate' matched for Active options");
	                                        } else {
	                                            sop("Option 'Duplicate' not matching for Active options");
	                                            errorLabels.add("Option 'Duplicate' not matched for Active options");
	                                        }
	                                        break;
	                                    case 3:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	                            }
	                            break;
	                        case "Sponsor Review":
	                            option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();
	                            option.click();
	                            
	                            optionCount = isComm ? 6 : 5;
	                            
	                            for (int j = 1; j <= optionCount; j++) {	
	                            	if(j<4)	
	                            		element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                            	else 
	                            		element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
	                                    case 2:
	                                        if (element.getText().equals("Edit")) {
	                                            sop("Option 'Edit' matched for Active options");
	                                        } else {
	                                            sop("Option 'Edit' not matching for Active options");
	                                            errorLabels.add("Option 'Edit' not matched for Active options");
	                                        }
	                                        break;
	                                    case 3:
	                                        if (element.getText().equals("Duplicate")) {
	                                            sop("Option 'Duplicate' matched for Active options");
	                                        } else {
	                                            sop("Option 'Duplicate' not matching for Active options");
	                                            errorLabels.add("Option 'Duplicate' not matched for Active options");
	                                        }
	                                        break;
	                                    case 4:
	                                        if (element.getText().equals("Approve")) {
	                                            sop("Option 'Approve' matched for Active options");
	                                        } else {
	                                            sop("Option 'Approve' not matching for Active options");
	                                            errorLabels.add("Option 'Approve' not matched for Active options");
	                                        }
	                                        break;
	                                    case 5:
	                                        if (element.getText().equals("Withdraw")) {
	                                            sop("Option 'Withdraw' matched for Active options");
	                                        } else {
	                                            sop("Option 'Withdraw' not matching for Active options");
	                                            errorLabels.add("Option 'Withdraw' not matched for Active options");
	                                        }
	                                        break;
	                                    case 6:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	                            }
	                            break;
	                        case "Approved":
	                            option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();
	                            option.click();
	                            
	                            optionCount = isComm ? 2 : 1;
	                            
	                            for (int j = 1; j <= optionCount; j++) {	                                
	                                element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
	                                    case 2:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	                            }
	                            break;
	                        case "Withdrawn":
	                            option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();	                            
	                            
	                            optionCount = isComm ? 3 : 2;
	                            
	                            for (int j = 1; j <= optionCount; j++) {
	                            	if(j == 2)
	                            		continue;
	                                element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
                                        // Disabled option - Cannot access
	                                    // case 2:
	                                    //    if (element.getText().equals("Request to Reopen")) {
	                                    //        sop("Option 'Request to Reopen' matched for Active options");
	                                    //    } else {
	                                    //        sop("Option 'Request to Reopen' not matching for Active options");
	                                    //        errorLabels.add("Option 'Request to Reopen' not matched for Active options");
	                                    //    }
	                                    //    break;
	                                    case 3:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	                            }
	                            break;
	                        case "Site Recruitment":
	                            option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();
	                            //option.click();
	                            
	                            optionCount = isComm ? 2 : 1;
	                            
	                            
	                            for (int j = 1; j <= optionCount; j++) {	                             
	                                element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
	                                    case 2:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	                            }
	                            break;
	                        case "Completed":
	                            option = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr[" + String.valueOf(i) + "]/td[8]/div/ul/li/span/button"));
	                            highlight(option);
	                            option.click();
	                            //option.click();
	                            
	                            optionCount = isComm ? 2 : 1;
	                            	                            
	                            for (int j = 1; j <= optionCount; j++) {	                                
	                                element = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[" + String.valueOf(j) + "]/span/span[2]"));
	                                highlight(element);
	                                switch (j) {
	                                    case 1:
	                                        if (element.getText().equals("View")) {
	                                            sop("Option 'View' matched for Active options");
	                                        } else {
	                                            sop("Option 'View' not matching for Active options");
	                                            errorLabels.add("Option 'View' not matched for Active options");
	                                        }
	                                        break;
	                                    case 2:
	                                        if (element.getText().equals("Communication")) {
	                                            sop("Option 'Communication' matched for Active options");
	                                        } else {
	                                            sop("Option 'Communication' not matching for Active options");
	                                            errorLabels.add("Option 'Communication' not matched for Active options");
	                                        }	                                        
	                                        break;
	                                }
	                            }
	                            break;
	                    }
                    }
                } else {
                    sop("Status not matching with selected item: " + header + " in Shortlist table");
                    errorLabels.add("Status not matching with selected item: " + header + " in Shortlist table");
                    break;
                }
                sop("Status matched for selected item: " + header + " in Shortlist table");
            }
        }

        private static void verifySearchBox(String shortlistRefNum)
        throws InterruptedException {
            WebElement element;

            // Shortlist Summary Search box
            driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[1]/div[2]/span/input"))
                .sendKeys(shortlistRefNum);

            // Search Summary Search box
            driver.findElement(By.xpath(
                    "/html/body/div/div/article[1]/section/article/article[2]/div/div/div[1]/div[2]/span/input"))
                .sendKeys(shortlistRefNum);

            // Only record found in Shortlist Summary Table
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[1]/div/div/div[4]/div/div[1]/table/tbody/tr/td[1]/a"));
            highlight(element);
            if (element.getText().equals(shortlistRefNum)) {
                sop("Record matched in Shortlist Summary Table after searching.");
            } else {
                sop("Record mismatched in Shortlist Summary Table after searching.");
                errorLabels.add(
                    "Record not matching in Shortlist Summary Table after searching");
            }

            // No records found in Search Table
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/div/div/div[4]/div/div[1]/table/tbody/tr/td"));
            highlight(element);
            if (element.getText().equals("No records available")) {
                sop("'No records found' message matched in Search Summary Table.");
            } else {
                sop("Record found in Search Summary Table.");
                errorLabels
                    .add("Record found in Search Summary Table");
            }
        }

        private static void verifyAdvancedFilters() throws InterruptedException {
            WebElement element;

            // // Apply Filters button
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/footer/button[3]/span"));
            // highlight(element);
            // if (element.getText().trim().equals("Apply Filters"))
            // sop("Button Label 'Apply Filters' matched in Site Search page");
            // else {
            // sop("Button Label 'Apply Filters' mismatch in Site Search page!");
            // errorLabels.add("'Apply Filters' button label in Site Search page");
            // }

            // Applying filters
            // Last Active
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/div/div/div/ul/li[5]"));
            highlight(element);
            element.click();

            // Stage at diagnosis
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[1]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[31]/div/article/section/article[2]/article/section[1]/span/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // Line of Therapy
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[2]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[32]/div/article/ul/span[2]/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // ECOGs
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[3]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[33]/div/article/ul/span[2]/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // Comorbidities
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[4]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[34]/div/article/ul/span[5]/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // Race
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[5]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[35]/div/article/ul/span[3]/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // Ethnicity
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[6]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[36]/div/article/ul/span[2]/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // Scrolling down
            WebElement scrollSection = driver
                .findElement(By.xpath("/html/body/div/div/article[1]/section"));
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 500)", scrollSection);

            // Gender
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/section/div[1]/input"));
            highlight(element);
            Thread.sleep(800);
            element.click();

            // Age
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[2]/div/div/ul/li[4]"));
            highlight(element);
            Thread.sleep(800);
            element.click();

            // State
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[8]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[37]/div/article/ul/span[3]/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // Apply Button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/footer/button[3]"));
            highlight(element);
            element.click();

            WebElement loadingMask = driver.findElement(
                    By.xpath("/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading filters...");
                    Thread.sleep(2000);
                    checkErrorPage();
                }

            // Get Matching Sites count
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/header/section[2]/div[2]/span[1]"));
            highlight(element);
            if (Integer.parseInt(element.getText()) == 0)
                sop("Matching Site count matched with Apply Filters");
            else {
                sop("Matching Site count found " + element.getText());
                sop("Result count does not match with Apply Filters");
                errorLabels.add("Result count does not match with Apply Filters");
            }

            // Resetting filters
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/footer/button[2]"));
            highlight(element);
            element.click();

            // Reclicking Apply Filters
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/footer/button[3]"));
            highlight(element);
            element.click();

            loadingMask = driver.findElement(
                    By.xpath("/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading filters...");
                    Thread.sleep(2000);
                    checkErrorPage();
                }

            // Get Matching Sites count
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/header/section[2]/div[2]/span[1]"));
            highlight(element);
            if (Integer.parseInt(element.getText()) > 0)
                sop("Matching Site count matched with no Filters");
            else {
                sop("Matching Site count found " + element.getText());
                sop("Result count does not match with no Filters");
                errorLabels.add("Result count does not match with no Filters");
            }

            /*
             * Applying All Filters for data 
             * *****************************
             */
            // Expanding Relevancy Filters
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/header/button"));
            highlight(element);
            element.click();

            // Diagnosis
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[1]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath( 
                "/html/body/overlay-container/article[1]/div[2]/article[3]/section/span/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By
                .xpath("/html/body/div/div/article[1]/article/div[1]/div/div"));
            element.click();

            // Biomarkers
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[2]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[29]/div[2]/article[10]/span/input"));
            element.click();
            // Textbox
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[29]/div[2]/article[10]/article/span/input"));
            element.click();
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[11]/article/ul/li[3]"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "//html/body/div/div/article[1]/article/div[1]/div/div"));
            element.click();

            Thread.sleep(1000);

            // Treatments
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[1]/div/article[3]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[30]/section/div[1]/div[1]/input"));
            element.click();
            driver.findElement(By.xpath(
                    "/html/body/overlay-container/article[30]/div/div/span/input"))
                .sendKeys("hercep");
            Thread.sleep(800);
            element.findElement(By.xpath(
                    "/html/body/overlay-container/article[30]/div/article/span[1]/input"))
                .click();
            highlight(element);
            // element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By
                .xpath("/html/body/div/div/article[1]/article/div[1]/div/div"));
            element.click();

            // Apply Filters - Using SVG icon on top
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/header/div[2]/button[3]"));
            highlight(element);
            element.click();

            loadingMask = driver.findElement(
                    By.xpath("/html/body/div/div/article[1]/section/article/article[1]"));
            while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                sop("Loading filters...");
                Thread.sleep(2000);
                checkErrorPage();
            }
               
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/header/div[2]/button[4]"));
            highlight(element);
            element.click();
            Thread.sleep(2000);
            
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/div[1]/button[1]"));
            highlight(element);
            element.click();
            Thread.sleep(2000);
            

            // Check matching site count
            // Get Matching Sites count
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/header/section[2]/div[2]/span[1]"));
            highlight(element);
            if (Integer.parseInt(element.getText()) == initialSiteCount)
                sop("Matching Site count matched after reapplying Relevancy filters");
            else {
                sop("Matching Site count found " + element.getText());
                sop("Result count does not match after reapplying Relevancy filters");
                errorLabels.add(
                    "Result count does not match after reapplying Relevancy filters");
            }

        }

        private static void reapplyAdvancedFilters() throws InterruptedException {
            WebElement element;

            // Expanding Relevancy Filters - Click on Advanced Filter expand button
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/header/button"));
            highlight(element);
            element.click();

            // Applying filters
            // // Last Active
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/div/div/div/ul/li[5]"));
            // highlight(element);
            // element.click();

            // Stage at diagnosis
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[1]/span/span/input"));
            highlight(element);
            element.click();
            Thread.sleep(800);
            // Choosing option
            element = driver.findElement(By.xpath(
                "/html/body/overlay-container/article[31]/div/article/section/article[2]/article/section[1]/span/input"));
            element.click();
            Thread.sleep(800);
            // Clicking out to close drop box
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            element.click();

            // // Line of Therapy
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[2]/span/span/input"));
            // highlight(element);
            // element.click();
            // Thread.sleep(800);
            // // Choosing option
            // element =
            // driver.findElement(By.xpath("/html/body/overlay-container/article[32]/div/article/ul/span[2]/input"));
            // element.click();
            // Thread.sleep(800);
            // // Clicking out to close drop box
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            // element.click();
            //
            // // ECOGs
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[3]/span/span/input"));
            // highlight(element);
            // element.click();
            // Thread.sleep(800);
            // // Choosing option
            // element =
            // driver.findElement(By.xpath("/html/body/overlay-container/article[33]/div/article/ul/span[2]/input"));
            // element.click();
            // Thread.sleep(800);
            // // Clicking out to close drop box
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            // element.click();
            //
            // // Comorbidities
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[4]/span/span/input"));
            // highlight(element);
            // element.click();
            // Thread.sleep(800);
            // // Choosing option
            // element =
            // driver.findElement(By.xpath("/html/body/overlay-container/article[34]/div/article/ul/span[5]/input"));
            // element.click();
            // Thread.sleep(800);
            // // Clicking out to close drop box
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            // element.click();
            //
            // // Race
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[5]/span/span/input"));
            // highlight(element);
            // element.click();
            // Thread.sleep(800);
            // // Choosing option
            // element =
            // driver.findElement(By.xpath("/html/body/overlay-container/article[35]/div/article/ul/span[3]/input"));
            // element.click();
            // Thread.sleep(800);
            // // Clicking out to close drop box
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            // element.click();
            //
            // // Ethnicity
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[6]/span/span/input"));
            // highlight(element);
            // element.click();
            // Thread.sleep(800);
            // // Choosing option
            // element =
            // driver.findElement(By.xpath("/html/body/overlay-container/article[36]/div/article/ul/span[2]/input"));
            // element.click();
            // Thread.sleep(800);
            // // Clicking out to close drop box
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            // element.click();

            // Scrolling down
            WebElement scrollSection = driver
                .findElement(By.xpath("/html/body/div/div/article[1]/section"));
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollBy(0, 1000)", scrollSection);

            // Gender
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[7]/section/div[1]/input"));
            highlight(element);
            Thread.sleep(800);
            element.click();

            // // Age
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[2]/div/div/ul/li[4]"));
            // highlight(element);
            // Thread.sleep(800);
            // element.click();
            //
            // // State
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/article[8]/span/span/input"));
            // highlight(element);
            // element.click();
            // Thread.sleep(800);
            // // Choosing option
            // element =
            // driver.findElement(By.xpath("/html/body/overlay-container/article[37]/div/article/ul/span[3]/input"));
            // element.click();
            // Thread.sleep(800);
            // // Clicking out to close drop box
            // element =
            // driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/main/article[2]/div/section[1]/label/span"));
            // element.click();

            // Apply Button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/article[2]/footer/button[3]"));
            highlight(element);
            element.click();

            WebElement loadingMask = driver.findElement(
                    By.xpath("/html/body/div/div/article[1]/section/article/article[1]"));
                while (!loadingMask.getCssValue("display").equalsIgnoreCase("none")) {
                    sop("Loading filters...");
                    Thread.sleep(2000);
                    checkErrorPage();
                }
        }

        private static void reVerifyAdvancedFilters() throws InterruptedException {
            WebElement element;

            // Verify Filters on top bar
            // 1 Biomarker
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/div[1]/div/article[1]"));
            highlight(element);
            if (element.getText().trim().equals("1 Biomarker")) {
                sop("'1 Biomarker' filter in top bar matched.");
            } else {
                sop("'1 Biomarker' filter not matched in top bar.");
                errorLabels.add("'1 Biomarker' filter not matched in top bar.");
            }

            // 101 Breast Cancer
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/div[1]/div/article[2]"));
            highlight(element);
            if (element.getText().trim().equals("101 Breast Cancer")) {
                sop("'101 Breast Cancer' filter in top bar matched.");
            } else {
                sop("'101 Breast Cancer' filter not matched in top bar.");
                errorLabels
                    .add("'101 Breast Cancer' filter not matched in top bar.");
            }

            // 1 Treatment Drug Inclusion
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/div[1]/div/article[3]"));
            highlight(element);
            if (element.getText().trim().equals("1 Treatment Drug Inclusion")) {
                sop("'1 Treatment Drug Inclusion' filter in top bar matched.");
            } else {
                sop("'1 Treatment Drug Inclusion' filter not matched in top bar.");
                errorLabels.add(
                    "'1 Treatment Drug Inclusion' filter not matched in top bar.");
            }

            // Gender All
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/div[1]/div/article[4]"));
            highlight(element);
            if (element.getText().trim().equals("Gender All")) {
                sop("'Gender All' filter in top bar matched.");
            } else {
                sop("'Gender All' filter not matched in top bar.");
                errorLabels.add("'Gender All' filter not matched in top bar.");
            }

            // 11 Stages at Diagnosis
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/div[1]/div/article[5]"));
            highlight(element);
            if (element.getText().trim().equals("11 Stages at Diagnosis")) {
                sop("'11 Stages at Diagnosis' filter in top bar matched.");
            } else {
                sop("'11 Stages at Diagnosis' filter not matched in top bar.");
                errorLabels.add(
                    "'11 Stages at Diagnosis' filter not matched in top bar.");
            }

            // Verify Filters in Sites - 1 Biomarker, 101 Diagnoses, Gender All, 11 Stages at Diagnosis
            // First Site
            element = driver.findElement(By.xpath( 
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/article/div/span[7]/article"));
            highlight(element);
            if (element.getText().trim().equals(
                    "1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender All, 11 Stages at Diagnosis")) {
                sop("Filter matched in first site result.");
            } else {
                sop("Filter not matching in first site result.");
                errorLabels.add("Filter not matching in first site result.");
            }
            // Second Site
            // Verify Filters in Sites - 1 Biomarker, 101 Diagnoses, Gender All, 11 Stages at Diagnosis
            element = driver.findElement(By.xpath(
                "/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/div/span[7]/article"));
            highlight(element);
            if (element.getText().trim().equals(
                    "1 Biomarker, 101 Diagnoses, 1 Treatment Drug Inclusion, Gender All, 11 Stages at Diagnosis")) {
                sop("Filter matched in second site result.");
            } else {
                sop("Filter not matching in second site result.");
                errorLabels.add("Filter not matching in second site result.");
            }
        }

        private static void checkAddnRemovebtn() throws InterruptedException {
            /**
             * Shortlisted Site
             * ****************
             */
            // Selecting first site 
            WebElement element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/span/input"));
            highlight(element);
            element.click();

            // Checking ADD TO SHORTLIST button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]"));
            highlight(element);
            if (!element.isEnabled())
                sop("'Add to Shortlist' button is disabled for shortlisted site as expected.");
            else {
                sop("'Add to Shortlist' button is not disabled for shortlisted site as expected.");
                errorLabels.add("'Add to Shortlist' button is not disabled for shortlisted site");
            }

            // Checking REMOVE FROM SHORTLIST button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]"));
            highlight(element);
            if (element.isEnabled())
                sop("'Remove from Shortlist' button is enabled for shortlisted site as expected.");
            else {
                sop("'Remove from Shortlist' button is not enabled for shortlisted site as expected.");
                errorLabels.add("'Remove from Shortlist' button is not enabled for shortlisted site");
            }

            // Unselecting first site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/span/input"));
            highlight(element);
            element.click();

            // Scrolling down
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0,700)", driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]")));

            /** 
             * Non-Shortlisted Site
             */
            // Selecting fifth site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[5]/span/input"));
            highlight(element);
            element.click();

            // Checking ADD TO SHORTLIST button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[1]"));
            highlight(element);
            if (element.isEnabled())
                sop("'Add to Shortlist' button is enabled for non-shortlisted site as expected.");
            else {
                sop("'Add to Shortlist' button is not enabled for non-shortlisted site as expected.");
                errorLabels.add("'Add to Shortlist' button is not enabled for non-shortlisted site");
            }

            // Checking REMOVE FROM SHORTLIST button
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/button[2]"));
            highlight(element);
            if (!element.isEnabled())
                sop("'Remove from Shortlist' button is disabled for non-shortlisted site as expected.");
            else {
                sop("'Remove from Shortlist' button is not disabled for non-shortlisted site as expected.");
                errorLabels.add("'Remove from Shortlist' button is not disabled for non-shortlisted site");
            }

            // Unselecting fifth element
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[5]/span/input"));
            highlight(element);
            element.click();

            // Scrolling back to top
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0,-700)", driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]")));
        }

        public static void checkSitesOrder() throws InterruptedException {
            /** 
             * Patient Count Ascending
             * ***********************
             */
            // Clicking on dropdown
            WebElement element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/div/article/span/span"));
            highlight(element);
            element.click();

            // Selecting Ascending
            element = driver.findElement(By.xpath("/html/body/overlay-container/article[39]/article/ul/li[1]"));
            highlight(element);
            element.click();
            Thread.sleep(500);

            // 1st Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/article/div/span[2]/span[2]"));
            highlight(element);
            int count1 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            // 2nd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/div/span[2]/span[2]"));
            highlight(element);
            int count2 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            // 3rd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/div/span[2]/span[2]"));
            highlight(element);
            int count3 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));

            if (count1 <= count2 && count2 <= count3) {
                sop("'Patient Count Ascending' on Shortlist Sites working as expected.");
            } else {
                sop("'Patient Count Ascending' on Shortlist Sites not working as expected.");
                errorLabels.add("'Patient Count Ascending' on Shortlist Sites not working as expected");
            }

            /** 
             * Patient Count Descending
             * ************************
             */
            // Clicking on dropdown
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/div/article/span/span"));
            highlight(element);
            element.click();

            // Selecting Descending
            element = driver.findElement(By.xpath("/html/body/overlay-container/article[39]/article/ul/li[2]"));
            highlight(element);
            element.click();
            Thread.sleep(500);

            // 1st Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/article/div/span[2]/span[2]"));
            highlight(element);
            count1 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            // 2nd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/div/span[2]/span[2]"));
            highlight(element);
            count2 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));
            // 3rd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/div/span[2]/span[2]"));
            highlight(element);
            count3 = Integer.parseInt(element.getText().replace(" patients", "").replace(" patient", ""));

            if (count1 >= count2 && count2 >= count3) {
                sop("'Patient Count Descending' on Shortlist Sites working as expected.");
            } else {
                sop("'Patient Count Descending' on Shortlist Sites not working as expected.");
                errorLabels.add("'Patient Count Descending' on Shortlist Sites not working as expected");
            }

            /** 
             * Site Number Ascending
             * *********************
             */
            // Clicking on dropdown
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/div/article/span/span"));
            highlight(element);
            element.click();

            // Selecting Ascending option 
            element = driver.findElement(By.xpath("/html/body/overlay-container/article[39]/article/ul/li[3]"));
            highlight(element);
            element.click();
            Thread.sleep(500);

            // 1st Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/article/section/button/span"));
            highlight(element);
            count1 = Integer.parseInt(element.getText().replace("Site #", ""));
            // 2nd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/section/button/span"));
            highlight(element);
            count2 = Integer.parseInt(element.getText().replace("Site #", ""));
            // 3rd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/section/button/span"));
            highlight(element);
            count3 = Integer.parseInt(element.getText().replace("Site #", ""));

            if (count1 < count2 && count2 < count3) {
                sop("'Site Number Ascending' on Shortlist Sites working as expected.");
            } else {
                sop("'Site Number Ascending' on Shortlist Sites not working as expected.");
                errorLabels.add("'Patient Number Ascending' on Shortlist Sites not working as expected");
            }

            /** 
             * Site Number Descending
             * **********************
             */
            // Clicking on dropdown
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/div/article/span/span"));
            highlight(element);
            element.click();

            // Selecting Descending option
            element = driver.findElement(By.xpath("/html/body/overlay-container/article[39]/article/ul/li[4]"));
            highlight(element);
            element.click();
            Thread.sleep(500);

            // 1st Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[1]/article/section/button/span"));
            highlight(element);
            count1 = Integer.parseInt(element.getText().replace("Site #", ""));
            // 2nd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[2]/article/section/button/span"));
            highlight(element);
            count2 = Integer.parseInt(element.getText().replace("Site #", ""));
            // 3rd Site
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[2]/div/div[3]/article/section/button/span"));
            highlight(element);
            count3 = Integer.parseInt(element.getText().replace("Site #", ""));

            if (count1 > count2 && count2 > count3) {
                sop("'Site Number Descending' on Shortlist Sites working as expected.");
            } else {
                sop("'Site Number Descending' on Shortlist Sites not working as expected.");
                errorLabels.add("'Patient Number Descending' on Shortlist Sites not working as expected");
            }
            
            /**
             * Reverting to PATIENT COUNT DESCENDING
             * *************************************
             */
            // Clicking on dropdown
            element = driver.findElement(By.xpath("/html/body/div/div/article[1]/section/article/main/section/article[1]/div[2]/div/article/span/span"));
            highlight(element);
            element.click();

            // Selecting Descending
            element = driver.findElement(By.xpath("/html/body/overlay-container/article[39]/article/ul/li[2]"));
            highlight(element);
            element.click();
            Thread.sleep(500);
            
            // TODO : ADD FOR NUMBER OF TRIALS // 
        }

        private static void highlight(WebElement element)
        throws InterruptedException {
            String jsStyle = "'3px solid red'";
            String jsStyleRemove = "'0px solid red'";

            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].style.border=" + jsStyle, element);
            Thread.sleep(800);
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border=" + jsStyleRemove, element);
        }

        //    private static void highlight(WebElement element, int timer) throws InterruptedException {
        //        String jsStyle = "'3px solid red'";
        //        String jsStyleRemove = "'0px solid red'";
        //
        //        ((JavascriptExecutor) driver)
        //        .executeScript("arguments[0].style.border=" + jsStyle, element);
        //        Thread.sleep(timer);
        //        ((JavascriptExecutor) driver).executeScript(
        //            "arguments[0].style.border=" + jsStyleRemove, element);
        //    }

        private static void printErrors() {
            if (errorLabels.isEmpty()) {
                sop("");
                sop("No mismatch in labels found!!!");
            } else {
                sop("");
                sop(":::::::: Mismatch in Labels :::::::");
                sop("");
                errorLabels.forEach(item -> {
                    sop(item);
                });
            }
        }

        public static void main(String[] args) throws InterruptedException {
//            System.setProperty("webdriver.chrome.driver",
//                "/opt/homebrew/Caskroom/chromedriver/110.0.5481.77/chromedriver");
             System.setProperty("webdriver.chrome.driver",
             "C:\\chromedriver\\102\\chromedriver.exe");
            // Open Chrome
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            // driver.get(https://testlogin.intrinsiq.com/Account/Login);
            try {
                runAutomation();
            } catch (Exception e) {
                sop(e.getMessage());
                checkErrorPage();
            } finally {
                // driver.close();
            }
        }

        public static void verifyHamburgerOptions(String xpath, String option,
            String header) throws InterruptedException {

            WebElement element = driver.findElement(By.xpath(xpath));
            highlight(element);           
            if (element.getText().equals(option))
                sop("'" + option + "' option under hamburger menu for '" + header +
                    "' matched");
            else {
                sop("'" + option + "' option under hamburger menu for '" + header +
                    "' mismatch!");
                errorLabels.add("'" + option + "' option under hamburger menu for '" +
                    header + "' subheading");
            }            
        }

        public static ArrayList < String > getDiagnosesFilters() {
            ArrayList < String > diagnosesFilters = new ArrayList < String > ();

            diagnosesFilters.add(
                "C44.299 - Other specified malignant neoplasm of skin of left ear and external auricular canal");
            diagnosesFilters.add("C50 - Malignant neoplasm of breast");
            diagnosesFilters.add("C50.0 - Malignant neoplasm of nipple and areola");
            diagnosesFilters.add(
                "C50.01 - Malignant neoplasm of nipple and areola, female");
            diagnosesFilters.add(
                "C50.011 - Malignant neoplasm of nipple and areola, right female breast");
            diagnosesFilters.add(
                "C50.012 - Malignant neoplasm of nipple and areola, left female breast");
            diagnosesFilters.add(
                "C50.019 - Malignant neoplasm of nipple and areola, unspecified female breast");
            diagnosesFilters.add(
                "C50.021 - Malignant neoplasm of nipple and areola, right male breast");
            diagnosesFilters.add(
                "C50.022 - Malignant neoplasm of nipple and areola, left male breast");
            diagnosesFilters.add(
                "C50.029 - Malignant neoplasm of nipple and areola, unspecified male breast");
            diagnosesFilters
                .add("C50.1 - Malignant neoplasm of central portion of breast");
            diagnosesFilters.add(
                "C50.11 - Malignant neoplasm of central portion of breast, female");
            diagnosesFilters.add(
                "C50.111 - Malignant neoplasm of central portion of right female breast");
            diagnosesFilters.add(
                "C50.112 - Malignant neoplasm of central portion of left female breast");
            diagnosesFilters.add(
                "C50.119 - Malignant neoplasm of central portion of unspecified female breast");
            diagnosesFilters.add(
                "C50.12 - Malignant neoplasm of central portion of breast, male");
            diagnosesFilters.add(
                "C50.121 - Malignant neoplasm of central portion of right male breast");
            diagnosesFilters.add(
                "C50.122 - Malignant neoplasm of central portion of left male breast");
            diagnosesFilters.add(
                "C50.129 - Malignant neoplasm of central portion of unspecified male breast");
            diagnosesFilters.add(
                "C50.2 - Malignant neoplasm of upper-inner quadrant of breast");
            diagnosesFilters.add(
                "C50.21 - Malignant neoplasm of upper-inner quadrant of breast, female");
            diagnosesFilters.add(
                "C50.211 - Malignant neoplasm of upper-inner quadrant of right female breast");
            diagnosesFilters.add(
                "C50.212 - Malignant neoplasm of upper-inner quadrant of left female breast");
            diagnosesFilters.add(
                "C50.219 - Malignant neoplasm of upper-inner quadrant of unspecified female breast");
            diagnosesFilters.add(
                "C50.22 - Malignant neoplasm of upper-inner quadrant of breast, male");
            diagnosesFilters.add(
                "C50.221 - Malignant neoplasm of upper-inner quadrant of right male breast");
            diagnosesFilters.add(
                "C50.222 - Malignant neoplasm of upper-inner quadrant of left male breast");
            diagnosesFilters.add(
                "C50.229 - Malignant neoplasm of upper-inner quadrant of unspecified male breast");
            diagnosesFilters.add(
                "C50.3 - Malignant neoplasm of lower-inner quadrant of breast");
            diagnosesFilters.add(
                "C50.31 - Malignant neoplasm of lower-inner quadrant of breast, female");
            diagnosesFilters.add(
                "C50.311 - Malignant neoplasm of lower-inner quadrant of right female breast");
            diagnosesFilters.add(
                "C50.312 - Malignant neoplasm of lower-inner quadrant of left female breast");
            diagnosesFilters.add(
                "C50.319 - Malignant neoplasm of lower-inner quadrant of unspecified female breast");
            diagnosesFilters.add(
                "C50.321 - Malignant neoplasm of lower-inner quadrant of right male breast");
            diagnosesFilters.add(
                "C50.322 - Malignant neoplasm of lower-inner quadrant of left male breast");
            diagnosesFilters.add(
                "C50.329 - Malignant neoplasm of lower-inner quadrant of unspecified male breast");
            diagnosesFilters.add(
                "C50.4 - Malignant neoplasm of upper-outer quadrant of breast");
            diagnosesFilters.add(
                "C50.41 - Malignant neoplasm of upper-outer quadrant of breast, female");
            diagnosesFilters.add(
                "C50.411 - Malignant neoplasm of upper-outer quadrant of right female breast");
            diagnosesFilters.add(
                "C50.412 - Malignant neoplasm of upper-outer quadrant of left female breast");
            diagnosesFilters.add(
                "C50.419 - Malignant neoplasm of upper-outer quadrant of unspecified female breast");
            diagnosesFilters.add(
                "C50.42 - Malignant neoplasm of upper-outer quadrant of breast, male");
            diagnosesFilters.add(
                "C50.421 - Malignant neoplasm of upper-outer quadrant of right male breast");
            diagnosesFilters.add(
                "C50.422 - Malignant neoplasm of upper-outer quadrant of left male breast");
            diagnosesFilters.add(
                "C50.429 - Malignant neoplasm of upper-outer quadrant of unspecified male breast");
            diagnosesFilters.add(
                "C50.5 - Malignant neoplasm of lower-outer quadrant of breast");
            diagnosesFilters.add(
                "C50.51 - Malignant neoplasm of lower-outer quadrant of breast, female");
            diagnosesFilters.add(
                "C50.511 - Malignant neoplasm of lower-outer quadrant of right female breast");
            diagnosesFilters.add(
                "C50.512 - Malignant neoplasm of lower-outer quadrant of left female breast");
            diagnosesFilters.add(
                "C50.519 - Malignant neoplasm of lower-outer quadrant of unspecified female breast");
            diagnosesFilters.add(
                "C50.521 - Malignant neoplasm of lower-outer quadrant of right male breast");
            diagnosesFilters.add(
                "C50.522 - Malignant neoplasm of lower-outer quadrant of left male breast");
            diagnosesFilters.add(
                "C50.529 - Malignant neoplasm of lower-outer quadrant of unspecified male breast");
            diagnosesFilters
                .add("C50.6 - Malignant neoplasm of axillary tail of breast");
            diagnosesFilters.add(
                "C50.61 - Malignant neoplasm of axillary tail of breast, female");
            diagnosesFilters.add(
                "C50.611 - Malignant neoplasm of axillary tail of right female breast");
            diagnosesFilters.add(
                "C50.612 - Malignant neoplasm of axillary tail of left female breast");
            diagnosesFilters.add(
                "C50.619 - Malignant neoplasm of axillary tail of unspecified female breast");
            diagnosesFilters.add(
                "C50.621 - Malignant neoplasm of axillary tail of right male breast");
            diagnosesFilters.add(
                "C50.622 - Malignant neoplasm of axillary tail of left male breast");
            diagnosesFilters.add(
                "C50.629 - Malignant neoplasm of axillary tail of unspecified male breast");
            diagnosesFilters.add(
                "C50.8 - Malignant neoplasm of overlapping sites of breast");
            diagnosesFilters.add(
                "C50.81 - Malignant neoplasm of overlapping sites of breast, female");
            diagnosesFilters.add(
                "C50.811 - Malignant neoplasm of overlapping sites of right female breast");
            diagnosesFilters.add(
                "C50.812 - Malignant neoplasm of overlapping sites of left female breast");
            diagnosesFilters.add(
                "C50.819 - Malignant neoplasm of overlapping sites of unspecified female breast");
            diagnosesFilters.add(
                "C50.82 - Malignant neoplasm of overlapping sites of breast, male");
            diagnosesFilters.add(
                "C50.821 - Malignant neoplasm of overlapping sites of right male breast");
            diagnosesFilters.add(
                "C50.822 - Malignant neoplasm of overlapping sites of left male breast");
            diagnosesFilters.add(
                "C50.829 - Malignant neoplasm of overlapping sites of unspecified male breast");
            diagnosesFilters.add(
                "C50.9 - Malignant neoplasm of breast of unspecified site");
            diagnosesFilters.add(
                "C50.91 - Malignant neoplasm of breast of unspecified site, female");
            diagnosesFilters.add(
                "C50.911 - Malignant neoplasm of unspecified site of right female breast");
            diagnosesFilters.add(
                "C50.912 - Malignant neoplasm of unspecified site of left female breast");
            diagnosesFilters.add(
                "C50.919 - Malignant neoplasm of unspecified site of unspecified female breast");
            diagnosesFilters.add(
                "C50.92 - Malignant neoplasm of breast of unspecified site, male");
            diagnosesFilters.add(
                "C50.921 - Malignant neoplasm of unspecified site of right male breast");
            diagnosesFilters.add(
                "C50.922 - Malignant neoplasm of unspecified site of left male breast");
            diagnosesFilters.add(
                "C50.929 - Malignant neoplasm of unspecified site of unspecified male breast");
            diagnosesFilters.add("D05 - Carcinoma in situ of breast");
            diagnosesFilters.add("D05.0 - Lobular carcinoma in situ of breast");
            diagnosesFilters.add(
                "D05.00 - Lobular carcinoma in situ of unspecified breast");
            diagnosesFilters
                .add("D05.01 - Lobular carcinoma in situ of right breast");
            diagnosesFilters
                .add("D05.02 - Lobular carcinoma in situ of left breast");
            diagnosesFilters.add("D05.1 - Intraductal carcinoma in situ of breast");
            diagnosesFilters.add(
                "D05.10 - Intraductal carcinoma in situ of unspecified breast");
            diagnosesFilters
                .add("D05.11 - Intraductal carcinoma in situ of right breast");
            diagnosesFilters
                .add("D05.12 - Intraductal carcinoma in situ of left breast");
            diagnosesFilters.add(
                "D05.8 - Other specified type of carcinoma in situ of breast");
            diagnosesFilters.add(
                "D05.80 - Other specified type of carcinoma in situ of unspecified breast");
            diagnosesFilters.add(
                "D05.81 - Other specified type of carcinoma in situ of right breast");
            diagnosesFilters.add(
                "D05.82 - Other specified type of carcinoma in situ of left breast");
            diagnosesFilters
                .add("D05.9 - Unspecified type of carcinoma in situ of breast");
            diagnosesFilters.add(
                "D05.90 - Unspecified type of carcinoma in situ of unspecified breast");
            diagnosesFilters.add(
                "D05.91 - Unspecified type of carcinoma in situ of right breast");
            diagnosesFilters.add(
                "D05.92 - Unspecified type of carcinoma in situ of left breast");
            diagnosesFilters.add(
                "D48.60 - Neoplasm of uncertain behavior of unspecified breast");
            diagnosesFilters
                .add("D48.61 - Neoplasm of uncertain behavior of right breast");
            diagnosesFilters
                .add("D48.62 - Neoplasm of uncertain behavior of left breast");
            diagnosesFilters
                .add("D49.3 - Neoplasm of unspecified behavior of breast");
            diagnosesFilters.add("N60.32 - Fibrosclerosis of left breast");

            return diagnosesFilters;
        }
    }
