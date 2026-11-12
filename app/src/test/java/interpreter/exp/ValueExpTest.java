
package interpreter.exp;

import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {

    @Test
    void evaluatesIntegerValue() throws Exception {
        ValueExp expression =
                new ValueExp(new IntValue(42));

        assertEquals(
                new IntValue(42),
                expression.eval(new MyDictionary<>())
        );
    }

    @Test
    void evaluatesBooleanTrue() throws Exception {
        ValueExp expression =
                new ValueExp(new BoolValue(true));

        assertEquals(
                new BoolValue(true),
                expression.eval(new MyDictionary<>())
        );
    }

    @Test
    void evaluatesBooleanFalse() throws Exception {
        ValueExp expression =
                new ValueExp(new BoolValue(false));

        assertEquals(
                new BoolValue(false),
                expression.eval(new MyDictionary<>())
        );
    }

    @Test
    void evaluatesStringValue() throws Exception {
        ValueExp expression =
                new ValueExp(new StringValue("test.in"));

        assertEquals(
                new StringValue("test.in"),
                expression.eval(new MyDictionary<>())
        );
    }

    @Test
    void evaluatesEmptyString() throws Exception {
        ValueExp expression =
                new ValueExp(new StringValue(""));

        assertEquals(
                new StringValue(""),
                expression.eval(new MyDictionary<>())
        );
    }

    @Test
    void evaluationDoesNotChangeValue() throws Exception {
        IntValue value = new IntValue(10);
        ValueExp expression = new ValueExp(value);

        assertEquals(value,
                expression.eval(new MyDictionary<>()));

        assertEquals(value,
                expression.eval(new MyDictionary<>()));
    }
}
