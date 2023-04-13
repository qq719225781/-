package com.ems.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author ems
 * @since 2023-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Pakage对象", description="")
public class Pakage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "包裹名")
    private String name;

    @ApiModelProperty(value = "存放货架")
    private String warehouse;

    @ApiModelProperty(value = "快递类型")
    @TableField("pakageType")
    private Integer pakagetype;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField("userId")
    private Integer userId;

    @TableField("logistics")
    private Integer logistics;

    @TableField("is_delete")
    private String isDelete;


}
