package pl.sda.intermediate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private InMemoryCategoryDAO inMemoryCategoryDAO = InMemoryCategoryDAO.getInstance();

    public List<CategoryDTO> filterOutCategories(String searchedText) {
        return filterCategories(searchedText).stream().filter(d -> d.getState().isOpen() != true).collect(Collectors.toList());
    }

    public List<CategoryDTO> filterCategories(String searchedText) {
        List<Category> categoryList = inMemoryCategoryDAO.getCategoryList();
        Map<Integer, CategoryDTO> dtoMap = categoryList.stream()
                .map(c -> buildCategoryDTO(c))
                .collect(Collectors.toMap(k -> k.getId(), v -> v));

        return dtoMap.values()
                .stream()
                .peek(dto -> dto.setParentCat(dtoMap.get(dto.getParentId())))
                .map(dto -> populateStateAndOpenParent(dto, searchedText))
                .collect(Collectors.toList());
    }

    private CategoryDTO populateStateAndOpenParent(CategoryDTO dto, String searchedText) {
        if (searchedText != null && dto.getText().equals(searchedText.trim())) {
            dto.getState().setOpen(true);
            dto.getState().setSelected(true);
            openParent(dto);
        }
        return dto;
    }

    private void openParent(CategoryDTO dto) {
        CategoryDTO parentCat = dto.getParentCat();
        if (parentCat == null) {
            return;
        }
        parentCat.getState().setOpen(true);
        openParent(parentCat);
    }

    private CategoryDTO buildCategoryDTO(Category c) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(c.getId());
        categoryDTO.setParentId(c.getParentId());
        categoryDTO.setDepth(c.getDepth());
        categoryDTO.setText(c.getName());
        categoryDTO.setState(new CategoryState());
        return categoryDTO;
    }
}
