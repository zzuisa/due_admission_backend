package cn.zzuisa.service.impl;

import cn.zzuisa.entity.Files;
import cn.zzuisa.entity.Type;
import cn.zzuisa.mapper.FileMapper;
import cn.zzuisa.mapper.TypeMapper;
import cn.zzuisa.service.FilesService;
import cn.zzuisa.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author frank
 * @since 2020-04-27
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
