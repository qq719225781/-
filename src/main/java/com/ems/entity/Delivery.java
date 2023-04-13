package com.ems.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ZJ
 * @data 2023/3/20 21:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Delivery对象", description = "")
public class Delivery {

    @TableField("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("delivery_user_id")
    private Integer deliveryUserId;

    @TableField("delivery_user_name")
    private String deliveryUserName;

    @TableField("receiving_user_name")
    private String receivingUserName;

    @TableField("receiving_user_id")
    private String receivingUserId;

    @TableField("wrap_no")
    private String wrapNo;

    @TableField("wrap_id")
    private String wrapId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("delivery_time")
    private Date deliveryTime;

    @TableField("remark")
    private String remark;

}
