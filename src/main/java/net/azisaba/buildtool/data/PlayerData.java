package net.azisaba.buildtool.data;

import net.azisaba.buildtool.operation.Operation;
import org.bukkit.inventory.Inventory;

public class PlayerData {

    private Operation.OperationType operationType = Operation.OperationType.LONG_LENGTH_PLACE;
    private int operationAmount = 1;
    private Inventory optionsInventory;

    public Operation.OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(Operation.OperationType operationType) {
        this.operationType = operationType;
    }

    public int getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(int operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Inventory getOptionsInventory() {
        return optionsInventory;
    }

    public void setOptionsInventory(Inventory optionsInventory) {
        this.optionsInventory = optionsInventory;
    }
}
