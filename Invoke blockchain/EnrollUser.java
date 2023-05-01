package com.example.governapispringboot.utils;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import javax.management.openmbean.InvalidKeyException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class EnrollUser {
    //Take an existing certificate, so there is no need to connect to the CA
    public static void main(String[] args) throws Exception {
        register2("org2","Org2");
    }

    public static void register2(String org,String Org) throws CertificateException, IOException {

        //      Path NETWORK_CONFIG_PATH = Paths.get("src", "main", "resources", "connection.json");
        Path credentialPath = Paths.get("./src/main/resources/organizations",
                "peerOrganizations", org+".example.com", "users", "User1@"+org+".example.com", "msp");
        X509Certificate certificate = null;
        PrivateKey privateKey = null;
        Gateway gateway = null;
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("./src/main/resources/wallet"));
        Path certificatePath = credentialPath.resolve(Paths.get("signcerts", "User1@org2.example.com-cert.pem"));
        certificate = readX509Certificate(certificatePath);

        Path privateKeyPath = credentialPath.resolve(Paths.get("keystore", "priv_sk"));
        privateKey = getPrivateKey(privateKeyPath);

        wallet.put("user"+Org,Identities.newX509Identity(Org+"MSP",certificate,privateKey));

    }
    private static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void register(String org,String portCA,String userName,String adminName) throws Exception {

        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        props.put("pemFile",
                "../organizations/peerOrganizations/"+org+".example.com/ca/ca."+org+".example.com-cert.pem");
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance("https://192.168.71.129:"+portCA, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

        // Check to see if we've already enrolled the user.
        if (wallet.get(userName) != null) {
            System.out.println("An identity for the user \""+userName+"\" already exists in the wallet");
            return;
        }

        X509Identity adminIdentity = (X509Identity)wallet.get(adminName);
        if (adminIdentity == null) {
            System.out.println(adminName+" needs to be enrolled and added to the wallet first");
            return;
        }

        Enrollment enrollment = caClient.enroll(userName, "user1pw");
        Identity user = Identities.newX509Identity(org+"MSP", enrollment);
        wallet.put(userName, user);
        System.out.println("Successfully enrolled user \""+userName+"\" and imported it into the wallet");
    }
}
