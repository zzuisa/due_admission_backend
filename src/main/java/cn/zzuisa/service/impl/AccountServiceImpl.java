package cn.zzuisa.service.impl;

import cn.zzuisa.base.R;
import cn.zzuisa.entity.Account;
import cn.zzuisa.mapper.AccountMapper;
import cn.zzuisa.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author frank
 * @since 2019-05-11
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Map<String, Object>> listByDescInvitationCount() {
        return accountMapper.listByDescInvitationCount();
    }

    @Override
    public R active(int userId, String code) {
        if (code.equals("ACTIVATED")) {
            return R.error("请不要重复激活!");
        }
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        Account account = accountMapper.selectById(userId);
        updateWrapper.eq("id", userId);
        account.setActivationCode("ACTIVATED");
        return accountMapper.update(account, updateWrapper) > 0 ? R.ok("success") : R.error("failed");
    }
}
