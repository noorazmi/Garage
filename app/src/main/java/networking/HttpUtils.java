package networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import networking.models.HTTPRequest;
import networking.models.HTTPResponse;
import networking.models.ValueObject;

/**
 * Created by noor on 22/04/15.
 */
public class HttpUtils {
    private static final String TAG = "HttpUtils";

    private static final int CONNECTION_TIME_OUT = 10 * 1000; //in Milliseconds
    private static final int SOCKET_TIME_OUT = 15 * 1000;

    public static HTTPResponse doGet(HTTPRequest httpRequest) {

        String url = encodeURL(httpRequest.getUrl());
        Logger.i(TAG, "URL: " + url);
        HttpGet httpGet = new HttpGet(url);
        setCommonHeaders(httpGet);
        HttpResponse httpResponse = null;
        try {

            DefaultHttpClient httpClient = getDefaultHttpClient();
            httpResponse = httpClient.execute(httpGet);
        } catch (ClientProtocolException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processHttpResponse(httpResponse, httpRequest.getValueObjectFullyQualifiedName());
    }

    public static HTTPResponse doPost(HTTPRequest httpRequest) {

        String url = encodeURL(httpRequest.getUrl());
        HttpPost httpPost = new HttpPost(url);
        setCommonHeaders(httpPost);
        HttpResponse httpResponse = null;

        try {
            StringEntity se = new StringEntity(httpRequest.getJSONPayload());
            httpPost.setEntity(se);

            Logger.i(TAG, "*****URL:" + url + " | JsonPayload: " + httpRequest.getJSONPayload());

            DefaultHttpClient httpClient = getDefaultHttpClient();
            httpResponse = httpClient.execute(httpPost);

        } catch (ClientProtocolException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processHttpResponse(httpResponse, httpRequest.getValueObjectFullyQualifiedName());
    }

    private static HTTPResponse processHttpResponse(@NonNull HttpResponse httpresponse, String valueObjectFullyQualifiedName) {

        HTTPResponse httpResponse = null;
        InputStream inputStream = null;
        try {
            httpResponse = new HTTPResponse();
            StatusLine statusLine = httpresponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            httpResponse.setHttpStatusCode(statusCode);
            Logger.i(TAG, "****StatusCode: " + statusLine.getStatusCode());

            String result = null;
            HttpEntity entity = httpresponse.getEntity();
            if (entity != null) {
                inputStream = entity.getContent();
                result = readStream(inputStream);
                Logger.i(TAG, "****JSONResponse: " + result);
                httpResponse.setResponseJSONString(result);

            }

            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED) {
                httpResponse.setValueObject(getValueObject(result, valueObjectFullyQualifiedName));
                httpResponse.setError(false);
            } else {
                setErrorMessage(httpResponse);
            }

        } catch (ClientProtocolException | UnsupportedEncodingException e) {
            e.printStackTrace();
            setErrorMessage(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
            setErrorMessage(httpResponse);
        }catch (NullPointerException e){
            e.printStackTrace();
            setErrorMessage(httpResponse);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return httpResponse;
    }


    /**
     * Creates and returns the {@link DefaultHttpClient}. We are setting Connection Timeout and Socket Time out here.
     */
    public static DefaultHttpClient getDefaultHttpClient() {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIME_OUT);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

        return httpClient;
    }

    public static void setCommonHeaders(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader(HttpConstants.HTTP_HEADER_KEY_CONTENT_TYPE, HttpConstants.HTTP_HEADER_VALUE_APPLICATION_JSON);
        httpRequestBase.setHeader(HttpConstants.HTTP_HEADER_KEY_USER_AGENT, System.getProperty(HttpConstants.HTTP_HEADER_VALUE_HTTP_AGENT));
    }

    private static void setErrorMessage(HTTPResponse httpResponse) {
        httpResponse.setError(true);
        httpResponse.setErrorMsg(HttpConstants.HTTP_DEFAULT_ERROR_MESSAGE);
    }

    public static String encodeURL(String url) {
        return url.replaceAll(" ", "%20");
    }

    /*
    *
    * Read InputStream into a String
    */
    public static String readStream(InputStream inputStream) throws IOException {
        //InputStream in = new FileInputStream(new File("C:/temp/test.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        return out.toString();
    }


    /**
     * Returns the JSONString from java object.
     * @param response
     * @return
     */
    public static String getJsonStringFromModel(Object response) {
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(response);
        return jsonString;
    }

    /**
     * Returns IValueObject after populating it using JSONString passed in this method.
     * @param resultJson
     * @param valueObjectFullyQualifiedName Fully qualified name of the class extending the ValueObject interface
     * @return
     */
    public static ValueObject getValueObject(String resultJson, String valueObjectFullyQualifiedName) {

        try {
            Class<? extends ValueObject> valueObjectClass = (Class<? extends ValueObject>) Class.forName(valueObjectFullyQualifiedName);
            return getModelFromJsonString(resultJson, valueObjectClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Parsing was not successful, we are returning null.
        return null;
    }

    /**
     *
     * @param response json string obtained from the server
     * @param valueObjectClass Class of ValueObject model in which we have to fill the data from json string
     * @return ValueObject after populating it with data obtained from json string
     */
    public static ValueObject getModelFromJsonString(String response, Class valueObjectClass) {
        Gson gson = new GsonBuilder().create();
        ValueObject valueObject = (ValueObject) gson.fromJson(response, valueObjectClass);
        return valueObject;
    }


    /**
     * Checks the connectivity of internet
     * @param context
     * @return
     */
    public boolean hasConnectivity(@NonNull Context context) {
        boolean connectivity = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            connectivity = true;
        }
        return connectivity;
    }

}
