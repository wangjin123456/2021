package com.taotao.myktdistributedluck.mapper;


import com.taotao.myktdistributedluck.entity.CommodityDetails;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName CommodityDetails
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
public interface CommodityDetailsMapper {

    /**
     * 扣库存
     *
     * @return
     */
    @Update("update  mayikt_commodity_details set stock = stock-1 where id=#{commodityId}")
    int reduceInventory(Long commodityId);

    @Select("select * from mayikt_commodity_details where id=#{commodityId}")
    CommodityDetails getCommodityDetails(Long commodityId);

    @Update("update  mayikt_commodity_details set stock = stock-1 where id=#{commodityId} and stock>0")
    int reduceInventory2(Long commodityId);

}
