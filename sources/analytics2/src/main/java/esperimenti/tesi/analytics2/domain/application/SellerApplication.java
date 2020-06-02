package esperimenti.tesi.analytics2.domain.application;

import esperimenti.tesi.analytics2.domain.model.ProjectAnalytics;
import esperimenti.tesi.analytics2.domain.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerApplication {

    @Autowired
    DbService dbService;

    public ProjectAnalytics getAnalyticsOfProject(Integer projectId) {
        ProjectAnalytics stats = new ProjectAnalytics();
        stats.setViews(dbService.getProjectAnalytics(projectId));
        stats.setTotal(stats.getViews().stream().mapToInt(value -> value.getViews().intValue()).sum());

        return stats;
    }
}
