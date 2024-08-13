package com.example.transportation.transportation.seeders;

import com.example.transportation.transportation.enums.EvtolModel;
import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.repositories.EvtolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class EvtolSeeder implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(EvtolSeeder.class);
    private final EvtolRepository evtolRepository;

    public EvtolSeeder(EvtolRepository evtolRepository) {
        this.evtolRepository = evtolRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (evtolRepository.count() > 1) {
            logger.info("Evtols already seeded");
            return;
        }

        List<EvtolModel> evtolModelList = Arrays.asList(EvtolModel.values());

        for (int i = 0; i < 20; i++) {
            Evtol evtol = new Evtol();
            evtol.setSerialNumber("SN" + i);
            evtol.setWeightLimit(new Random().nextInt(500 - 100) + 100);
            evtol.setModel(evtolModelList.get(new Random().nextInt(0, evtolModelList.size() - 1)));
            evtol.setState(EvtolState.IDLE);
            evtolRepository.save(evtol);
        }

        logger.info("Evtols seeded successfully");
    }
}
