package com.example.android.bakingapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class NetUtils {

    private NetUtils() { /* This class is not intended to be instantiated */ }

    public static void request(String endpoint, final Response response) {
        request(endpoint, Response.NO_ID, response);
    }

    public static void request(String endpoint, final int recipeId, final Response response) {
        if (response != null) {
            final URL url;

            try {
                url = new URL(endpoint);
            } catch (MalformedURLException e) {
                response.onError(e.getMessage(), recipeId);
                return;
            }

            AppExecutors.instance().networkIO().execute(() -> {
                HttpURLConnection urlConnection = null;

                try {
                    Socket sock = new Socket();

                    sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                    sock.close();

                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();

                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");

                    response.onData(scanner.hasNext() ? scanner.next() : null, recipeId);
                } catch (IOException e) {
                    response.onError(e.getMessage(), recipeId);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            });
        }
    }

    public interface Response {
        int NO_ID = -1;

        void onData(@Nullable String response, int recipeId);
        void onError(@NonNull String message, int recipeId);
    }
}
