package by.it.academy.blockchain.TEST;

import by.it.academy.blockchain.RSA.BCKeysFactoryUtil;
import by.it.academy.blockchain.RSA.FileWriterUtil;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.KeyCrypter;
import org.bitcoinj.params.MainNetParams;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECPoint;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static org.apache.commons.codec.digest.DigestUtils.sha256;
import static org.bitcoinj.core.NetworkParameters.*;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
//        BCKeysFactoryUtil BCKeysFactoryUtil = new BCKeysFactoryUtil();
//        FileWriterUtil fileWriterUtil = new FileWriterUtil();
//
//        KeyPair keyPair = BCKeysFactoryUtil.generateKeyPair();
//        String privateKey = BCKeysFactoryUtil.getBCPrivateKey(keyPair);
//        String publicKey = BCKeysFactoryUtil.getBCPublicKey(keyPair);
//        System.out.println("Public key!!! " + publicKey);
//
//        String newPublicKey = BCKeysFactoryUtil.computeBCPubKeyInSHA256(publicKey);
//        System.out.println("Public key 2!!! " + newPublicKey);
//        System.out.println("Private key!!! " + privateKey);
//            fileWriterUtil.writeKeyToFile("vpr@mail.ru", privateKey);

        String message = "Unless required by applicable law or agreed to in writing, software\n" +
                "  distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "  See the License for the specific language governing permissions and\n" +
                "  limitations under the License.";

        ECKey keyPair = new ECKey();
        String privateKey = keyPair.getPrivateKeyAsHex();
        String publicKey = keyPair.getPublicKeyAsHex();

        System.out.println(privateKey);
        System.out.println(publicKey);

        Sha256Hash hash = Sha256Hash.of(message.getBytes());
        System.out.println(hash.toString());

        ECKey.ECDSASignature signature = keyPair.sign(hash);

        ECPoint ecPoint =
        ECKey key = ECKey.fromPublicOnly(publicKey.getBytes());
        System.out.println(key.verify(hash, signature));

    }

}
