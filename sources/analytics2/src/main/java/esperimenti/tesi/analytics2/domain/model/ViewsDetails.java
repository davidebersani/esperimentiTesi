package esperimenti.tesi.analytics2.domain.model;

import com.influxdb.annotations.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ViewsDetails {
    @Column(name="user")
    private String user;
    @Column(name="_value")
    private Double views;
}
