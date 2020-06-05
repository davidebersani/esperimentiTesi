package esperimenti.tesi.analytics2.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ProjectAnalytics {
    private List<ViewsDetails> views;
    private Integer total;
}
