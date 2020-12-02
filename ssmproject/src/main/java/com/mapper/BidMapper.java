package com.mapper;

import com.domain.Bid;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BidMapper extends Mapper<Bid> {

    Bid selectBidAndBidfileAll(@Param(value = "bid") Bid bid);

    /**
     * 根据招标项目的id查询参与这个项目的投标的信息
     *
     */
    List<Bid> selectByProjectbiddingid(@Param(value = "projectbiddingid") int projectbiddingid);
}