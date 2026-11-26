
package interpreter.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewStmtTest {

    @Test
    void allocatesValueInHeap() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);

        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        assertEquals(
                new IntValue(20),
                state.getHeap().get(1)
        );

        assertEquals(
                new RefValue(1, new IntType()),
                state.getSymTable().lookup("v")
        );
    }

    @Test
    void allocatesNewAddressEveryTime() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);

        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        new NewStmt("v", new ValueExp(new IntValue(30)))
                .execute(state);

        assertEquals(new IntValue(20), state.getHeap().get(1));
        assertEquals(new IntValue(30), state.getHeap().get(2));

        assertEquals(
                new RefValue(2, new IntType()),
                state.getSymTable().lookup("v")
        );
    }

    @Test
    void allocatesNestedReference() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);

        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        new VarDeclStmt(
                "a",
                new RefType(new RefType(new IntType()))
        ).execute(state);

        new NewStmt("a", new VarExp("v")).execute(state);

        assertEquals(
                new RefValue(1, new IntType()),
                state.getHeap().get(2)
        );

        assertEquals(
                new RefValue(2, new RefType(new IntType())),
                state.getSymTable().lookup("a")
        );
    }

    @Test
    void undeclaredVariableThrows() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(
                StatementException.class,
                () -> new NewStmt(
                        "v",
                        new ValueExp(new IntValue(20))
                ).execute(state)
        );
    }

    @Test
    void nonReferenceVariableThrows() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new IntType()).execute(state);

        assertThrows(
                StatementException.class,
                () -> new NewStmt(
                        "v",
                        new ValueExp(new IntValue(20))
                ).execute(state)
        );
    }

    @Test
    void wrongValueTypeThrows() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);

        assertThrows(
                StatementException.class,
                () -> new NewStmt(
                        "v",
                        new ValueExp(new BoolValue(true))
                ).execute(state)
        );

        assertTrue(state.getHeap().getContent().isEmpty());
    }
}
