package esperimenti.tesi.analytics2.domain.service;

import esperimenti.tesi.analytics2.domain.model.ViewOfProject;

import java.util.List;

public interface DbService {
    public List<ViewOfProject> getViewOfProject(Integer id);
}
