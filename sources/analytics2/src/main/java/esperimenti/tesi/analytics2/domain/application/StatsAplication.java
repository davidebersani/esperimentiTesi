package esperimenti.tesi.analytics2.domain.application;

import esperimenti.tesi.analytics2.domain.exception.BadResultDimensionsException;
import esperimenti.tesi.analytics2.domain.model.ViewStats;
import esperimenti.tesi.analytics2.domain.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatsAplication {

    @Autowired
    DbService dbService;

    @Autowired


    public ViewStats getViewStats() throws BadResultDimensionsException {
        Integer count = dbService.getViewCount();

        return ViewStats.builder().countView(count).build();
    }
}
