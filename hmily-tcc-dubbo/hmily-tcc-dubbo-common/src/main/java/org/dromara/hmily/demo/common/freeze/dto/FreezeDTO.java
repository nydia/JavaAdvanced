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

package org.dromara.hmily.demo.common.freeze.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The type Inventory dto.
 *
 * @author nydia
 */
@Data
@Builder
public class FreezeDTO implements Serializable {

    private static final long serialVersionUID = 8229355519336565493L;

    private String userId;

    private BigDecimal freezeAmt;

    /**
     * 账户类型 RMB - 人民币 / USD - 美元
     */
    private String accountType;
}
