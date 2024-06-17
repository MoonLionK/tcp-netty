package chc.dts.data.manage.function;

import com.ql.util.express.Operator;

/**
 * 计算扩展库
 *
 * @author xgy
 * @date 2024/5/28 18:51
 */
public class ExpressRunnerFunction {
    //入参为单个double且输出为double的计算方法
    public static class OperatorDoubleMath extends Operator {
        public OperatorDoubleMath(String name) {
            this.name = name;
        }

        @Override
        public Object executeInner(Object[] objects) throws Exception {
            double turn = 0.0;
            double result = 0.0;
            if (objects[0] instanceof Double) {
                turn = (Double) objects[0];
            } else {
                turn = ((Number) objects[0]).doubleValue();
            }
            switch (this.name) {
                //角度转弧度方法
                case "radian":
                    result = Math.toRadians(turn);
                    break;
                //sin方法
                case "sin":
                    result = Math.sin(turn);
                    break;
                case "cos":
                    result = Math.cos(turn);
                    break;
                case "tan":
                    result = Math.tan(turn);
                    break;
                case "cot":
                    result = Math.cos(turn) / Math.sin(turn);
                    break;
                case "sec":
                    result = 1.0 / Math.cos(turn);
                    break;
                case "csc":
                    result = 1.0 / Math.sin(turn);
                    break;
                case "sqrt"://开平方
                    result = Math.sqrt(turn);
                    break;
                case "abs"://绝对值
                    result = Math.abs(turn);
                    break;
            }
            return result;
        }
    }
    //入参为一个double加一个计算参数double，输出为double的计算方法
    public static class OperatorTwoDMath extends Operator {
        public OperatorTwoDMath(String name) {
            this.name = name;
        }

        @Override
        public Object executeInner(Object[] objects) throws Exception {
            double turn = 0.0;
            double turn1 = 0.0;
            double result = 0.0;
            if (objects[0] instanceof Double) {
                turn = (Double) objects[0];
            } else {
                turn = ((Number) objects[0]).doubleValue();
            }
            if (objects[1] instanceof Double) {
                turn1 = (Double) objects[1];
            } else {
                turn1 = ((Number) objects[1]).doubleValue();
            }
            switch (this.name) {
                //n次方
                case "pow":
                    result = Math.pow(turn,turn1);
                    break;
            }
            return result;
        }
    }
}
