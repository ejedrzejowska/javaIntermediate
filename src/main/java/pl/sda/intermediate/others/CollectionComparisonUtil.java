package pl.sda.intermediate.others;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CollectionComparisonUtil {

    public static <A, B> CollectionComparisonResult<A, B> compareCollections(Collection<A> collectionA, Collection<B> collectionB, CollectionComparisonComparator<A, B> comparator) {
        CollectionComparisonResult<A, B> result = new CollectionComparisonResult<>();
        result.setOnlyInFirst(collectionA.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new)));
        result.setOnlyInSecond(collectionB.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new)));
        return result;
    }


    public static <ELEMENT, KEY> CollectionComparisonResult<ELEMENT, ELEMENT> compareCollections(Collection<ELEMENT> collectionA, Collection<ELEMENT> collectionB, Function<ELEMENT, KEY> keyProvider) {
        throw new NotImplementedException("Do dzieła!"); //todo
    }
}
