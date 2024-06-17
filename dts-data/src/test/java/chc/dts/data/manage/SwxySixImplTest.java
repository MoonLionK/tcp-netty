package chc.dts.data.manage;

import chc.dts.data.manage.function.ExpressRunnerPack;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author xgy
 * @date 2024/5/14 14:29
 */
class SwxySixImplTest {

    @Test
    void calculate() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("VT", 1);
        context.put("SIG", 2);
        context.put("SI", 3);
        String express = "VT+SIG-SI";
        Object r = runner.execute(express, context, null, true, true);
        assertEquals(0, r);
    }

    @Test
    void sin() throws Exception {
        // 创建 DecimalFormat 对象,设置格式为保留一位小数
        DecimalFormat df = new DecimalFormat("#0.0");
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("SIG", 30.0);
        String express = "sin(radian(SIG))";
        Object r = ExpressRunnerPack.getInstance().getExpressRunner().execute(express, context, null, true, true);
        assertEquals("0.5", df.format(r));

    }

    @Test
    void sin2() throws Exception {
        // 创建 DecimalFormat 对象,设置格式为保留一位小数
        DecimalFormat df = new DecimalFormat("#0.0");
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("SIG", 30);
        String express = "sin(radian(SIG))";
        Object r = ExpressRunnerPack.getInstance().getExpressRunner().execute(express, context, null, true, true);
        assertEquals("0.5", df.format(r));

    }

    @Test
    void test1() {
        Integer i = 300;
        update(i);
        Assertions.assertEquals(300, i);

    }

    private void update(Integer i) {
        i = 400;
        Assertions.assertEquals(400, i);
    }

    @Test
    void test2() {
        Integer i = 300;
        Integer i1 = update2(i);
        Assertions.assertEquals(300, i);
        Assertions.assertEquals(400, i1);

    }

    private Integer update2(Integer i) {
        i = 400;
        Assertions.assertEquals(400, i);
        return i;
    }
}