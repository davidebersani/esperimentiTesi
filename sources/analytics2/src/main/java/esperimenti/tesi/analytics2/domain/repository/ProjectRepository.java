package esperimenti.tesi.analytics2.domain.repository;

import esperimenti.tesi.analytics2.domain.model.ViewsDetails;

import java.util.List;

public interface ProjectRepository {
    public List<ViewsDetails> getProjectAnalytics(Integer id);
}
