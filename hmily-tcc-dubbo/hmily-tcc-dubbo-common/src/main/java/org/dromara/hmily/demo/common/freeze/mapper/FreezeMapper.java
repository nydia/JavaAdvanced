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

package org.dromara.hmily.demo.common.freeze.mapper;

import org.apache.ibatis.annotations.Update;
import org.dromara.hmily.demo.common.freeze.dto.FreezeDTO;

/**
 * The interface Inventory mapper.
 *
 * @author nydia
 */
public interface FreezeMapper {
    
    /**
     * Decrease int.
     *
     * @param freezeDTO the inventory dto
     * @return the int
     */
    @Update("update account_freeze set " +
            " freeze_amt = freeze_amt + #{freezeAmt}," +
            " update_time = now()" +
            " where user_id =#{userId}  and account_type = #{accountType} ")
    int update(FreezeDTO freezeDTO);
    
    /**
     * Confirm int.
     *
     * @param freezeDTO the inventory dto
     * @return the int
     */
    @Update("update account_freeze set " +
            " freeze_amt = freeze_amt - #{freezeAmt}" +
            " where user_id =#{userId}  and account_type = #{accountType} ")
    int confirm(FreezeDTO freezeDTO);
    
    /**
     * Cancel int.
     *
     * @param freezeDTO the inventory dto
     * @return the int
     */
    @Update("update account_freeze set " +
            " freeze_amt = freeze_amt - #{freezeAmt}" +
            " where user_id =#{userId}  and account_type = #{accountType} ")
    int cancel(FreezeDTO freezeDTO);
    
}
