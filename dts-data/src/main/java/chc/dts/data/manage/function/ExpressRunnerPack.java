package chc.dts.data.manage.function;
import com.ql.util.express.ExpressRunner;
/**
 * 计算ExpressRunner封装为单例
 *
 * @author xgy
 * @date 2024/5/28 18:51
 */
public class ExpressRunnerPack {

    private ExpressRunner expressRunner;
    private static final ExpressRunnerPack INSTANCE = new ExpressRunnerPack();

    public static ExpressRunnerPack getInstance() {
        return INSTANCE;
    }


    public ExpressRunner getExpressRunner() {
        if (expressRunner == null) {
            expressRunner = new ExpressRunner();
            initExpressRunner();
        }
        return expressRunner;
    }
    //初始化扩展方法库
    private void initExpressRunner() {
        expressRunner.addFunction("radian", new ExpressRunnerFunction.OperatorDoubleMath("radian"));
        expressRunner.addFunction("sin" ,new ExpressRunnerFunction.OperatorDoubleMath("sin"));
        expressRunner.addFunction("cos" ,new ExpressRunnerFunction.OperatorDoubleMath("cos"));
        expressRunner.addFunction("tan" ,new ExpressRunnerFunction.OperatorDoubleMath("tan"));
        expressRunner.addFunction("cot" ,new ExpressRunnerFunction.OperatorDoubleMath("cot"));
        expressRunner.addFunction("sec" ,new ExpressRunnerFunction.OperatorDoubleMath("sec"));
        expressRunner.addFunction("csc" ,new ExpressRunnerFunction.OperatorDoubleMath("csc"));
        expressRunner.addFunction("sqrt" ,new ExpressRunnerFunction.OperatorDoubleMath("sqrt"));
        expressRunner.addFunction("abs" ,new ExpressRunnerFunction.OperatorDoubleMath("abs"));
        expressRunner.addFunction("pow" ,new ExpressRunnerFunction.OperatorTwoDMath("pow"));
    }
}

