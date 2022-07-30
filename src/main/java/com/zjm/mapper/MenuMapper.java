package com.zjm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.domain.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 张佳敏
 * @Description
 * @create 2022/7/18 14:00
 **/
@Component
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据userid查询相关的权限信息
     *
     * @param userid
     * @return
     */
    List<String> selectPermsByUserId(Long userid);
}
