package com.github.zjjfly.spring5.domain.common;

import lombok.Setter;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/22
 */
@Setter
public class ValidList<T> extends ArrayList<T> {

    @Delegate
    @Valid
    private List<T> list;

}
