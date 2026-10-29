package interpreter;


import interpreter.model.exp.*;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArithExpTest {

    private IDictionary<String, IValue> emptyTable() {
        return new MyDictionary<>();
    }

    private IExp num(int value) {
        return new ValueExp(new IntValue(value));
    }

    private IExp bool(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    @Test
    void additionWorks() throws MyException {
        IExp exp = new ArithExp(
                ArithOp.ADD, num(10), num(5)
        );

        assertEquals(new IntValue(15), exp.eval(emptyTable()));
    }

    @Test
    void subtractionWorks() throws MyException {
        IExp exp = new ArithExp(
                ArithOp.SUB, num(10), num(5)
        );

        assertEquals(new IntValue(5), exp.eval(emptyTable()));
    }

    @Test
    void multiplicationWorks() throws MyException {
        IExp exp = new ArithExp(
                ArithOp.MUL, num(10), num(5)
        );

        assertEquals(new IntValue(50), exp.eval(emptyTable()));
    }

    @Test
    void divisionWorks() throws MyException {
        IExp exp = new ArithExp(
                ArithOp.DIV, num(10), num(5)
        );

        assertEquals(new IntValue(2), exp.eval(emptyTable()));
    }

    @Test
    void nestedArithmeticWorks() throws MyException {
        // 2 + (3 * 5) = 17
        IExp exp = new ArithExp(
                ArithOp.ADD,
                num(2),
                new ArithExp(
                        ArithOp.MUL, num(3), num(5)
                )
        );

        assertEquals(new IntValue(17), exp.eval(emptyTable()));
    }

    @Test
    void divisionByZeroThrows() {
        IExp exp = new ArithExp(
                ArithOp.DIV, num(10), num(0)
        );

        ExpressionException exception = assertThrows(
                ExpressionException.class,
                () -> exp.eval(emptyTable())
        );

        assertEquals("division by zero", exception.getMessage());
    }

    @Test
    void firstArithmeticOperandMustBeInteger() {
        IExp exp = new ArithExp(
                ArithOp.ADD, bool(true), num(5)
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(emptyTable())
        );
    }

    @Test
    void secondArithmeticOperandMustBeInteger() {
        IExp exp = new ArithExp(
                ArithOp.ADD, num(5), bool(true)
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(emptyTable())
        );
    }

    @Test
    void logicalAndWorks() throws MyException {
        IExp exp = new LogicExp(
                LogicOp.AND, bool(true), bool(false)
        );

        assertEquals(
                new BoolValue(false),
                exp.eval(emptyTable())
        );
    }

    @Test
    void logicalOrWorks() throws MyException {
        IExp exp = new LogicExp(
                LogicOp.OR, bool(false), bool(true)
        );

        assertEquals(
                new BoolValue(true),
                exp.eval(emptyTable())
        );
    }

    @Test
    void firstLogicalOperandMustBeBoolean() {
        IExp exp = new LogicExp(
                LogicOp.AND, num(1), bool(true)
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(emptyTable())
        );
    }

    @Test
    void secondLogicalOperandMustBeBoolean() {
        IExp exp = new LogicExp(
                LogicOp.OR, bool(false), num(1)
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(emptyTable())
        );
    }

    @Test
    void variableExpressionReadsSymbolTable() throws MyException {
        IDictionary<String, IValue> table = emptyTable();
        table.put("x", new IntValue(42));

        IExp exp = new VarExp("x");

        assertEquals(new IntValue(42), exp.eval(table));
    }

    @Test
    void undefinedVariableThrows() {
        IExp exp = new VarExp("missing");

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(emptyTable())
        );
    }
}
