package ca.mcgill.ecse321.cooperator;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;


import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
@EnableSwagger2
public class CooperatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(CooperatorApplication.class,args);
	}

  @RequestMapping("/")
  public String greeting(){
    return "Hello testing";
  }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/CooperatorController.*"))
                .build();
    }
  
}
