package esperimenti.tesi.analytics2.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsOfProject {
    private List<ViewOfProject> views;
    private Integer total;
}
