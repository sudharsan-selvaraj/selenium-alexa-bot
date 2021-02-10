package com.testninja;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        AlexaDriver alexaDriver = new AlexaDriver(getWebDriver(), "Alexa");

        for(String questions: List.of(
                "what is your name",
                "where are you from")
        ) {
            String reply = alexaDriver.askQuestion(questions);
            System.out.println("Reply:" + reply);
            alexaDriver.sleep(2000);
        }

        alexaDriver.quit();
    }

    public static WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--enable-speech-dispatcher");
        options.addArguments("--use-fake-ui-for-media-stream");
        options.addArguments("--use-fake-ui-for-media-stream");
        options.addArguments( "--window-size=0,0");
        options.addArguments( "--window-position=0,0");
        return new ChromeDriver(options);
    }
}
