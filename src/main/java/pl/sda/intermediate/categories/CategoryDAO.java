package pl.sda.intermediate.categories;

import lombok.Getter;
import pl.sda.intermediate.DataSourceProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class CategoryDAO {
    private static CategoryDAO instance;
    @Getter
    private List<Category> categoryList;
    public static final String CATEGORIES_DATA_TXT = "c:/projects/categories.txt";
    File file = new File(CATEGORIES_DATA_TXT);

    private CategoryDAO() {
//        categoryList = initializeCategories();  //we only need to initialize once to get everything into DB!
//        try (FileOutputStream fos = new FileOutputStream(file);
//             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(categoryList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        categoryList = initializeCategoryList();
    }

    private List<Category> initializeCategoryList(){
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "select * from categories";
        try{
            connection = DataSourceProvider.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Integer parentId = null;
                if (resultSet.getObject("parentId") != null){
                    parentId = resultSet.getInt("parentId") - 1;
                }
                Category category = Category.builder()
                        .id(resultSet.getInt("id") - 1)
                        .name(resultSet.getString("name"))
                        .depth(resultSet.getInt("depth"))
                        .parentId(parentId)
                        .build();
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceProvider.closeRequest(connection, statement, resultSet);
        }
        return categories;
    }

    private List<Category> initializeCategories() {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "insert into categories (id, name, depth, parentId) values (?, ?, ?, ?)";
        try {
            connection = DataSourceProvider.getConnection();
            List<String> strings = Files.readAllLines(Paths.get(
                    this.getClass().getClassLoader().getResource("kategorie2.txt").toURI()), Charset.forName("UNICODE"));
            List<Category> categories = new ArrayList<>();
            int counter = 1;
            for (String line : strings) {
                String name = line.trim();
                int depth = calculateDepth(line);
                categories.add(Category.builder()
                        .name(name)
                        .id(counter++)
                        .depth(depth)
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
            for (Category category : categories) {
//                Field privateParenId = Category.class.getDeclaredField("parentId"); //refleksja
//                privateParenId.setAccessible(true);
                statement = connection.prepareStatement(query);
                statement.setInt(1, category.getId());
                statement.setString(2,category.getName());
                statement.setInt(3, category.getDepth());
//                if(privateParenId.get(category) != null) {
                if(category.getParentId() != null){
                    statement.setInt(4, category.getParentId());
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
                statement.executeUpdate();
            }
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceProvider.closeRequest(connection, statement);
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

    public static CategoryDAO getInstance() {
        if (instance == null) {
            synchronized (CategoryDAO.class) {
                if (instance == null) {
                    instance = new CategoryDAO();
                }
            }
        }
        return instance;
    }


}
