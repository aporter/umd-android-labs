package course.labs.alarmslab;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

public class AlarmTweetService extends Service {

    // Notification ID to allow for future updates
    private static final String TAG = "AlarmTweetService";

    // TODO -- Pick one of the following user accounts. Change the
    // CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, and ACCESS_TOKEN_SECRET
    // to the values for your account

    // http://www.twitter.com/CMSC436Tester

    // Consumer key: S2xDEU4mJyWKEsMEN9c3g
    // Consumer secret: Pz7gH6S4ReftwuKVYEmkVW6TqImoIgbEWE9AFeDBWRc
    //
    // Access Token: 2151769766-ce3mtIH2bDEY8PZy73BKULWFfBqVnx8xHWeYvkY
    // Access Token Secret: W7qEJxvEiWhqQmCuvFbKK6emg0cMqf8SxuABc3N33tz5w

    // http://www.twitter.com/CMSC436Tester2

    // Consumer key: kCgvgTnJ5nDuJmZrV5kTXA
    // Consumer secret: B4qDeJOClLCw67w7G08B4FUXBpS0P3qkX0WSbo0Bjgo
    //
    // Access Token: 2151777866-VTX8oVguPz6xoTqPpQnDRqJJ7DhYRgkowCn9MCv
    // Access Token Secret: H3M8VGanx6euQS336BLqItB7saTy7K89CpxlQ4PqpTkQC
    //

    // http://www.twitter.com/CMSC436Tester3

    // Consumer key: qUkfkuK8xKhxGq3BztWlLw
    // Consumer secret: cck2aY4390u8Z9hFRfE7TSRYrATUQG0MkB61xEErDg
    //
    // Access Token: 2151782420-mHjtmHTQiE4L6nDgIhMbzfDYtjE6JFNK2Bm04nq
    // Access Token Secret: Wy9SK20WbZnDjXlxBH1gxQRKNCz9Pi4EoMCyJPGrjjJVY

    // Consumer Key
    private final static String CONSUMER_KEY = "S2xDEU4mJyWKEsMEN9c3g";

    // Consumer Secret
    private final static String CONSUMER_SECRET = "Pz7gH6S4ReftwuKVYEmkVW6TqImoIgbEWE9AFeDBWRc";

    // Access Token
    private static String ACCESS_TOKEN = "2151769766-ce3mtIH2bDEY8PZy73BKULWFfBqVnx8xHWeYvkY";

    // Access Token Secret
    private static String ACCESS_TOKEN_SECRET = "W7qEJxvEiWhqQmCuvFbKK6emg0cMqf8SxuABc3N33tz5w";

    // networking variables
    private static SecureRandom mRandom;

    public AlarmTweetService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getExtras() != null){

            mRandom = new SecureRandom();

            new NetworkingTask().execute(intent.getExtras().getString(AlarmCreateActivity.TWEET_STRING));

        }

        return START_STICKY;
    }

    public class NetworkingTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... args) {
            try {
                return postTweet(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "COULDN'T GET IT");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(getApplicationContext(),
                    "The Tweet was submitted with result:" + result,
                    Toast.LENGTH_LONG).show();
        }

    }

    private static boolean postTweet(String status) throws IOException {
        HttpsURLConnection connection = null;
        String nonce = newNonce();
        String timestamp = timestamp();

        try {

            // URL for updating the Twitter status
            URL url = new URL(
                    "https://api.twitter.com/1.1/statuses/update.json");

            //TODO - make connection into an HttpsUrlConnection for url above
            connection = (HttpsURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "TwitterNetworkingLab");
            connection.setRequestProperty("Authorization", "OAuth "
                    + "oauth_consumer_key=\"" + CONSUMER_KEY + "\", "
                    + "oauth_nonce=\"" + nonce + "\", " + "oauth_signature=\""
                    + signature(status, nonce, timestamp) + "\", "
                    + "oauth_signature_method=\"HMAC-SHA1\", "
                    + "oauth_timestamp=\"" + timestamp + "\", "
                    + "oauth_token=\"" + ACCESS_TOKEN + "\", "
                    + "oauth_version=\"1.0\"");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            Log.d(TAG, connection.getRequestProperties().toString());

            // This POST request needs a body
            // This is how you write to the body

            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());

            // Twitter status is added here.

            out.write("status=" + URLEncoder.encode(status, "UTF-8"));

            out.flush();
            out.close();

            String test = "status=" + URLEncoder.encode(status, "UTF-8");
            Log.i(TAG, test);

            JSONObject response = new JSONObject(read(connection));

            if (response != null) {
                Log.d(TAG, "The tweet seems to have been posted successfully!");
            }

            return response != null;

        } catch (MalformedURLException e) {
            throw new IOException("Invalid URL.", e);
        } catch (JSONException e) {
            throw new IOException("Invalid response from Twitter", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String read(HttpsURLConnection connection) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = new String();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
        } catch (IOException e) {
            try {
                Log.i(TAG,
                        "Error reponse from twitter: "
                                + connection.getResponseMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stringBuffer.append("Failed");
        }
        return stringBuffer.toString();
    }

    private static String newNonce() {
        byte[] random = new byte[32];
        mRandom.nextBytes(random);

        String nonce = Base64.encodeToString(random, Base64.NO_WRAP);
        nonce = nonce.replaceAll("[^a-zA-Z0-9\\s]", "");
        return nonce;

    }

    private static String timestamp() {
        Long time = System.currentTimeMillis() / 1000L;
        return time.toString();
    }

    private static String signature(String status, String nonce,
                                    String timestamp) {
        String output = "";
        // Obviously you can use a TreeMap with concurrency to implement this,
        // but this way you can see what is going on
        try {
            // Gather all parameters used for the status post
            String signatureBaseString = "";
            String parameterString = "";
            String signingKey = "";
            String signature = "";

            String httpMethod = "POST";
            String baseURL = "https://api.twitter.com/1.1/statuses/update.json";

            String consumerKey = CONSUMER_KEY;
            String encMethod = "HMAC-SHA1";
            String token = ACCESS_TOKEN;
            String version = "1.0";

            String kStatus = "status";
            String kKey = "oauth_consumer_key";
            String kNonce = "oauth_nonce";
            String kSigMeth = "oauth_signature_method";
            String kTimestamp = "oauth_timestamp";
            String kToken = "oauth_token";
            String kVersion = "oauth_version";

            // encode the parameters put into the http request header separately
            consumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            nonce = URLEncoder.encode(nonce, "UTF-8");
            encMethod = URLEncoder.encode(encMethod, "UTF-8");
            timestamp = URLEncoder.encode(timestamp, "UTF-8");
            token = URLEncoder.encode(token, "UTF-8");
            version = URLEncoder.encode(version, "UTF-8");
            status = URLEncoder.encode(status, "UTF-8");
            status = status.replace("+", "%20"); // URLEncoder encodes " " to
            // "+", which is incorrect
            // for twitter encoding

            kKey = URLEncoder.encode(kKey, "UTF-8");
            kNonce = URLEncoder.encode(kNonce, "UTF-8");
            kSigMeth = URLEncoder.encode(kSigMeth, "UTF-8");
            kTimestamp = URLEncoder.encode(kTimestamp, "UTF-8");
            kToken = URLEncoder.encode(kToken, "UTF-8");
            kVersion = URLEncoder.encode(kVersion, "UTF-8");
            kStatus = URLEncoder.encode(kStatus, "UTF-8");

            // append the parameters together with = and &
            parameterString = kKey + "=" + consumerKey + "&" + kNonce + "="
                    + nonce + "&" + kSigMeth + "=" + encMethod + "&"
                    + kTimestamp + "=" + timestamp + "&" + kToken + "=" + token
                    + "&" + kVersion + "=" + version + "&" + kStatus + "="
                    + status;

            // build the signature base string
            signatureBaseString += httpMethod;
            signatureBaseString += "&";
            baseURL = URLEncoder.encode(baseURL, "UTF-8");
            signatureBaseString += baseURL;
            signatureBaseString += "&";
            parameterString = URLEncoder.encode(parameterString, "UTF-8");
            signatureBaseString += parameterString;
            Log.d(TAG, signatureBaseString);

            // generate the HMAC-SHA1 signing key with the application and
            // account secrets
            signingKey = URLEncoder.encode(CONSUMER_SECRET, "UTF-8") + "&"
                    + URLEncoder.encode(ACCESS_TOKEN_SECRET, "UTF-8");

            // this should be self explanatory
            byte[] bytes = signingKey.getBytes();
            Key key = new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] signatureBytes = mac.doFinal(signatureBaseString.getBytes());
            signature = Base64.encodeToString(signatureBytes, Base64.NO_WRAP);
            signature = URLEncoder.encode(signature, "UTF-8");

            output = signature;

        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Error encoding parameters");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.d(TAG, "Error encrypting signature");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, "Invalid algorithm for signature");
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}