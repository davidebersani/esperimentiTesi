package esperimenti.tesi.analytics2.domain.service;

import esperimenti.tesi.analytics2.domain.exception.BadResultDimensionsException;

public interface DbService {
    public Integer getViewCount() throws BadResultDimensionsException;
}
