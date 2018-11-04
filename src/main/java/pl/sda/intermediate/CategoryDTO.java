package pl.sda.intermediate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private Integer parentId;
    private Integer depth;
    private String name;
    private CategoryDTO parentCat;
    private CatergoryState catergoryState;

}
