package pl.sda.intermediate.others;

@FunctionalInterface
public interface CollectionComparisonComparator<A, B> {
    boolean isEqual(A elementA, B elementB);
}
