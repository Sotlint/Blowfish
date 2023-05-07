package com.kurs.blowfish.entity;


import java.util.Arrays;

public class blowfish {

    private final long[] P = new long[18];
    private final long[][] S = new long[4][256];

    String key="secret";
    boolean check=false;


    public blowfish() {
        init();
    }


    private void init() {
        char[] keyArray = key.toCharArray();
        int keyIndex=0;

        for (int i = 0; i < P.length; i++) {
            P[i] = i;
            P[i]^=keyArray[keyIndex];
            if(keyIndex+1<keyArray.length)
            {
                keyIndex++;
            }
            else
            {
                keyIndex=0;
            }
        }
        keyIndex=0;
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S[i].length; j++) {
                S[i][j] = j;
                S[i][j]^=keyArray[keyIndex];
                if(keyIndex+1<keyArray.length)
                {
                    keyIndex++;
                }
                else
                {
                    keyIndex=0;
                }
            }
        }
    }

    public void newKey(String key)
    {
        this.setKey(key);
        char[] keyArray = key.toCharArray();
        int keyLen=keyArray.length;
        int keyIndex=0;

        for (int i = 0; i < P.length; i++) {
            P[i] = i;
            P[i]^=keyArray[keyIndex];
            if(keyIndex+1<keyLen)
            {
                keyIndex++;
            }
            else
            {
                keyIndex=0;
            }
        }
        keyIndex=0;
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S[i].length; j++) {
                S[i][j] = j;
                S[i][j]^=keyArray[keyIndex];
                if(keyIndex+1<keyLen)
                {
                    keyIndex++;
                }
                else
                {
                    keyIndex=0;
                }
            }
        }
    }
    private long[] encryptBlock(long[] block)
    {
        long[] result=new long[2];
        long temp;
        long L=block[0];
        long R=block[1];
        for (int i = 0; i < 16; i++) {
            L^=P[i];
            R^=F(L);
            temp=L;
            L=R;
            R=temp;
        }
        temp=L;
        L=R;
        R=temp;

        L^=P[17];
        R^=P[16];

        result[0]=L;
        result[1]=R;
        return result;
    }

    private long[] decryptBlock(long[] block)
    {
        long[] result = new long[2];
        long temp;
        long L = block[0];
        long R = block[1];

        R ^= P[16];
        L ^= P[17];

        temp = L;
        L = R;
        R = temp;

        for (int i = 15; i >= 0; i--) {
            temp = R;
            R = L;
            L = temp;
            R ^= F(L);
            L ^= P[i];
        }

        result[0] = L;
        result[1] = R;

        return result;
    }

    private long F(long x)
    {
        long a = S[0][(int)((x >> 24) & 0xFF)];
        long b = S[1][(int)((x >> 16) & 0xFF)];
        long c = S[2][(int)((x >> 8) & 0xFF)];
        long d = S[3][(int)(x & 0xFF)];
        return ((a + b) ^ c) + d;
    }

    public String encrypt(String text)
    {

        if(text.length()%2!=0)
        {
            text+="_";
            check=true;
        }
        else
        {
            check=false;
        }

        char[] originArray = text.toCharArray();

        long[] encArray = new long[originArray.length];
        long[] LongArray = new long[originArray.length];
        for(int i=0;i<LongArray.length;i++)
        {
            LongArray[i]= originArray[i];
        }

        int k=0;

        long[] block = new long[2];
        long[] result;
        for(int i=0;i<encArray.length;i+=2)
        {
            block[0]=LongArray[i];
            block[1]=LongArray[i+1];

            result=encryptBlock(block);

            encArray[k]=  result[0];
            encArray[k+1]=  result[1];
            k+=2;
        }

        return Arrays.toString(encArray);
    }

    public String decrypt(long[] text)
    {
        char[] originArray = new char[text.length];

        int k=0;

        long[] block = new long[2];
        long[] result;
        for(int i=0;i<text.length;i+=2)
        {
            block[0]=text[i];
            block[1]=text[i+1];

            result=decryptBlock(block);

            originArray[k]= (char) result[0];
            originArray[k+1]= (char) result[1];
            k+=2;
        }

        if(check)
        {
            originArray[originArray.length-1]=' ';
        }
        return new String(originArray);
    }

    public long[] converter(String str)
    {
        str = str.replace("[", "").replace("]", "");
        String[] strArr = str.split(", ");
        long[] longArr = new long[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            longArr[i] = Long.parseLong(strArr[i]);
        }
        return longArr;
    }

    public void setKey(String key) {
        this.key = key;
    }
}