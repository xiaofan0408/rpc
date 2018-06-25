package my.util;

public class ZigZag {
    public static  int intToZigzag(int n) {
        return  (n<<1)^(n>>31);
    }
    public static  int ZigzagToInt(int n) {
        return (int)((getUnsignedIntt(n)>>1)^-(n&1));
    }
    public static  long getUnsignedIntt (int data){
        return data&0x0FFFFFFFF;
    }

    public static void main(String[] args) {
        System.out.println(ZigZag.intToZigzag((2)));
        System.out.println(ZigZag.ZigzagToInt((1)));
    }
}
