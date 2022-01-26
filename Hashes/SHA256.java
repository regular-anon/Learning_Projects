import java.util.ArrayList;
import java.util.Scanner;

public class SHA256 {
    public static void main(String[] args) {
        System.out.print("Text Input: ");
        String input = new Scanner(System.in).nextLine();
        // byte b = (byte)input.charAt(0);

        ArrayList<Byte> data = new ArrayList<>();
        for(int i = 0;i < input.length(); ++i) {
            data.add((byte)input.charAt(i));
        }
        data.add((byte)128);
        int messageLength = data.size() - 1;

        int multiple = 512;
        while(data.size() * 8 + 64 > multiple)
            multiple += 512;
        
        while(data.size() * 8 < multiple - 64)
            data.add((byte)0);
        
        long lengthBytes = messageLength * 8;
        System.out.println("Length Bytes: " + lengthBytes);

        for(int i = 0;i < 8; ++i) {
            data.add((byte)(lengthBytes / (((long)1) << 56)));
            lengthBytes <<= 8;
        }

        for(int i = 0;i < data.size(); ++i) {
            System.out.println(data.get(i));
        }

        int h0 = 0x6a09e667;
        int h1 = 0xbb67ae85;
        int h2 = 0x3c6ef372;
        int h3 = 0xa54ff53a;
        int h4 = 0x510e527f;
        int h5 = 0x9b05688c;
        int h6 = 0x1f83d9ab;
        int h7 = 0x5be0cd19;

        int[] roundValues = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};
        
        System.out.println("Data Size: " + data.size());

        //Chunk Loop
        System.out.println("Words-----");
        for (int i = 0;i < data.size() / 64; ++i) {
            int[] messageSchedule = new int[64];
            for(int j = 0;j < 64;j += 4) {
                int word = ((int)data.get(i + j)) << 24 + ((int)data.get(i + j + 1)) << 16 + ((int)data.get(i + j + 2)) << 8 + (int)data.get(i + j + 3);
                messageSchedule[j / 4] = word;
            }
            for(int j = 0;j < 16;++j)
                System.out.println(messageSchedule[j]);
            for(int j = 16;j < 64;++j)
                messageSchedule[j] = 0;
            for(int j = 16;j < 64; ++j) {
                int s0 = (messageSchedule[j - 15] >>> 7) ^ (messageSchedule[j - 15] >>> 18) ^ (messageSchedule[j - 15] >> 3);
                int s1 = (messageSchedule[j - 2] >>> 17) ^ (messageSchedule[j - 2] >>> 19) ^ (messageSchedule[j - 2] >> 10);
                messageSchedule[j] = messageSchedule[j - 16] + s0 + messageSchedule[j - 7] + s1; 
            }
            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;
            int f = h5;
            int g = h6;
            int h = h7;

            for(int j = 0;j < 64; ++j) {
                int S1 = (e >>> 6) ^ (e >>> 1) ^ (e >>> 25);
                int ch = (e & f) ^ ((~e) & g);
                int temp1 = h + S1 + ch + roundValues[j] + messageSchedule[j];
                int S0 = (a >>> 2) ^ (a >>> 13) ^ (a >>> 22);
                int maj = (a & b) ^ (a & c) ^ (b & c);
                int temp2 = S0 + maj;
                h = g;
                g = f;
                f = e;
                e = d + temp1;
                d = c;
                c = b;
                b = a;
                a = temp1 + temp2;
            }
            h0 += a;
            h1 += b;
            h2 += c;
            h3 += d;
            h4 += e;
            h5 += f;
            h6 += g;
            h7 += h;
        }
        String hash = Integer.toHexString(h0) + Integer.toHexString(h1) + Integer.toHexString(h2) + Integer.toHexString(h3) + Integer.toHexString(h4) + Integer.toHexString(h5) + Integer.toHexString(h6) + Integer.toHexString(h7);
        System.out.println("Final Hash: " + hash);
    }
}