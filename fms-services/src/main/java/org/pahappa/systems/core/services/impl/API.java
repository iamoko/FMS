package org.pahappa.systems.core.services.impl;

import okhttp3.*;
import java.io.IOException;

public class API {
    private static String username = "amoko";
    private static String password = "d85518cb49";

    public static int APIDeposit(int amount) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"apiUsername\": \"" + username + "\",\n    \"apiPassword\": \"" + password + "\",\n    \"amount\": \"" + amount + "\",\n    \"phoneNumber\": \"256755170341\",\n    \"description\": \"Adding cash to company's PGW account\"\n}");
        Request request = new Request.Builder()
                .url("http://172.105.72.111:8080/payment-gateway/api/Payments/MakeDeposit")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        return response.code();
    }

    public static int APIWithdraw(int amount, String phone, String name) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"apiUsername\": \"" + username + "\",\n    \"apiPassword\": \"" + password + "\",\n    \"amount\": \"" + amount + "\",\n    \"phoneNumber\": \"" + phone + "\",\n    \"description\": \"This is " + name + "'s Food allowance\"\n}");
        Request request = new Request.Builder()
                .url("http://172.105.72.111:8080/payment-gateway/api/Payments/MakeWithdraw")
                .method("POST", body)
                .addHeader("Content-transfer-encoding", "text")
                .build();
        Response response = client.newCall(request).execute();
        return response.code();

    }
}
