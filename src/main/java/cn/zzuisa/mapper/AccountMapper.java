package cn.zzuisa.mapper;

import cn.zzuisa.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author frank
 * @since 2019-05-07
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    List<Map<String, Object>> listByDescInvitationCount();
}
