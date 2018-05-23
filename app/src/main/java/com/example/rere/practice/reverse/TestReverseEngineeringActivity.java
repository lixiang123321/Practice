package com.example.rere.practice.reverse;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * reverse engineering
 * <p>
 * Created by rere on 18-4-20.
 */

public class TestReverseEngineeringActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestReverseEngineeringActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "get other app sign info", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get other app sign info
                final PackageManager packageManager = mContext.getPackageManager();
                final List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES);

                StringBuilder sb = new StringBuilder();

                for (PackageInfo p : packageList) {
                    final String strName = p.applicationInfo.loadLabel(packageManager).toString();
                    final String strVendor = p.packageName;

                    if (!"com.ccb.wop".equals(p.packageName)) {
                        continue;
                    }

                    sb.append("<br>" + strName + " / " + strVendor + "<br>");

                    final Signature[] arrSignatures = p.signatures;
                    for (final Signature sig : arrSignatures) {
                    /*
                    * Get the X.509 certificate.
                    */
                        final byte[] rawCert = sig.toByteArray();
                        InputStream certStream = new ByteArrayInputStream(rawCert);

                        try {
                            CertificateFactory certFactory = CertificateFactory.getInstance("X509");
                            X509Certificate x509Cert = (X509Certificate) certFactory.generateCertificate(certStream);

                            sb.append("Certificate subject: " + x509Cert.getSubjectDN() + "<br>");
                            sb.append("Certificate issuer: " + x509Cert.getIssuerDN() + "<br>");
                            sb.append("Certificate serial number: " + x509Cert.getSerialNumber() + "<br>");
                            sb.append("<br>");
                        } catch (CertificateException e) {
                            // e.printStackTrace();
                        }
                    }
                }
                TagLog.i(TAG, "onClick() : " + "sign = " + sb + ",");
            }
        });


    }
}
