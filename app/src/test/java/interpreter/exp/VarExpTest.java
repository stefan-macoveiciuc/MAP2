
package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.VarExp;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {

    @Test
    void evaluatesIntegerVariable() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        symTable.put("a", new IntValue(10));

        assertEquals(
                new IntValue(10),
                new VarExp("a").eval(symTable)
        );
    }

    @Test
    void evaluatesBooleanVariable() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        symTable.put("flag", new BoolValue(true));

        assertEquals(
                new BoolValue(true),
                new VarExp("flag").eval(symTable)
        );
    }

    @Test
    void evaluatesStringVariable() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        symTable.put("file", new StringValue("test.in"));

        assertEquals(
                new StringValue("test.in"),
                new VarExp("file").eval(symTable)
        );
    }

    @Test
    void undeclaredVariableThrowsException() {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        assertThrows(
                ExpressionException.class,
                () -> new VarExp("missing").eval(symTable)
        );
    }

    @Test
    void evaluatesUpdatedVariableValue() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        symTable.put("a", new IntValue(5));

        VarExp expression = new VarExp("a");

        assertEquals(new IntValue(5),
                expression.eval(symTable));

        symTable.update("a", new IntValue(20));

        assertEquals(new IntValue(20),
                expression.eval(symTable));
    }

    @Test
    void evaluatesCorrectVariable() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        symTable.put("a", new IntValue(5));
        symTable.put("b", new IntValue(10));

        assertEquals(
                new IntValue(10),
                new VarExp("b").eval(symTable)
        );
    }
}
