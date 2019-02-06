package com.example.rere.practice.encrypt.base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64
{
    private static final Encoder encoder = new Base64Encoder();
    private static String coderTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public static boolean isBase64(String raw) {
        if ((raw == null) || (raw.length() == 0)) {
            return false;
        }
        boolean ret = true;
        for (int i = 0; i < raw.length(); i++) {
            if (coderTable.indexOf(Character.toString(raw.charAt(i))) == -1) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public static byte[] encode(byte[] data)
    {
        int len = (data.length + 2) / 3 * 4;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
        try
        {
            encoder.encode(data, 0, data.length, bOut);
        }
        catch (IOException e)
        {
            throw new RuntimeException("exception encoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    public static int encode(byte[] data, OutputStream out)
            throws IOException
    {
        return encoder.encode(data, 0, data.length, out);
    }

    public static int encode(byte[] data, int off, int length, OutputStream out)
            throws IOException
    {
        return encoder.encode(data, off, length, out);
    }

    public static byte[] decode(byte[] data)
    {
        int len = data.length / 4 * 3;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
        try
        {
            encoder.decode(data, 0, data.length, bOut);
        }
        catch (IOException e)
        {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    public static byte[] decode(String data)
    {
        int len = data.length() / 4 * 3;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
        try
        {
            encoder.decode(data, bOut);
        }
        catch (IOException e)
        {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }

        return bOut.toByteArray();
    }

    public static int decode(String data, OutputStream out)
            throws IOException
    {
        return encoder.decode(data, out);
    }
}