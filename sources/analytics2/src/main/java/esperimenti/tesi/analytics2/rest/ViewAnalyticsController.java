package esperimenti.tesi.analytics2.rest;

import esperimenti.tesi.analytics2.domain.model.ViewStats;
import esperimenti.tesi.analytics2.domain.application.StatsAplication;
import esperimenti.tesi.analytics2.domain.exception.BadResultDimensionsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ViewAnalyticsController {

    @Autowired
    StatsAplication statsAplication;

    @GetMapping("/stat")
    public ResponseEntity<ViewStats> getViewStat() {
        try {
            return ResponseEntity.ok(statsAplication.getViewStats());
        } catch (BadResultDimensionsException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
