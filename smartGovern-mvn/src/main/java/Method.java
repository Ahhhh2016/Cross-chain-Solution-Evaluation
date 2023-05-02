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
 *
 * @author Yixuan Liu
 * @date 2023/03/14
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
     * Query data
     */
    @Transaction
    public String queryData(final Context ctx, String sno) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (state.isEmpty()) {
            System.out.println("No data found with key" + sno);
        }
        return state;
    }

    /**
     * Data upchain
     */
    @Transaction
    public String createData(final Context ctx, String sno, String sname, String sex, String age, String college) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (state.isEmpty()) {
            System.out.println("Before upchain data, no data found with key" + sno);
            QueryResult result = new QueryResult();
            result.setSno(sno);
            result.setSname(sname);
            result.setAge(age);
            result.setCollege(college);
            result.setSex(sex);
            stub.putStringState(sno, genson.serialize(result));
            return genson.serialize(result);
        } else {
            System.out.println("There is already data on the chain whose key is：" + sno + ". Upchain fails.");
            return null;
        }
    }


    /**
     * Update data
     */
    @Transaction
    public String updateData(final Context ctx, String sno, String sname, String sex, String age, String college) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (!state.isEmpty()) {
            System.out.println("Before data modification, find the data whose key is：" + sno);
            QueryResult result = new QueryResult();
            result.setSno(sno);
            result.setSname(sname);
            result.setAge(age);
            result.setCollege(college);
            result.setSex(sex);
            stub.putStringState(sno, genson.serialize(result));
            return genson.serialize(result);
        } else {
            System.out.println("Before data modification, not find the data whose key is：" + sno);
            return null;
        }
    }

    /**
     * Delete data
     */
    @Transaction
    public String deleteData(final Context ctx, String sno) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(sno);
        if (state.isEmpty()) {
            System.out.println("No data found with key " + sno);
            return "false";
        } else {
            stub.delState(sno);
            return "true";
        }
    }

    /**
     * Trace the source
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






























































