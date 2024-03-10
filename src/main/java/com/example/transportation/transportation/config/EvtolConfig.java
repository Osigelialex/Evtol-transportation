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

            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Lightweight, 100)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Lightweight, 100)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Lightweight, 100)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Lightweight, 100)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Middleweight, 200)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Middleweight, 200)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Middleweight, 200)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Middleweight, 200)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Middleweight, 200)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Cruiserweight, 400)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Cruiserweight, 400)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Cruiserweight, 400)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Cruiserweight, 400)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Cruiserweight, 400)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Heavyweight, 500)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Heavyweight, 500)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Heavyweight, 500)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Heavyweight, 500)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Heavyweight, 500)));
            log.info("Preloading " + repository.save(new Evtol(EvtolModel.Heavyweight, 500)));

        };
    }
}
