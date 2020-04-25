package cn.zzuisa;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

public class MyGenerator extends AutoGenerator {

	String prefix = "";

	@Override
	protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
		List<TableInfo> l = super.getAllTableInfoList(config);
		List<TableInfo> c = new ArrayList<>();
		for (TableInfo t : l) {
			if (t.getName().startsWith(prefix)) {
				c.add(t);
			}
		}
		return c;
	}

	/**
	 * 过滤，只生成和指定表前缀一致的文件
	 * @return
	 */
	public MyGenerator prefixFilter(String prefix) {
		this.prefix = prefix;
		return this;
	}
}
