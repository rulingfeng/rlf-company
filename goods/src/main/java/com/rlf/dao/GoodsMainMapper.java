package com.rlf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rlf.model.GoodsMain;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-01-13
 */
 @Mapper
 public interface GoodsMainMapper extends BaseMapper<GoodsMain> {

//      @Update("CREATE TABLE IF NOT EXISTS ${name} LIKE order_main")
//      void createTable(@Param("name") String name);
//      // 查询 sharding好像查不了
//      @Select("SHOW TABLES LIKE 'order_main_%'")
//      List<String> showTables();
//      // 查询 sharding直接不支持
//      @Select("select table_name from information_schema.tables where table_schema='order' and table_name like \"order_main%\"")
//      List<String> showTables2();

}
