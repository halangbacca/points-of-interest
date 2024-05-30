package com.halan.poi;

import com.halan.poi.entity.PointOfInterest;
import com.halan.poi.repository.PointOfInterestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoiApplication implements CommandLineRunner {

    public PoiApplication(PointOfInterestRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PoiApplication.class, args);
    }

    private final PointOfInterestRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new PointOfInterest("Lanchonete", 27L, 12L));
        repository.save(new PointOfInterest("Posto", 31L, 18L));
        repository.save(new PointOfInterest("Joalheria", 15L, 12L));
        repository.save(new PointOfInterest("FLoricultura", 19L, 21L));
        repository.save(new PointOfInterest("Pub", 12L, 8L));
        repository.save(new PointOfInterest("Supermercado", 23L, 6L));
        repository.save(new PointOfInterest("Churrascaria", 28L, 2L));
    }
}
