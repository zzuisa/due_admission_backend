package cn.zzuisa.utils;

import cn.zzuisa.entity.Account;
import org.springframework.stereotype.Component;

/**
 * @author Ao
 * @date 2019-10-27 14:56
 */
@Component
public class HostHolder {
    private ThreadLocal<Account> users = new ThreadLocal<>();

    public void setUser(Account user) {
        users.set(user);

    }

    public Account getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();

    }
}
