package com.testninja;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class AlexaDriver {

    private final WebDriver driver;
    private final String alexaInvokeKeyword;
    private final static String[] functionsToBeLoaded = new String[]{"init", "listen", "speak"};

    public AlexaDriver(WebDriver driver, String alexaInvokeKeyword) {
        this.driver = driver;
        this.alexaInvokeKeyword = alexaInvokeKeyword;

        loadClientSideScript();
    }

    public AlexaDriver(WebDriver driver) {
        this(driver, "alexa!");
    }


    public String askQuestion(String question) {
        speak(alexaInvokeKeyword);
        sleep(1000);

        System.out.println("\n\nCommand: " + question);
        speak(question);
        return listen();
    }

    private void speak(String text) {
        executeAsyncScript("speak(arguments[0]).then(callback);", text);
    }

    private String listen() {
        return (String) executeAsyncScript("listen(callback)");
    }

    private void loadClientSideScript() {
        if (isWebKitPresent()) {
            driver.get("data:text/html," + ResourceUtil.getFileContent("/index.html"));
            for (String function : functionsToBeLoaded) {
                executeScript("window." + function + "=" + JavaScriptFunctions.getSrc(function));
            }
            executeScript("init()");
            driver.findElement(By.id("start-button")).click();
        } else {
            System.out.println("Webkit not supported by the browser");
        }
    }

    private Boolean isWebKitPresent() {
        return (Boolean) executeScript("return 'webkitSpeechRecognition' in window;");
    }

    private Object executeScript(String js) {
        return (Object) ((JavascriptExecutor) driver).executeScript(js);
    }

    private Object executeAsyncScript(String js, Object... args) {
        return (Object) ((JavascriptExecutor) driver).executeAsyncScript(
                "var callback = arguments[arguments.length - 1];" + js,
                args);
    }

    public void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void quit() {
        this.driver.quit();
    }
}
