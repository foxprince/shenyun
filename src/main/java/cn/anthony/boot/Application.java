package cn.anthony.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import cn.anthony.boot.domain.Customer;
import cn.anthony.boot.repository.CustomerRepository;

@SpringBootApplication
// same as @Configuration @EnableAutoConfiguration @ComponentScan
// 你可以使用@EntityScan注解自定义实体扫描路径。具体参考Section 67.4, “Separate @Entity definitions
// from Spring configuration”。
// @EnableMongoRepositories
public class Application extends SpringBootServletInitializer {

    // @Override
    // protected SpringApplicationBuilder configure(SpringApplicationBuilder
    // application) {
    // return application.sources(Application.class);
    // }

    public static void main(String[] args) {
	// SpringApplication.run(Application.class, args);
	SpringApplication app = new SpringApplication(Application.class);
	// app.setWebEnvironment(true);
	// app.setApplicationContextClass(AnnotationConfigEmbeddedWebApplicationContext.class);
	// app.setShowBanner(false);
	// app.setSources(sources);
	app.run(args);
    }

    @Autowired
    private CustomerRepository repository;

    public void run(String... args) throws Exception {
	repository.deleteAll();

	// save a couple of customers
	repository.save(new Customer("Alice", "Smith"));
	repository.save(new Customer("Bob", "Smith"));

	// fetch all customers
	System.out.println("Customers found with findAll():");
	System.out.println("-------------------------------");
	for (Customer customer : repository.findAll()) {
	    System.out.println(customer);
	}
	System.out.println();

	// fetch an individual customer
	System.out.println("Customer found with findByFirstName('Alice'):");
	System.out.println("--------------------------------");
	System.out.println(repository.findByFirstName("Alice"));

	System.out.println("Customers found with findByLastName('Smith'):");
	System.out.println("--------------------------------");
	for (Customer customer : repository.findByLastName("Smith")) {
	    System.out.println(customer);
	}

    }

}