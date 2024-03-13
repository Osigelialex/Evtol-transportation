package com.example.transportation.transportation.config;

import com.example.transportation.transportation.enums.EvtolModel;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.repositories.EvtolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvtolConfig {

    private static final Logger log = LoggerFactory.getLogger(EvtolConfig.class);

    @Bean
    CommandLineRunner initDatabase(EvtolRepository repository) {

        return args -> {

            log.info("Preloading " + repository.save(new Evtol("78410808109", EvtolModel.Lightweight, 100)));
            log.info("Preloading " + repository.save(new Evtol("78410808129", EvtolModel.Lightweight, 100)));
            log.info("Preloading " + repository.save(new Evtol("55566677788", EvtolModel.Heavyweight, 150)));
            log.info("Preloading " + repository.save(new Evtol("12345678901", EvtolModel.Lightweight, 80)));
            log.info("Preloading " + repository.save(new Evtol("45678901234", EvtolModel.Middleweight, 110)));
            log.info("Preloading " + repository.save(new Evtol("98765431109", EvtolModel.Heavyweight, 170)));
            log.info("Preloading " + repository.save(new Evtol("11223346556", EvtolModel.Lightweight, 95)));
            log.info("Preloading " + repository.save(new Evtol("78901239567", EvtolModel.Middleweight, 130)));
            log.info("Preloading " + repository.save(new Evtol("34567899123", EvtolModel.Heavyweight, 180)));
            log.info("Preloading " + repository.save(new Evtol("98765437190", EvtolModel.Lightweight, 85)));
            log.info("Preloading " + repository.save(new Evtol("56789013345", EvtolModel.Middleweight, 140)));
            log.info("Preloading " + repository.save(new Evtol("65432105876", EvtolModel.Heavyweight, 190)));
        };
    }
}
