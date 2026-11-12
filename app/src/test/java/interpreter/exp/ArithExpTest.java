
package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {

    private IValue evaluate(ArithOp op, int a, int b)
            throws Exception {

        ArithExp expression = new ArithExp(
                op,
                new ValueExp(new IntValue(a)),
                new ValueExp(new IntValue(b))
        );

        return expression.eval(new MyDictionary<>());
    }

    @Test
    void addition() throws Exception {
        assertEquals(new IntValue(8),
                evaluate(ArithOp.ADD, 5, 3));
    }

    @Test
    void subtraction() throws Exception {
        assertEquals(new IntValue(2),
                evaluate(ArithOp.SUB, 5, 3));
    }

    @Test
    void multiplication() throws Exception {
        assertEquals(new IntValue(15),
                evaluate(ArithOp.MUL, 5, 3));
    }

    @Test
    void division() throws Exception {
        assertEquals(new IntValue(2),
                evaluate(ArithOp.DIV, 6, 3));
    }

    @Test
    void integerDivisionTruncates() throws Exception {
        assertEquals(new IntValue(2),
                evaluate(ArithOp.DIV, 7, 3));
    }

    @Test
    void divisionByZeroThrowsException() {
        assertThrows(ExpressionException.class,
                () -> evaluate(ArithOp.DIV, 5, 0));
    }

    @Test
    void firstOperandMustBeInteger() {
        ArithExp expression = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new BoolValue(true)),
                new ValueExp(new IntValue(5))
        );

        assertThrows(ExpressionException.class,
                () -> expression.eval(new MyDictionary<>()));
    }

    @Test
    void secondOperandMustBeInteger() {
        ArithExp expression = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new IntValue(5)),
                new ValueExp(new BoolValue(true))
        );

        assertThrows(ExpressionException.class,
                () -> expression.eval(new MyDictionary<>()));
    }

    @Test
    void negativeNumbers() throws Exception {
        assertEquals(new IntValue(-8),
                evaluate(ArithOp.ADD, -5, -3));
    }
}
