package cn.zzuisa.config;

import java.util.HashMap;

public class TokenManager {

    private static final HashMap<String,Object> tokens = new HashMap<>();

    public int size() {
        return tokens.size();
    }

    public synchronized static void put(String token,Object value) {
        tokens.put(token,value);
    }

    public static boolean isLogin(String token) {
        return tokens.containsKey(token);
    }

    public synchronized static void rm(String token) {
        tokens.remove(token);
    }

    public static HashMap<String,Object> all() {
        return tokens;
    }

    public static Integer get(String token) {
        System.out.println("!!!!!!!!"+token);
        return (Integer) tokens.get(token.replaceAll("\"",""));
    }
}
