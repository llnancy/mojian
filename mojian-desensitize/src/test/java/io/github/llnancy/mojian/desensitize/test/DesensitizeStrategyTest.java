package io.github.llnancy.mojian.desensitize.test;

import io.github.llnancy.mojian.desensitize.strategy.DesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.AddressDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.BankCardDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.ChineseNameDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.EmailDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.FixedPhoneDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.IdCardDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.MobileDesensitizeStrategy;
import io.github.llnancy.mojian.desensitize.strategy.impl.PasswordDesensitizeStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * DesensitizeStrategy Test
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class DesensitizeStrategyTest {

    @Test
    public void testAddress() {
        String address = "浙江省杭州市滨江区网商路666号";
        DesensitizeStrategy strategy = new AddressDesensitizeStrategy();
        String desensitize = strategy.desensitize(address);
        Assertions.assertEquals("浙江省杭州市**********", desensitize);
    }

    @Test
    public void testBankCard() {
        String bankCard = "6212364646483232455";
        DesensitizeStrategy strategy = new BankCardDesensitizeStrategy();
        String desensitize = strategy.desensitize(bankCard);
        Assertions.assertEquals("621236*********2455", desensitize);
    }

    @Test
    public void testChineseName() {
        String chineseName = "李逍遥";
        DesensitizeStrategy strategy = new ChineseNameDesensitizeStrategy();
        String desensitize = strategy.desensitize(chineseName);
        Assertions.assertEquals("李**", desensitize);
    }

    @Test
    public void testEmail() {
        String email = "admin@lilu.org.cn";
        DesensitizeStrategy strategy = new EmailDesensitizeStrategy();
        String desensitize = strategy.desensitize(email);
        Assertions.assertEquals("a****@lilu.org.cn", desensitize);
    }

    @Test
    public void testFixedPhone() {
        String fixedPhone = "076512344321";
        DesensitizeStrategy strategy = new FixedPhoneDesensitizeStrategy();
        String desensitize = strategy.desensitize(fixedPhone);
        Assertions.assertEquals("********4321", desensitize);
    }

    @Test
    public void testIdCard() {
        String idCard = "421083199905126789";
        DesensitizeStrategy strategy = new IdCardDesensitizeStrategy();
        String desensitize = strategy.desensitize(idCard);
        Assertions.assertEquals("**************6789", desensitize);
    }

    @Test
    public void testMobile() {
        String mobile = "15208900746";
        DesensitizeStrategy strategy = new MobileDesensitizeStrategy();
        String desensitize = strategy.desensitize(mobile);
        Assertions.assertEquals("152****0746", desensitize);
    }

    @Test
    public void testPassword() {
        String password = "LoveNancy";
        DesensitizeStrategy strategy = new PasswordDesensitizeStrategy();
        String desensitize = strategy.desensitize(password);
        Assertions.assertEquals("******", desensitize);
    }
}
