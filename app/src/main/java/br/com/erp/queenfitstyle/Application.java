package br.com.erp.queenfitstyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "br.com.erp.queenfitstyle.app",
        "br.com.erp.queenfitstyle.catalog",
        "br.com.erp.queenfitstyle.upload"
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
