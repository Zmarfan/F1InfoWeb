package f1_Info.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

public class BulkOfWork {
    final List<IQueryData<Void>> mQueryDatas = new LinkedList<>();

    public BulkOfWork() {
    }

    public BulkOfWork(final List<? extends IQueryData<Void>> queryDatas) {
        add(queryDatas);
    }

    public void add(final List<? extends IQueryData<Void>> queryDatas) {
        mQueryDatas.addAll(queryDatas);
    }

    public List<List<IQueryData<Void>>> getQueryDatas() {
        return mQueryDatas
            .stream()
            .collect(Collector.of(
                ArrayList::new,
                (accumulator, item) -> {
                    for (List<IQueryData<Void>> iQueryData : accumulator) {
                        if (iQueryData.get(0).getClass() == item.getClass()) {
                            iQueryData.add(item);
                            return;
                        }
                    }
                    accumulator.add(new ArrayList<>(List.of(item)));
                },
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                }
            ));
    }
}
