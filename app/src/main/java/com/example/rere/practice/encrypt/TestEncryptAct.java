package com.example.rere.practice.encrypt;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;
import com.example.rere.practice.encrypt.base64.Base64;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by rere on 2018/11/12.
 */

public class TestEncryptAct extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestEncryptAct.class));
    }

    /*public static final String content = "UBQyQwFVJyy/WEDMtgOoCyOKHNNmDhi/6iyO+FBBseX7wpzf04ACYOdLu1MC6Y9pUiHPzhoU5MRVULVtZp0Odnkc1xij3lLx/TsVdVGCArjHbtYVG8W9y2fBI51R9m70uX94HjQMUpzvabWXn+JgUGxOdZl2pPrTfXX5UuC0hf5FwNJStKH7iVN/rpvGZJyA4pih1SJ/2f9s6lEamEKS3TOOtFRyyN3MUP/Yic/nX7viszjzUlkqduA67FTfkngiIpDDJfLtAST+5qZmBVsFo68pnXzUeWC3GCxsMe1E0Y0iMBJuwofSaviSys/faB/TIs8eYfSlxHoRj7ZYj7/jDYfMsHtjlKpEskG4W5zKgmA8RKs2b51T/bFTzn/Fwssl88KI4UiiqukrO7lMWhfzglDimSSnbVVZxQ5X6pvC4pYAUJheQt2PsUHFkt1Ajr4gIPH4mNV70ikqFnHby8ZGMoacRvpLKy6trilQNJRoo/4c1sUSZBt6HBz16ViDf7m4TtlPA/Uz5fCJNfAzriPga/TjXgHiCZTSFvVs29KtY1QAOhb4B8mzOfrYnCfqNA8dI8KXB5y/f+tT+JcTRvFAGxlvtU9JlihWpcKZwYecQjLPHG+9bD+Tsf3WahZlZXhh5NRi30UTseAak4RnsrQ8iGM62e7timU7eOuEscRM3yFOeD3QAj/5dct+E+EM0pEfPogCaOfbjvzeClP1ZVXUDxxhx2qMPYeaa3iIlqEPdc0FUbJj4PRgflC4bRLOdJo8i8IKRcrWTAlxNcp+XVzKn92x4dROWrh0orwBp5ORLWgEBIXy9UMT0M1o6eS05t3V6+TdpwYTTeKZA6xJN8JwyPB7QAtILHVSmMZMPTBW77VhiIuSEW+t/VbKzOB4tslHPFR26HPqRFP0W//iNjDVLmV2dtfQ3Tk/mOwxJvDKSkHDE+SX0Zwno6NX+NSaH//z";
    public static final String key = "tJRkQJbQkVRfRTPxtXYxaEJp";
    public static final String ivs = "56Tpe0bV";*/

    public static final String content = "IQjJmIdGZQ4EZQs6OAYkgXWTTGfmOgTXHIFEu25A/C35bqybLsvYDgvf5r4AR4rUr2lvGhGeW/2wdQbOsM4WddfP+87NN3V6aurUgC9xZbQLszHo1JQsYg==";
    public static final String key = "UOGvP9TM2xF3mrwPjjiWPG6z";
    public static final String ivs = "lU8tCTP1";

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "descrypt", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // descrypt
                final Triple3DesUtils triple3DesUtils = new Triple3DesUtils(key, ivs);
                final String s = triple3DesUtils.decryptECB(content, "UTF-8");
                TagLog.i(TAG, "onClick() : " + " s = " + s + ",");
            }
        });
    }

    public class Triple3DesUtils {

        byte[] key;
        byte[] ivs;

        public Triple3DesUtils(String key, String ivs) {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(ivs)) {
                if (key.length() > 24) {
                    key = key.substring(0, 24);
                }
                if (ivs.length() > 8) {
                    ivs.substring(0, 8);
                }
                this.key = key.getBytes();
                this.ivs = ivs.getBytes();
            }
        }

        public String decryptECB(String srcStr, String codeName) {
            try {
                SecretKeySpec desKey = new SecretKeySpec(this.key, "DESede");
                IvParameterSpec iv = new IvParameterSpec(this.ivs);
                Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
                cipher.init(Cipher.DECRYPT_MODE, desKey, iv);
                final byte[] base64Decode = Base64.decode(srcStr);
                final String base64DecodeStr = new String(base64Decode);
                TagLog.i(TAG, "decryptECB() : " + " base64DecodeStr = " + base64DecodeStr + ",");
                final byte[] bytes = cipher.doFinal(base64Decode);
                final String result = new String(bytes, codeName);
                TagLog.i(TAG, "decryptECB() : " + " result = " + result + ",");
                return result;
            } catch (Exception e) {
                TagLog.e(TAG, "decryptECB() : " + e.getMessage());
                return "";
            }
        }


    }

}
