package pl.sda.intermediate.categories;

import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCategoryDAO {
    private static InMemoryCategoryDAO instance;
    @Getter
    private List<Category> categoryList;
    public static final String CATEGORIES_DATA_TXT = "c:/projects/categories.txt";
    File file = new File(CATEGORIES_DATA_TXT);

    private InMemoryCategoryDAO() {
        categoryList = initializeCategories();
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(categoryList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Category> initializeCategories() {
        try {
            List<String> strings = Files.readAllLines(Paths.get(
                    this.getClass().getClassLoader().getResource("kategorie.txt").toURI()), Charset.forName("UNICODE"));
            List<Category> categories = new ArrayList<>();
            int counter = 1;
            for (String line : strings) {
                categories.add(Category.builder()
                        .name(line.trim())
                        .id(counter++)
                        .depth(calculateDepth(line))
                        .build());
            }
            Map<Integer, List<Category>> categoryMap = new HashMap<>();
            for (Category category : categories) {
                if (categoryMap.containsKey(category.getDepth())) {
                    categoryMap.get(category.getDepth()).add(category);
                } else {
                    List<Category> cats = new ArrayList<>();
                    cats.add(category);
                    categoryMap.put(category.getDepth(), cats);
                }
            }
            populateParentId(categoryMap, 0);
            return categories;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void populateParentId(Map<Integer, List<Category>> categoryMap, Integer depth) {
        if (!categoryMap.containsKey(depth)) {
            return;
        }
        List<Category> children = categoryMap.get(depth);
        for (Category child : children) {
            if (depth != 0) {
                List<Category> potentialParents = categoryMap.get(depth - 1);
                int parentId = 0;
                for (Category potentialParent : potentialParents) {
                    if (potentialParent.getId() < child.getId() && parentId < potentialParent.getId()) {
                        parentId = potentialParent.getId();
                    }
                }
                if (parentId == 0) throw new RuntimeException();
                child.setParentId(parentId);
            }
        }
        populateParentId(categoryMap, ++depth);
    }

    private int calculateDepth(String line) {
        if (line.startsWith(" ")) {
            return line.split("\\S")[0].length();
        }
        return 0;
    }

    public static InMemoryCategoryDAO getInstance() {
        if (instance == null) {
            synchronized (InMemoryCategoryDAO.class) {
                if (instance == null) {
                    instance = new InMemoryCategoryDAO();
                }
            }
        }
        return instance;
    }
}
