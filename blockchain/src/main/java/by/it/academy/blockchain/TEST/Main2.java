package by.it.academy.blockchain.TEST;

import by.it.academy.blockchain.RSA.RSAGenUtil;
import by.it.academy.blockchain.entity.Transaction;
import by.it.academy.blockchain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;

public class Main2 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
//
//
//        KeyPair keyPair2 = RSAGenUtil.generateKeyPair();
//        PrivateKey aPrivate2 = RSAGenUtil.getPrivate(keyPair2);
//        PublicKey aPublic2 = RSAGenUtil.getPublic(keyPair2);
//
//        String message = "Unless required by applicable law or agreed to in writing, software\n" +
//                "  distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
//                "  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
//                "  See the License for the specific language governing permissions and\n" +
//                "  limitations under the License.";
//
//        //генерим
//        KeyPair keyPair = RSAGenUtil.generateKeyPair();
//        PrivateKey aPrivate = RSAGenUtil.getPrivate(keyPair);
//        PublicKey aPublic = RSAGenUtil.getPublic(keyPair);
//
//        String privateKey = RSAGenUtil.stringPrivateKey(aPrivate);
//        String publicKey = RSAGenUtil.stringPublicKey(aPublic);
//
//        // смотрим ключи
//        System.out.println("Private key: " + privateKey);
//        System.out.println("Public key: " + publicKey);
//
//        // транзакция
//        Transaction transaction = new Transaction();
//        transaction.setSenderPublicKey(publicKey);
//        transaction.setReceiverPublicKey(RSAGenUtil.stringPublicKey(aPublic2));
//        transaction.setValue(10.2);
//        transaction.setComission(2.4);
//        transaction.setDate(LocalDateTime.now());
//        transaction.setStatus(TransactionStatus.NOT_CONFIRMED.getName());
//
//        String txId = RSAGenUtil.hashTransaction(transaction);
//        // сетим ID
//        transaction.setId(txId);
//        System.out.println("TX hash: " + transaction.getId());
//
//        // читаем ключи
//        PrivateKey privateFromString = RSAGenUtil.getPrivateFromString(privateKey);
//        PublicKey publicFromString = RSAGenUtil.getPublicFromString(publicKey);
//
//        String signature = RSAGenUtil.getSignature(privateFromString, txId);
//        // подписываем и сетим подпись
//        transaction.setSignature(signature);
//        System.out.println("TX signature: " + signature);
//
//        // проверяем хеши на модификацию
//        System.out.println("Check tx hash for modification: " + RSAGenUtil.checkTransactionHash(transaction));
//
//        // верифицируем цифр подпись
//        boolean b = RSAGenUtil.verifySignature(publicFromString, txId, signature);
//        System.out.println(b);

        BigDecimal bigDecimal = new BigDecimal("2.245");
        BigDecimal bigDecimal2 = new BigDecimal("2.245");
        BigDecimal sum = bigDecimal.add(bigDecimal2);
        Double s = sum.doubleValue();
    }
}
