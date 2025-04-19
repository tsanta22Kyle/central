package org.accdatabase.central.model;

import jdk.jfr.Name;
import lombok.*;

@AllArgsConstructor@Getter@NoArgsConstructor@ToString@EqualsAndHashCode@Setter
public class PointOfSale {
    private String id;
    private String name;
    private String apiKey;
    private String apiUrl;
}
