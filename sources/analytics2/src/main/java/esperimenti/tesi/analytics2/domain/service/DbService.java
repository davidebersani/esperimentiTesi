package esperimenti.tesi.analytics2.domain.service;

import esperimenti.tesi.analytics2.domain.model.ViewsDetails;

import java.util.List;

public interface DbService {
    public List<ViewsDetails> getProjectAnalytics(Integer id);
}
