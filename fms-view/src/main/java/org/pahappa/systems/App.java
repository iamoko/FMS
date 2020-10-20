package org.pahappa.systems;

import okhttp3.*;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"apiUsername\": \"amoko\",\n    \"apiPassword\": \"d85518cb49\",\n    \"amount\": \"1000\",\n    \"phoneNumber\": \"256705531898\",\n    \"description\": \"This is Johns Food allowance\"\n}");
        Request request = new Request.Builder()
                .url("http://172.105.72.111:8080/payment-gateway/api/Payments/MakeWithdraw")
                .method("POST", body)
                .addHeader("Content-transfer-encoding", "text")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());

    }
}
