package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public interface IExp {
    IValue eval(IDictionary<String, IValue> symbolTable);
}
