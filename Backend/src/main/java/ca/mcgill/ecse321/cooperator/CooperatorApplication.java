package ca.mcgill.ecse321.cooperator;

@RestController
@SpringBootApplication
public class CooperatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(CooperatorApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting() {
		return "Hello world!";
	}
}