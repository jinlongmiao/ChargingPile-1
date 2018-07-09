package com.swust.chargingmobileback.dao;

/**
 * @author 73559
 * @Date 2018/7/8 16:26
 * @Description 用于写一些没有必要创建model,mapper的sql,
 */
public interface DefinedMapper {
    /**
     * 在t_chargingpile中通过pileid查询pileState
     * @param pileid
     * @return
     */
    String findPileState(String pileid);
}
