/*
 * Copyright 2017-2021 Dromara.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hmily.demo.common.freeze.api;

import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.demo.common.freeze.dto.FreezeDTO;
import org.dromara.hmily.demo.common.freeze.entity.FreezeDO;

import java.util.List;

/**
 * The interface Inventory service.
 *
 * @author nydia
 */
public interface FreezeService {
    
    /**
     * 扣减库存操作
     * 这一个tcc接口
     *
     * @param freezeDTO 库存DTO对象
     * @return true boolean
     */
    @Hmily
    Boolean decrease(FreezeDTO freezeDTO);
    
    /**
     * Test in line list.
     *
     * @return the list
     */
    @Hmily
    List<FreezeDTO> testInLine();
    
    /**
     * Test decrease boolean.
     *
     * @param freezeDTO the inventory dto
     * @return the boolean
     */
    Boolean testDecrease(FreezeDTO freezeDTO);
    
    /**
     * 获取商品库存信息
     *
     * @param productId 商品id
     * @return FreezeDO inventory do
     */
    FreezeDO findByProductId(String productId);
    
    /**
     * mock扣减库存异常
     *
     * @param freezeDTO dto对象
     * @return String string
     */
    @Hmily
    String mockWithTryException(FreezeDTO freezeDTO);
    
    /**
     * mock扣减库存超时
     *
     * @param freezeDTO dto对象
     * @return String boolean
     */
    @Hmily
    Boolean mockWithTryTimeout(FreezeDTO freezeDTO);
    
    /**
     * mock 扣减库存confirm超时
     *
     * @param freezeDTO dto对象
     * @return True boolean
     */
    @Hmily
    Boolean mockWithConfirmTimeout(FreezeDTO freezeDTO);
}
