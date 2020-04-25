package cn.zzuisa.service;

import cn.zzuisa.base.R;
import cn.zzuisa.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author frank
 * @since 2019-05-07
 */
public interface AccountService extends IService<Account> {

    List<Map<String, Object>> listByDescInvitationCount();

    R active(int userId, String code);
}
