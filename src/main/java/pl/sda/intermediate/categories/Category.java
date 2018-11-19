package pl.sda.intermediate.categories;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Category implements Serializable {
    private static final Long serialVersionUID = 42L;
    private Integer id;
    private Integer parentId;
    private Integer depth;
    private String name;


}
