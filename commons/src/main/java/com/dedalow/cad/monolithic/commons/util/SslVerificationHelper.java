package com.dedalow.cad.monolithic.commons.util;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SslVerificationHelper {

  public TrustManager[] createAllTrusting(boolean enable) {

    TrustManager[] trustManager = new TrustManager[] {};

    if (enable) {

      trustManager =
          new TrustManager[] {
            new X509TrustManager() {
              @Override
              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
              }

              @Override
              public void checkClientTrusted(
                  final X509Certificate[] certs, final String authType) {}

              @Override
              public void checkServerTrusted(
                  final X509Certificate[] certs, final String authType) {}
            }
          };

    } else {

      try {

        TrustManagerFactory trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);
        trustManager = trustManagerFactory.getTrustManagers();

      } catch (NoSuchAlgorithmException nsae) {
        nsae.printStackTrace();
      } catch (KeyStoreException kse) {
        kse.printStackTrace();
      }
    }

    return trustManager;
  }

  public void disableSslVerification(SSLContext sc) {

    try {

      TrustManager[] trustAllCerts = createAllTrusting(true);
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    } catch (KeyManagementException e) {
      e.printStackTrace();
    }

    updateHostVerifier();
  }

  public void enableSslVerification(SSLContext sc) {

    try {

      TrustManager[] untrustAllCerts = createAllTrusting(false);
      sc.init(null, untrustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    } catch (KeyManagementException e) {
      e.printStackTrace();
    }

    updateHostVerifier();
  }

  public void updateHostVerifier() {

    final HostnameVerifier allHostsValid =
        new HostnameVerifier() {
          @Override
          public boolean verify(String hostname, SSLSession session) {
            return true;
          }
        };
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
  }
}
