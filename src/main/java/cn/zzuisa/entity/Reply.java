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

/**
 * <p>
 *
 * </p>
 *
 * @author frank
 * @since 2019-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zzu_reply")
public class Reply extends Model<Reply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 志愿活动ID
     */
    private Long invitationId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复人
     */
    private Long memberId;

    /**
     * 回复时间
     */
    private LocalDateTime replyTime;

    /**
     * 类型0回复，1私信
     */
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
