package f1_Info.database;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
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
}
