package com.tez.contoller;



import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
//        QueryController c = new QueryController();
//        c.deneme("What are \"cast\" and \"resemble movies\" and when is the \"publish year\" of the mvoie \"Titanic\"?"); 
    }
    
}
