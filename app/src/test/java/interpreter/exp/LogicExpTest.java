
package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {

    private IValue evaluate(LogicOp op, boolean a, boolean b)
            throws Exception {

        LogicExp expression = new LogicExp(
                op,
                new ValueExp(new BoolValue(a)),
                new ValueExp(new BoolValue(b))
        );

        return expression.eval(new MyDictionary<>());
    }

    @Test
    void andTrueTrue() throws Exception {
        assertEquals(new BoolValue(true),
                evaluate(LogicOp.AND, true, true));
    }

    @Test
    void andTrueFalse() throws Exception {
        assertEquals(new BoolValue(false),
                evaluate(LogicOp.AND, true, false));
    }

    @Test
    void andFalseTrue() throws Exception {
        assertEquals(new BoolValue(false),
                evaluate(LogicOp.AND, false, true));
    }

    @Test
    void andFalseFalse() throws Exception {
        assertEquals(new BoolValue(false),
                evaluate(LogicOp.AND, false, false));
    }

    @Test
    void orTrueTrue() throws Exception {
        assertEquals(new BoolValue(true),
                evaluate(LogicOp.OR, true, true));
    }

    @Test
    void orTrueFalse() throws Exception {
        assertEquals(new BoolValue(true),
                evaluate(LogicOp.OR, true, false));
    }

    @Test
    void orFalseTrue() throws Exception {
        assertEquals(new BoolValue(true),
                evaluate(LogicOp.OR, false, true));
    }

    @Test
    void orFalseFalse() throws Exception {
        assertEquals(new BoolValue(false),
                evaluate(LogicOp.OR, false, false));
    }

    @Test
    void firstOperandMustBeBoolean() {
        LogicExp expression = new LogicExp(
                LogicOp.AND,
                new ValueExp(new IntValue(5)),
                new ValueExp(new BoolValue(true))
        );

        assertThrows(ExpressionException.class,
                () -> expression.eval(new MyDictionary<>()));
    }

    @Test
    void secondOperandMustBeBoolean() {
        LogicExp expression = new LogicExp(
                LogicOp.OR,
                new ValueExp(new BoolValue(true)),
                new ValueExp(new IntValue(5))
        );

        assertThrows(ExpressionException.class,
                () -> expression.eval(new MyDictionary<>()));
    }
}
