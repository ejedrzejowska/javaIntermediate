package pl.sda.intermediate.others;

import lombok.Data;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class CollectionComparisonResult<A, B> {
    private LinkedHashSet<A> onlyInFirst = new LinkedHashSet<>();
    private LinkedHashSet<B> onlyInSecond = new LinkedHashSet<>();
    private Map<A, B> common = new LinkedHashMap<>();

    public boolean isSame() {
        if (onlyInFirst.size() != onlyInSecond.size()) {
            return false;
        }
        return false;
    }
}
