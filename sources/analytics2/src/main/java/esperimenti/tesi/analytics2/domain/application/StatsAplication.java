package esperimenti.tesi.analytics2.domain.application;

import esperimenti.tesi.analytics2.domain.model.StatsOfProject;
import esperimenti.tesi.analytics2.domain.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatsAplication {

    @Autowired
    DbService dbService;

    public StatsOfProject getStatsForProject(Integer projectId) {
        StatsOfProject stats = new StatsOfProject();
        stats.setViews(dbService.getViewOfProject(projectId));
        stats.setTotal(stats.getViews().stream().mapToInt(value -> value.getViews().intValue()).sum());

        return stats;
    }
}
