package com.ems.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZJ
 * @data 2023/3/20 15:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ApplyInfo对象", description = "")
public class ApplyInfo implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableField("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("pakage_id")
    private Integer pakageId;

    @TableField("wrap_no")
    private String wrapNo;

    @TableField("pickup_code")
    private String pickupCode;

    @TableField("apply_user_id")
    private Integer applyUserId;

    @TableField("apply_user_name")
    private String applyUserName;

    @TableField("apply_state")
    private String applyState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("apply_time")
    private Date applyTime;

}
