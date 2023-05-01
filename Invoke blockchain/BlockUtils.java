package com.example.governapispringboot.utils;
import com.example.governapispringboot.entity.Certificate;
import com.example.governapispringboot.entity.FilesListofUser;
import com.owlike.genson.Genson;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import org.hyperledger.fabric.gateway.*;

public class BlockUtils {
    Path walletpath= Paths.get("./src/main/resources","wallet");
    Genson genson=new Genson();
    Wallet wallet= Wallets.newFileSystemWallet(walletpath);
    String p1="org1.example.com";
    String p2="connection-org1.json";
    String identity="userOrg1";
    String channelName="mychannel";
    String chaincodeName="govern1";
    String fileID;

    public BlockUtils() throws IOException {
    }

    public Contract start() throws IOException {
        Path ccp= Paths.get("src/main/resources/organizations","peerOrganizations",p1,p2);
        Gateway.Builder builder=Gateway.createBuilder();
        builder.identity(wallet,identity).networkConfig(ccp).discovery(true);
        Gateway gateway=builder.connect();
        Network mychannel = gateway.getNetwork(channelName);
        Contract contract = mychannel.getContract(chaincodeName);
        return contract;
    }
    //Apply for certificate
    public String  createCertificate(String fileID, String applicantID,String userID, String synopsis) throws IOException, ContractException, InterruptedException, TimeoutException, NoSuchAlgorithmException {
        //String createCertificate(final Context ctx, String fileID, String applicantID,String userID, String synopsis)
//        builder.identity(wallet,identity).networkConfig(ccp).discovery(true);//必须放在方法内 不知道为啥
//        Gateway gateway=builder.connect();
//        Network mychannel = gateway.getNetwork(channelName);
//        Contract contract = mychannel.getContract(chaincodeName);
        Contract contract = start();
        byte[] bytes = contract.submitTransaction("createCertificate", fileID,applicantID,userID,synopsis);
        String overcome=new String((bytes));
        if (overcome.equals("-1")){
            return "-1";
        }else {
            return genson.deserialize(overcome,Certificate.class).toString();
        }
    }

    public String updateFiles(String originalFileID,String newFileID,String userID,String applicantID,String synopsis) throws ContractException, InterruptedException, TimeoutException, IOException {
        //String updateFiles(final Context ctx,String originalFileID,String newFileID,String userID,String applicantID,String synopsis)
        //To modify a file, a historical file ID field needs to be added. API needs to modify mysql
        Contract contract = start();
        byte[] bytes = contract.submitTransaction("updateFiles", originalFileID, newFileID, userID, applicantID, synopsis);
        String overcome=new String(bytes);
        return overcome;
    }
    public Certificate updateCertificate(String fileID,String userID,String synopsis,String applicantID) throws ContractException, InterruptedException, TimeoutException, IOException {
        //String updateCertificate(final Context ctx,String fileID,String userID,String synopsis,String applicantID)
        Contract contract = start();
        byte[] bytes = contract.submitTransaction("updateFiles", fileID, userID, synopsis, applicantID);
        String overcome=new String(bytes);
        if (overcome.equals("-1")){
            return null;
        }else {
            Certificate deserialize = genson.deserialize(overcome, Certificate.class);

            return deserialize;
        }
    }

    public  Certificate serchByFileID(String fileID) throws IOException, ContractException {
        //String searchByFileID(final Context ctx,String fileID)
        Contract contract = start();
        byte[] bytes = contract.evaluateTransaction("searchByFileID", fileID);
        String overcome=new String((bytes));
        System.out.println(overcome);
        if (overcome.equals("-1")){
            return null;
        }else {
            Certificate deserialize = genson.deserialize(overcome, Certificate.class);

            return deserialize;
        }
    }
    public  String serchByUserID(String userID) throws IOException, ContractException {
        //String searchByUserID(final Context ctx,String userID)
        Contract contract = start();
        byte[] bytes = contract.evaluateTransaction("serchByUserID", userID);
        String overcome=new String((bytes));
        if (overcome.equals("-1")){
            return "-1";
        }else {
            return genson.deserialize(overcome, FilesListofUser.class).toString();
        }
    }
    public String delete(String fileID,String userID,String applicantID) throws ContractException, InterruptedException, TimeoutException, IOException {
        //String delete(final Context ctx,String fileID,String userID,String applicantID)
        Contract contract = start();
        byte[] bytes = contract.submitTransaction("delete", fileID, userID, applicantID);
        String overcome=new String(bytes);
        return overcome;
    }
    public String getHistory(String key) throws ContractException, IOException {
        //String getHistory(final Context ctx, String key)
        Contract contract = start();
        byte[] bytes = contract.evaluateTransaction("getHistory", key);
        String overcome=new String(bytes);
        return overcome;
    }





}
