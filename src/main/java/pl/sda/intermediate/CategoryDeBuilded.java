package pl.sda.intermediate;

public class CategoryDeBuilded {
    private Integer id;
    private Integer parentId;
    private Integer depth;
    private String name;

    @java.beans.ConstructorProperties({"id", "parentId", "depth", "name"})
    CategoryDeBuilded(Integer id, Integer parentId, Integer depth, String name) {
        this.id = id;
        this.parentId = parentId;
        this.depth = depth;
        this.name = name;
    }

    public static CategoryDeBuildedBuilder builder() {
        return new CategoryDeBuildedBuilder();
    }


    public static class CategoryDeBuildedBuilder {
        private Integer id;
        private Integer parentId;
        private Integer depth;
        private String name;

        CategoryDeBuildedBuilder() {
        }

        public CategoryDeBuildedBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CategoryDeBuildedBuilder parentId(Integer parentId) {
            this.parentId = parentId;
            return this;
        }

        public CategoryDeBuildedBuilder depth(Integer depth) {
            this.depth = depth;
            return this;
        }

        public CategoryDeBuildedBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryDeBuilded build() {
            return new CategoryDeBuilded(id, parentId, depth, name);
        }

        public String toString() {
            return "CategoryDeBuilded.CategoryDeBuildedBuilder(id=" + this.id + ", parentId=" + this.parentId + ", depth=" + this.depth + ", name=" + this.name + ")";
        }
    }
}
