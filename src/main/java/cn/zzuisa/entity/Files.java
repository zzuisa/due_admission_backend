package cn.zzuisa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ao
 * @date 2020-05-06 20:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("file")
public class Files extends Model<Files> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String studentId;
    private String path;
    private String type;
    private Date createTime;
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
