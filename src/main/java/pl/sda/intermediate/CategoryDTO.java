package pl.sda.intermediate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private Integer parentId;
    private Integer depth;
    private String text;
    private CategoryDTO parentCat;
    private CategoryState state;

    public String getParent(){
        return parentId == null ? "#" : parentId.toString();
    }
}
