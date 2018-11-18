package pl.sda.intermediate.categories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryState {
    private boolean open;
    private boolean selected;
    private boolean disabled;
}
