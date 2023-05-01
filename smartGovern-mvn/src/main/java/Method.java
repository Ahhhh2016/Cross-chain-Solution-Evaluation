import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 方法
 *
 * @author Wangrihao
 * @date 2023/04/14
 */
@Contract(
        name = "FabricChainCode",
        info = @Info(
                title = "FabricChainCode",
                description = "The hyperlegendary FabricChainCode contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "2@qq.com",
                        name = "W")))
@Default
public class Method implements ContractInterface {
    Genson genson = new Genson();

    /**
     * 查询数据
     */
    @Transaction
    public String queryData(final Context ctx, String sno) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (state.isEmpty()) {
            System.out.println("没有找到key为：" + sno + "的数据");
        }
        return state;
    }

    /**
     * 上链数据
     */
    @Transaction
    public String createData(final Context ctx, String sno, String sname, String sex, String age, String college) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (state.isEmpty()) {
            System.out.println("数据上链前，没有找到key为：" + sno + "的数据");
            QueryResult result = new QueryResult();
            result.setSno(sno);
            result.setSname(sname);
            result.setAge(age);
            result.setCollege(college);
            result.setSex(sex);
            stub.putStringState(sno, genson.serialize(result));
            return genson.serialize(result);
        } else {
            System.out.println("链上已有key为：" + sno + "的数据，上链失败");
            return null;
        }
    }


    /**
     * 更新数据
     */
    @Transaction
    public String updateData(final Context ctx, String sno, String sname, String sex, String age, String college) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (!state.isEmpty()) {
            System.out.println("数据修改前，找到key为：" + sno + "的数据");
            QueryResult result = new QueryResult();
            result.setSno(sno);
            result.setSname(sname);
            result.setAge(age);
            result.setCollege(college);
            result.setSex(sex);
            stub.putStringState(sno, genson.serialize(result));
            return genson.serialize(result);
        } else {
            System.out.println("数据修改前，没有找到key为：" + sno + "的数据");
            return null;
        }
    }

    /**
     * 删除数据
     */
    @Transaction
    public String deleteData(final Context ctx, String sno) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (state.isEmpty()) {
            System.out.println("没有找到key为：" + sno + "的数据");
            return "false";
        } else {
            stub.delState(sno);
            return "true";
        }
    }

    /**
     * 溯源
     */
    @Transaction
    public List<Map<String, Object>> getHistory(final Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        QueryResultsIterator<KeyModification> historyForKey = stub.getHistoryForKey(key);
//        ArrayList<String> arrayList = new ArrayList<>();
        List<Map<String, Object>> historyList = new ArrayList<>();
        for (KeyModification e : historyForKey) {
            Map<String, Object> historyMap = new HashMap<>(3);
            historyMap.put("TXid", e.getTxId());
            historyMap.put("value", e.getStringValue());
            historyMap.put("time", e.getTimestamp().plusSeconds(TimeUnit.HOURS.toSeconds(8)));
            historyList.add(historyMap);
//            arrayList.add("TXid:" + e.getTxId() + ",value:{" + e.getStringValue() + "," + e.getTimestamp().plusSeconds(TimeUnit.HOURS.toSeconds(8)) + "}");
        }
        return historyList;
    }

    @Transaction
    public String Test(final Context ctx, String id) {
        return id;
    }


}






























































