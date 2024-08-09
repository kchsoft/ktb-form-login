package formLogin.form.jwt;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JWTBlacklist {

    private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

    // JWT를 블랙리스트에 추가
    public void addToken(String token, Date expiryDate) {
        System.out.println("token add = " + token);
        blacklist.put(token, expiryDate);
        System.out.println("current blacklist = " + blacklist);
    }

    // JWT가 블랙리스트에 있는지 확인
    public boolean isBlacklisted(String token) {
        System.out.println("token = " + token);
        System.out.println("blacklist.containsKey(token) = " + blacklist.containsKey(token));
        return blacklist.containsKey(token);
    }

    // 만료된 JWT를 블랙리스트에서 제거
    public void removeExpiredTokens() {
        Date now = new Date();
        blacklist.entrySet().removeIf(entry -> entry.getValue().before(now));
    }
}
