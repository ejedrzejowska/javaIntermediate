package pl.sda.intermediate.categories;

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
    private CategoryState state;

    public String getText(){
        return name;
    }

    public CategoryState getState(){
        return state;
    }

    public String getParent(){
        return parentId == null ? "#" : parentId.toString();
    }
}
