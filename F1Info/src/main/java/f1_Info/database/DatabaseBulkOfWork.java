package f1_Info.database;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class DatabaseBulkOfWork {
    final List<IQueryData<Void>> mQueryDatas = new LinkedList<>();

    public void add(final List<? extends IQueryData<Void>> queryDatas) {
        mQueryDatas.addAll(queryDatas);
    }
}
