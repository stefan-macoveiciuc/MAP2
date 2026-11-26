
package interpreter.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WriteHeapStmtTest {

    private PrgState createState() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);

        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        return state;
    }

    @Test
    void updatesHeapValue() throws Exception {
        PrgState state = createState();

        new WriteHeapStmt(
                "v", new ValueExp(new IntValue(30))
        ).execute(state);

        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    @Test
    void doesNotAllocateNewAddress() throws Exception {
        PrgState state = createState();

        new WriteHeapStmt(
                "v", new ValueExp(new IntValue(30))
        ).execute(state);

        assertEquals(1, state.getHeap().getContent().size());

        assertEquals(
                new RefValue(1, new IntType()),
                state.getSymTable().lookup("v")
        );
    }

    @Test
    void undeclaredVariableThrows() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt(
                        "v", new ValueExp(new IntValue(30))
                ).execute(state)
        );
    }

    @Test
    void nonReferenceVariableThrows() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new IntType()).execute(state);

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt(
                        "v", new ValueExp(new IntValue(30))
                ).execute(state)
        );
    }

    @Test
    void uninitializedReferenceThrows() throws Exception {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt(
                        "v", new ValueExp(new IntValue(30))
                ).execute(state)
        );
    }

    @Test
    void wrongValueTypeThrows() throws Exception {
        PrgState state = createState();

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt(
                        "v", new ValueExp(new BoolValue(true))
                ).execute(state)
        );

        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void sharedReferencesSeeUpdatedValue() throws Exception {
        PrgState state = createState();

        state.getSymTable().put(
                "a", new RefValue(1, new IntType())
        );

        new WriteHeapStmt(
                "v", new ValueExp(new IntValue(50))
        ).execute(state);

        ReadHeapExp exp = new ReadHeapExp(new VarExp("a"));

        assertEquals(
                new IntValue(50),
                exp.eval(state.getSymTable(), state.getHeap())
        );
    }
}
