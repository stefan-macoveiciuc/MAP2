package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String name) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable){
        return symbolTable.get(name);
    }

    @Override
    public String toString(){
        return name;
    }
}
