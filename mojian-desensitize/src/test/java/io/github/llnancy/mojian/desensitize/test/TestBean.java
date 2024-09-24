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
 * test bean
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
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

    @Desensitize(strategy = PasswordDesensitizeStrategy.class)
    private String password2;
}
