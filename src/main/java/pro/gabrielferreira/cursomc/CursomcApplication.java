package pro.gabrielferreira.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pro.gabrielferreira.cursomc.services.S3Service;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	/* Classe principal do spring boot */
	
	@Autowired
	private S3Service s3service;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		s3service.uploadFile("/home/gabriel/Pictures/mulher.jpg");
	}
}
