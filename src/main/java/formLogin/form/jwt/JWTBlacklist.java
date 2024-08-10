package formLogin.form.jwt;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JWTBlacklist {

    private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

    public void addToken(String token, Date expiryDate) {
        System.out.println("token add to blacklist = " + token);
        blacklist.put(token, expiryDate);
        System.out.println("current blacklist = " + blacklist);
    }

    public boolean isBlacklisted(String token) {
        System.out.println("token = " + token);
        System.out.println("blacklist.containsKey(token) = " + blacklist.containsKey(token));
        return blacklist.containsKey(token);
    }

    public void removeExpiredTokens() {
        Date now = new Date();
        blacklist.entrySet().removeIf(entry -> entry.getValue().before(now));
    }
}
