package org.pahappa.systems.core.API;

import okhttp3.*;

import java.io.IOException;

public class Withdrawing extends Thread{
    public int amount;
    public String phone, name;
    public Withdrawing(int amount, String phone, String name){
        this.amount = amount;
        this.phone = phone;
        this.name = name;
    }

    @Override
    public void run() {
        APIWithdraw(this.amount, this.phone, this.name);
    }

    public void APIWithdraw(int amount, String phone, String name){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"apiUsername\": \"amoko\",\n    \"apiPassword\": \"d85518cb49\",\n    \"amount\": \"" + amount + "\",\n    \"phoneNumber\": \"" + phone + "\",\n    \"description\": \"This is " + name + "'s Food allowance\"\n}");
        Request request = new Request.Builder()
                .url("http://172.105.72.111:8080/payment-gateway/api/Payments/MakeWithdraw")
                .method("POST", body)
                .addHeader("Content-transfer-encoding", "text")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.code());

    }
}
