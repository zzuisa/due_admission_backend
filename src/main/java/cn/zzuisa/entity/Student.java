package cn.zzuisa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ao
 * @date 2020-04-24 16:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student")
public class Student extends Model<Student> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private int roleId;
    private String name;
    private Date birthday;
    private String gender;
    private String cet4;
    private String cet6;
    private String phone;
    private String apsPassed;
    private String address;
    private String nationality;

    private int apsid;
    private String apsAuthFile;
    private String examAuthFile;
    private String passport;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
