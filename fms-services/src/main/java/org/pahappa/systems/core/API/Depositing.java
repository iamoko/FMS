package org.pahappa.systems.core.API;

import okhttp3.*;

import java.io.IOException;

public class Depositing extends Thread{
    public int amount;
    public Depositing(int amount){
        this.amount = amount;
    }

    @Override
    public void run() {
            APIDeposit(this.amount);
    }

    public void APIDeposit(int amount) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"apiUsername\": \"amoko\",\n    \"apiPassword\": \"d85518cb49\",\n    \"amount\": \"" + amount + "\",\n    \"phoneNumber\": \"256755170341\",\n    \"description\": \"Adding cash to company's PGW account\"\n}");
        Request request = new Request.Builder()
                .url("http://172.105.72.111:8080/payment-gateway/api/Payments/MakeDeposit")
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
