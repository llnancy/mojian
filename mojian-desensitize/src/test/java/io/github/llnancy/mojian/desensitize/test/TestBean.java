package io.github.llnancy.mojian.desensitize.test;

import io.github.llnancy.mojian.desensitize.annotation.Desensitize;
import io.github.llnancy.mojian.desensitize.strategy.impl.AddressDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.BankCardDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.ChineseNameDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.EmailDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.FixedPhoneDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.IdCardDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.MobileDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.PasswordDesensitizeStrategy;
import lombok.Data;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
@Data
public class TestBean {

    @Desensitize(strategy = ChineseNameDesensitizeStrategy.class)
    private String name;

    @Desensitize(strategy = EmailDesensitizeStrategy.class)
    private String email;

    @Desensitize(strategy = MobileDesensitizeStrategy.class)
    private String mobile;

    @Desensitize(strategy = FixedPhoneDesensitizeStrategy.class)
    private String fixedPhone;

    @Desensitize(strategy = AddressDesensitizeStrategy.class)
    private String address;

    @Desensitize(strategy = BankCardDesensitizeStrategy.class)
    private String bankCard;

    @Desensitize(strategy = IdCardDesensitizeStrategy.class)
    private String idCard;

    @Desensitize(strategy = PasswordDesensitizeStrategy.class)
    private String password;
}
