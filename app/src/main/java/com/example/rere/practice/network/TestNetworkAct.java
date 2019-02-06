package com.example.rere.practice.network;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by rere on 2018/9/23.
 */

public class TestNetworkAct extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestNetworkAct.class));
    }

    private String server_address = "192.168.0.108";
    private String server_port = "8001";
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private EditText etServerAddress;
    private EditText etServerPort;

    @Override
    protected void addViews(LinearLayout layout) {
        etServerAddress = getEditText(layout, "server address");
        etServerPort = getEditText(layout, "server port");

        etServerAddress.setText(server_address);
        etServerPort.setText(server_port);

        getButton(layout, "get My server test", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshServerAddress();

                // get My server test
                Request request = new Request.Builder()
                        .url("http://" + server_address + ":" + server_port)
                        .build();

                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        TagLog.i(TAG, "onFailure() : ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "onFailure" + " e.getMessage() = " + e.getMessage() + ",", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        TagLog.i(TAG, "onResponse() : " + " response = " + response + ",");
                        String string = response.body().string();
                        TagLog.i(TAG, "onResponse() : " + " string = " + string + ",");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, " response.body() = " + string + ",", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

        getButton(layout, "post image", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // post image
                refreshServerAddress();

                File file = new File(Environment.getExternalStorageDirectory(), "face_image_contains_2.jpg");
                if (!file.exists()) {
                    Toast.makeText(mContext, "文件不存在", Toast.LENGTH_SHORT).show();
                    return;
                }


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", "admin")//
                        .addFormDataPart("password", "password")//
                        .addFormDataPart("image", "face_image_contains_2.jpg", RequestBody.create(MediaType.parse("application/octet-stream"), file))
                        .build();

                Request request = new Request.Builder()
                        .post(requestBody)
                        .url("http://" + server_address + ":" + server_port)
                        .build();

                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        TagLog.i(TAG, "onFailure() : ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "onFailure" + " e.getMessage() = " + e.getMessage() + ",", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        TagLog.i(TAG, "onResponse() : " + " response = " + response + ",");
                        String string = response.body().string();
                        TagLog.i(TAG, "onResponse() : " + " string = " + string + ",");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, " response.body() = " + string + ",", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void refreshServerAddress() {
        server_address = etServerAddress.getText().toString();
        server_port = etServerPort.getText().toString();
    }
}
